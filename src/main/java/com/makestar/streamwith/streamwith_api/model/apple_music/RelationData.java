package com.makestar.streamwith.streamwith_api.model.apple_music;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class RelationData {
    private String id;

    @JsonProperty("href")
    private String url;

    private String type;
}
