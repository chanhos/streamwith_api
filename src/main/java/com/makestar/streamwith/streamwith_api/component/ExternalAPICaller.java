package com.makestar.streamwith.streamwith_api.component;

import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import jakarta.annotation.Resource;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class ExternalAPICaller {

    @Resource
    RestTemplate restTemplate;


    private String _apiKey = "eyJhbGciOiJFUzI1NiIsImtpZCI6IjNHM0ZOV0ZIM1ciLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJRNDc0M1Y2M1NCIiwiZXhwIjoxNzE0NDI2NTU4LCJpYXQiOjE3MTQzODMzNTh9.Qball06I_J2-0LF-ZaQ-ud94T9hg8c8my7Iz_o5GQEwIc6dkqTgAOeHPdR-y-IgyvuUrxwHLYYPwJc6Qp6SnDg";


    public void getApiCall(String endPoint, String artist,String songName){



        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization","Bearer "+_apiKey);


        String uri = UriComponentsBuilder.fromUri(URI.create(endPoint))
                .queryParam("types","artists,songs")
                .queryParam("term","{term}")
                .encode()
                .toUriString();


        Map<String, String> params = new HashMap<>();
        params.put("term",artist+"+"+songName);


        try{
            HttpEntity<String> apiResponse = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers),String.class,params);
            System.out.println(apiResponse.getBody());
            ObjectMapper mapper = new ObjectMapper();

        }catch (Exception e){
            System.out.printf("error occured : %s",e);
        }

    }
}
