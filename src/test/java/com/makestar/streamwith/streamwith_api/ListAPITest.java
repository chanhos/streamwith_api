package com.makestar.streamwith.streamwith_api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.makestar.streamwith.streamwith_api.component.AppleMusicApiCaller;
import com.makestar.streamwith.streamwith_api.model.apple_music.AppleSongDigest;
import com.makestar.streamwith.streamwith_api.model.apple_music.StreamWithAppleMusicList;
import com.makestar.streamwith.streamwith_api.model.apple_music.TrackInformation;
import com.makestar.streamwith.streamwith_api.model.spotify.SpotifyMusicConvertParam;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static com.makestar.streamwith.streamwith_api.util.StringUtil.replaceWhiteSpace;

@SpringBootTest
public class ListAPITest {

    @Resource
    AppleMusicApiCaller appleMusicApiCaller;

    @Test
    public void SimpleTest() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        String inputFile= Files.readString(Paths.get("spotify_playlist.json"));

        List<SpotifyMusicConvertParam> findList = new ArrayList<>();
        try {


            JsonNode node = mapper.readTree(inputFile);

            if ( node.get("spotify_playlist").isArray() ){

                node.get("spotify_playlist").forEach(id -> {
                    String trackName = replaceWhiteSpace(node.get("spotify_track_information").get(id.asText()).get("name").asText());

                    String albumName = replaceWhiteSpace(node.get("spotify_track_information").get(id.asText()).get("album").get("name").asText());
                    List<String> artists = new ArrayList<>();
                    node.get("spotify_track_information").get(id.asText()).get("artists").forEach(artist ->{
                        artists.add(  replaceWhiteSpace(artist.get("name").asText()));
                    });
                    findList.add(new SpotifyMusicConvertParam(trackName,albumName,artists));
                });
            }


            SpotifyMusicConvertParam param = findList.get(0);


            ResponseEntity<String> resp = appleMusicApiCaller.searchForCatalogResourece(param.getAppleMusicApiQueryParam());

            if (resp.getStatusCode() != HttpStatus.OK){
                return;
            }

            // #1. ditto,new+jeans 검색결과 파싱

            JsonNode apiNode = mapper.readTree(resp.getBody());
            JsonNode songs = apiNode.get("results").get("songs").get("data");

            List<AppleSongDigest> targetSongs = new ArrayList<>();

            songs.forEach(song->{
                String songName = song.get("attributes").get("name").asText();
                String artist = song.get("attributes").get("artistName").asText();
                if (param.isMatchedSongName(songName) && param.isMatchedArtist(artist)){
                    String albumName = song.get("attributes").get("albumName").asText();
                    String id = song.get("id").asText();
                    targetSongs.add(new AppleSongDigest(id,songName,albumName,artist));
                }
            });

            AppleSongDigest targetSong = null;

            if( targetSongs.size() > 1) {
                for (AppleSongDigest target: targetSongs) {
                    if ( param.isMatchedAlbumName(target.getAlbumName())){
                        targetSong = target;
                    }
                }
            }

            System.out.println(targetSong);

            ResponseEntity<String> respSong = appleMusicApiCaller.getACatalogSong(targetSong.getId());



            JsonNode songNode = mapper.readTree(respSong.getBody());

            TrackInformation track = mapper.treeToValue(songNode.get("data").get(0), TrackInformation.class);

            StreamWithAppleMusicList streamWithAppleMusicList = new StreamWithAppleMusicList();

            streamWithAppleMusicList.getAppleMusicPlaylist().add(track.getTrackId());
            streamWithAppleMusicList.getAppleMusicTrackInformation().put(track.getTrackId(),track);

            String  str = mapper.writeValueAsString(streamWithAppleMusicList);
            System.out.println(str);

        }catch (Exception e){
            System.out.printf("error: %s",e);
        }
    }

    @Test
    public void StringCompareTest(){
        String tested = "thatthat(prod.&feat.sugaofbts)";
        tested = tested.replaceAll("\\(","\\\\\\(");


        String tested2 = "thatthat(prod.&feat.sugaofbts)";



        System.out.println(tested2.matches(tested));

    }
}
