package com.makestar.streamwith.streamwith_api.component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.makestar.streamwith.streamwith_api.model.apple_music.*;
import com.makestar.streamwith.streamwith_api.model.spotify.SpotifyMusicConvertParam;
import com.makestar.streamwith.streamwith_api.model.streamwith.Artwork;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static com.makestar.streamwith.streamwith_api.util.StringUtil.replaceWhiteSpace;

@Slf4j
@Component
public class AppleMusicListConverter {

    @Resource
    AppleMusicApiCaller appleMusicApiCaller;

    public StreamWithAppleMusicList convert(){
        ObjectMapper mapper = new ObjectMapper();
        StreamWithAppleMusicList streamWithAppleMusicList = new StreamWithAppleMusicList();

        try {
            String inputFile= Files.readString(Paths.get("spotify_playlist.json"));
            List<SpotifyMusicConvertParam> apiCallParamList = new ArrayList<>();

            JsonNode node = mapper.readTree(inputFile);
            if ( node.get("spotify_playlist").isArray() ){

                node.get("spotify_playlist").forEach(id -> {
                    String trackName = node.get("spotify_track_information").get(id.asText()).get("name").asText();

                    String albumName = node.get("spotify_track_information").get(id.asText()).get("album").get("name").asText();
                    List<String> artists = new ArrayList<>();
                    node.get("spotify_track_information").get(id.asText()).get("artists").forEach(artist ->{
                        artists.add(  artist.get("name").asText());
                    });
                    apiCallParamList.add(new SpotifyMusicConvertParam(trackName,albumName,artists));
                });
            }


            for (SpotifyMusicConvertParam apiCallParam : apiCallParamList) {
                ResponseEntity<String> resp = appleMusicApiCaller.searchForCatalogResourece(apiCallParam.getAppleMusicApiQueryParam());

                // #1. ditto,new+jeans 검색결과 파싱
                List<AppleSongDigest> matchedSongs = new ArrayList<>();
                AppleSongDigest targetSong = null;
                JsonNode searchSongsResult = mapper.readTree(resp.getBody()).get("results").get("songs").get("data");

                searchSongsResult.forEach(song->{
                    String songName = song.get("attributes").get("name").asText();
                    String artist = song.get("attributes").get("artistName").asText();
                    if (apiCallParam.isMatchedSongName(songName) && apiCallParam.isMatchedArtist(artist)){
                        String albumName = song.get("attributes").get("albumName").asText();
                        String id = song.get("id").asText();
                        matchedSongs.add(new AppleSongDigest(id,songName,albumName,artist));
                    }
                });


                if( matchedSongs.size() > 1) {
                    for (AppleSongDigest target: matchedSongs) {
                        if ( apiCallParam.isMatchedAlbumName(target.getAlbumName())){
                            targetSong = target;
                        }
                    }
                }else if (matchedSongs.size() == 1 ){
                    targetSong = matchedSongs.get(0);
                }else {
                    //TODO: 없는 노래에대한 처리..
                    System.out.println(targetSong);
                }


                ResponseEntity<String> respSong = appleMusicApiCaller.getACatalogSong(targetSong.getId());

                JsonNode songNode = mapper.readTree(respSong.getBody());
                TrackInformation track = mapper.treeToValue(songNode.get("data").get(0), TrackInformation.class);

                track.getEquivalents().put("US",track.getTrackId());

                streamWithAppleMusicList.getAppleMusicPlaylist().add(track.getTrackId());
                streamWithAppleMusicList.getAppleMusicTrackInformation().put(track.getTrackId(),track);
            }

            return streamWithAppleMusicList;

        }catch (Exception e){
            System.out.printf("error: %s",e);
            return  null;
        }
    }


    private TrackInformation convertTrackInfo(com.makestar.streamwith.streamwith_api.model.apple_music.TrackInformation track){
        TrackInformation converted = new TrackInformation();

        converted.setTrackId(track.getTrackId());
        converted.setTrackUrl(track.getTrackUrl());
        converted.setTrackType(track.getTrackType());

        Attribute attr = new Attribute();
        attr.setAttributeUrl(track.getAttributes().getAttributeUrl());
        attr.setIsrc(track.getAttributes().getIsrc());
        attr.setSongName(track.getAttributes().getSongName());
        attr.setAlbumName(track.getAttributes().getAlbumName());
        attr.setHasLyrics(track.getAttributes().isHasLyrics());
        attr.setArtistName(track.getAttributes().getArtistName());
        attr.setDiscNumber(track.getAttributes().getDiscNumber());
        attr.setHasCredits(track.getAttributes().isHasCredits());
        attr.setReleaseDate(track.getAttributes().getReleaseDate());
        attr.setTrackNumber(track.getAttributes().getTrackNumber());
        attr.setComposerName(track.getAttributes().getComposerName());
        attr.setDurationInMillis(track.getAttributes().getDurationInMillis());
        attr.setAppleDigitalMaster(track.getAttributes().isAppleDigitalMaster());
        attr.setGenreNames(track.getAttributes().getGenreNames());

        //ArtWork
        attr.setArtwork(new Artwork(
                track.getAttributes().getArtwork().getWidth(),
                track.getAttributes().getArtwork().getHeight(),
                track.getAttributes().getArtwork().getArtworkUrl(),
                track.getAttributes().getArtwork().getArtworkUrl(),
                track.getAttributes().getArtwork().getTextColor1(),
                track.getAttributes().getArtwork().getTextColor2(),
                track.getAttributes().getArtwork().getTextColor3(),
                track.getAttributes().getArtwork().getTextColor4()
            ));


        //Preview
        Preview[] previews = new Preview[track.getAttributes().getPreviews().length];
        for (int i = 0; i < track.getAttributes().getPreviews().length; i++) {
            previews[i] = new Preview(track.getAttributes().getPreviews()[i].getPreviewUrl());
        }
        attr.setPreviews(previews);


        return converted;
    }
}

