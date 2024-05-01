package com.makestar.streamwith.streamwith_api.component;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class AppleMusicApiCaller {

    private static final String SearchForCatalogResourceUrl = "https://api.music.apple.com/v1/catalog/{storefront}/search";

    private static final String GetACatalogSongUrl = "https://api.music.apple.com/v1/catalog/{storefront}/songs/{id}";
    @Resource
    RestTemplate restTemplate;

    @Value("${config.apikey}")
    private String _apiKey;

    public ResponseEntity<String> searchForCatalogResourece(String searchParam) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + _apiKey);

        String uri = UriComponentsBuilder.fromUriString(SearchForCatalogResourceUrl)
                .queryParam("types", "artists,songs")
                .queryParam("term", "{term}")
                .encode()
                .toUriString();


        Map<String, String> params = new HashMap<>();
        params.put("storefront", "us");
        params.put("term", searchParam);


        try {
            return restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers), String.class, params);
        } catch (Exception e) {
            System.out.printf("error occured : %s", e);
            return new ResponseEntity<>("internal Server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> getACatalogSong(String searchParam) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + _apiKey);

        String uri = UriComponentsBuilder.fromUriString(GetACatalogSongUrl)
                .encode()
                .toUriString();


        Map<String, String> params = new HashMap<>();
        params.put("storefront", "us");
        params.put("id", searchParam);


        try {
            return restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers), String.class, params);
        } catch (Exception e) {
            System.out.printf("error occured : %s", e);
            return new ResponseEntity<>("internal Server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

