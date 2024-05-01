package com.makestar.streamwith.streamwith_api.model.streamwith;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Artwork {
    private int width;

    private int height;
    @JsonProperty("url")
    private String artworkUrl;

    private String bgColor;

    private String textColor1;

    private String textColor2;

    private String textColor3;

    private String textColor4;
}
