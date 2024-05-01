package com.makestar.streamwith.streamwith_api.model.apple_music;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class Artist {
    @JsonProperty("data")
    private RelationData[] artistDatas;

    @JsonProperty("href")
    private String artistUrl;
}
