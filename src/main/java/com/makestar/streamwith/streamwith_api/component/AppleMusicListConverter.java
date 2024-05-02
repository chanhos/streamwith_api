package com.makestar.streamwith.streamwith_api.component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.makestar.streamwith.streamwith_api.model.apple_music.*;
import com.makestar.streamwith.streamwith_api.model.spotify.SpotifyMusicConvertParam;
import com.makestar.streamwith.streamwith_api.model.streamwith.Artwork;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${config.listPath}")
    String _convertListPath;

    @Resource
    AppleMusicApiCaller appleMusicApiCaller;

    public StreamWithAppleMusicList convert() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        StreamWithAppleMusicList streamWithAppleMusicList = new StreamWithAppleMusicList();


        //#1. spotify music list를 읽어와서 apple music에서 찾아야 될 노래를 담은 리스트를 생성한다.
        String inputFile= Files.readString(Paths.get(_convertListPath));
        List<SpotifyMusicConvertParam> apiCallParamList = new ArrayList<>();

        JsonNode node = mapper.readTree(inputFile);
        if ( node.get("spotify_playlist").isArray() ){
            node.get("spotify_playlist").forEach(id -> {
                String trackName = node.get("spotify_track_information").get(id.asText()).get("name").asText();
                String albumName = node.get("spotify_track_information").get(id.asText()).get("album").get("name").asText();
                String spotifyId = node.get("spotify_track_information").get(id.asText()).get("id").asText();
                List<String> artists = new ArrayList<>();
                node.get("spotify_track_information").get(id.asText()).get("artists").forEach(artist ->{
                    artists.add(  artist.get("name").asText());
                });
                apiCallParamList.add(new SpotifyMusicConvertParam(spotifyId,trackName,albumName,artists));
            });
        }


        //#2 apple music api 를 호출하여 sopotyfy music 리스트와 호환되는 apple music 컨텐츠를 찾는다.
        for (SpotifyMusicConvertParam apiCallParam : apiCallParamList) {

            //2-1. apple music api
            //     SearchForCatalogResourece(https://developer.apple.com/documentation/applemusicapi/search_for_catalog_resources)
            //     를 호출 하여 곡을 찾는다 (결과 개수가 여러개일 수 있음)
            ResponseEntity<String> resp = appleMusicApiCaller.searchForCatalogResourece(apiCallParam.getAppleMusicApiQueryParam());
            List<AppleSongDigest> matchedSongs = new ArrayList<>();  //검색을 통해 찾게된 apple music 컨텐츠 중 spotify music 컨텐츠와 매치되는 컨텐츠들..
            AppleSongDigest targetSong = null; //가장 spotify music 컨텐츠와 동일하다고 결정되어 변환하려는 apple music 컨텐츠.

            // #  검색결과 파싱
            JsonNode searchSongsResult = mapper.readTree(resp.getBody()).get("results").get("songs").get("data");
            searchSongsResult.forEach(song->{
                String songName = song.get("attributes").get("name").asText();
                String artist = song.get("attributes").get("artistName").asText();
                // 곡명과 아티스트 명으로 우선 필터링 하여 매치시킴.
                if (apiCallParam.isMatchedSongName(songName) && apiCallParam.isMatchedArtist(artist)){
                    String albumName = song.get("attributes").get("albumName").asText();
                    String id = song.get("id").asText();
                    matchedSongs.add(new AppleSongDigest(id,songName,albumName,artist));
                }
            });

            //2-2. 매칭된 컨텐츠에 대한 처리 .
            if( matchedSongs.size() > 1) {
                //매칭된 컨텐츠 중. 앨범이름까지 같다면 같은 컨텐츠라고 판단 하여 지정.
                for (AppleSongDigest target: matchedSongs) {
                    if ( apiCallParam.isMatchedAlbumName(target.getAlbumName())){
                        targetSong = target;
                        break;
                    }
                }
            }else if (matchedSongs.size() == 1 ){
                //한곡 밖에 없다면 그것이 같은 컨텐츠라고 판단 하여 지정.
                targetSong = matchedSongs.get(0);
            }else {
                //apple music에서 매칭되는 노래가 없다면 없는 노래에 대한 처리를 한다.
            }


            if (targetSong != null) {
                //2-3-1. 변한활 컨텐츠에 대한 정보를 apple music api
                //     GetACatalogSong (https://developer.apple.com/documentation/applemusicapi/get_a_catalog_song)
                //     을 호출 하여 곡에 대한 상세정보를 조회한다.
                ResponseEntity<String> respSong = appleMusicApiCaller.getACatalogSong(targetSong.getId());

                JsonNode songNode = mapper.readTree(respSong.getBody());
                //검색결과중 data 부분을 TrackInformation 클래스로 mapping
                TrackInformation track = mapper.treeToValue(songNode.get("data").get(0), TrackInformation.class);
                track.getEquivalents().put("US",track.getTrackId());

                //반환되는 streamWithAppleMusicList 에 리스트 및 상세내용 정보에 추가.
                streamWithAppleMusicList.getAppleMusicPlaylist().add(track.getTrackId());
                streamWithAppleMusicList.getAppleMusicTrackInformation().put(track.getTrackId(),track);
            }else {
                //2-3-2apple music에서 매칭되는 노래가 없으므로 매칭되지 않았던 spotify music 에 대한 정보를 추가 한다.
                NotMatchedMusic notMatched = new NotMatchedMusic();
                notMatched.setOriginSpotifyId(apiCallParam.getId());
                notMatched.setSongName(apiCallParam.getSongName());
                notMatched.setAlbumName(apiCallParam.getAlbumName());
                notMatched.setArtists(apiCallParam.getArtists());

                streamWithAppleMusicList.getNotMatchedAppleMusicTrackInformation().put(notMatched.getOriginSpotifyId(), notMatched);
            }
        }
        return streamWithAppleMusicList;
    }
}

