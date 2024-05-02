package com.makestar.streamwith.streamwith_api.model.apple_music;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class NotMatchedMusic {

    @JsonProperty("id")
    private String OriginSpotifyId;

    @JsonProperty("name")
    private String songName;


    private String albumName;

    private List<String> artists;
}
