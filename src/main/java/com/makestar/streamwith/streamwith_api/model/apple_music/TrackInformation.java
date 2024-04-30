package com.makestar.streamwith.streamwith_api.model.apple_music;


import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrackInformation {
    @JsonAlias("id")
    private  String trackId;

    @JsonAlias("type")
    private String trackType;

    @JsonAlias("href")
    private String trackUrl;


    private Attribute attributes;
}
