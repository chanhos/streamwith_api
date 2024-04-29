package com.makestar.streamwith.streamwith_api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.makestar.streamwith.streamwith_api.component.ExternalAPICaller;
import com.makestar.streamwith.streamwith_api.model.spotify.Album;
import com.makestar.streamwith.streamwith_api.model.spotify.Artist;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


public class ListAPITest {


    @Test
    public void SimpleTest() throws IOException {
        System.out.println("qwe");
        ObjectMapper mapper = new ObjectMapper();

        String jsonFile= Files.readString(Paths.get("spotify_playlist.json"));

        try {
            //Artist list = mapper.readValue(jsonFile, Artist.class);


            JsonNode node = mapper.readTree(jsonFile);
            if ( node.get("spotify_playlist").isArray() ){
                node.findValues("spotify_playlist").stream().forEach(id->{
                    System.out.println(id);
                });

            }
            String spotifyUrls = node.get("external_urls").get("spotify").asText();

            System.out.println(node);
        }catch (Exception e){
            System.out.printf("error: %s",e);
        }
    }
}
