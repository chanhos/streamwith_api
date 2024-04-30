package com.makestar.streamwith.streamwith_api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.makestar.streamwith.streamwith_api.component.ExternalAPICaller;
import com.makestar.streamwith.streamwith_api.model.apple_music.TrackList;
import com.makestar.streamwith.streamwith_api.model.request_vo.AppleMusicFindParam;
import com.makestar.streamwith.streamwith_api.model.spotify.Album;
import com.makestar.streamwith.streamwith_api.model.spotify.Artist;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static com.makestar.streamwith.streamwith_api.util.StringUtil.replaceWhiteSpace;


public class ListAPITest {


    @Test
    public void SimpleTest() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        String inputFile= Files.readString(Paths.get("spotify_playlist.json"));

        List<AppleMusicFindParam> findList = new ArrayList<>();
        try {


            JsonNode node = mapper.readTree(inputFile);

            if ( node.get("spotify_playlist").isArray() ){

                node.get("spotify_playlist").forEach(id -> {
                    String trackName = replaceWhiteSpace(node.get("spotify_track_information").get(id.asText()).get("name").asText());
                    List<String> artists = new ArrayList<>();
                    node.get("spotify_track_information").get(id.asText()).get("artists").forEach(artist ->{
                        artists.add(  replaceWhiteSpace(artist.get("name").asText()));
                    });
                    findList.add(new AppleMusicFindParam(trackName,artists));
                });
            }


            // #1. ditto,new+jeans 검색결과 파싱
            String apiResultFile = Files.readString(Paths.get("2pIUpMhHL6L9Z5lnKxJJr9.json"));

            JsonNode apiNode = mapper.readTree(apiResultFile);

            JsonNode data = apiNode.get("results").get("songs");
            TrackList trackList = mapper.treeToValue(data,TrackList.class);


            System.out.println(trackList.getTrackList());
        }catch (Exception e){
            System.out.printf("error: %s",e);
        }
    }
}
