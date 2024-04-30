package com.makestar.streamwith.streamwith_api.model.apple_music;


import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PlayParam {
    @JsonAlias("id")
    private String playId;

    private String kind;
}
