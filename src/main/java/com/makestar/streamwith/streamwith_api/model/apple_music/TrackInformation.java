package com.makestar.streamwith.streamwith_api.model.apple_music;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
@NoArgsConstructor
public class TrackInformation {
    @JsonProperty("id")
    private  String trackId;

    @JsonProperty("type")
    private String trackType;

    @JsonProperty("href")
    private String trackUrl;

    private Attribute attributes;

    private Map<String,String> equivalents = new HashMap<>();

    private Relationship relationships;
}
