package com.makestar.streamwith.streamwith_api.model.apple_music;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class Album {
    @JsonProperty("data")
    private RelationData[] albumDatas;

    @JsonProperty("href")
    private String albumUrl;
}
