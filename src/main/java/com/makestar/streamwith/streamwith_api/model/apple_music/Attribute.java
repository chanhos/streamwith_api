package com.makestar.streamwith.streamwith_api.model.apple_music;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.makestar.streamwith.streamwith_api.model.streamwith.Artwork;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class Attribute {
    @JsonProperty("name")
    private String songName;

    @JsonProperty("url")
    private String attributeUrl;

    private String isrc;

    private Artwork artwork;

    private Preview[] previews;

    private String albumName;

    private boolean hasLyrics;

    private String artistName;

    private int discNumber;

    private String[] genreNames;

    private boolean hasCredits;

    private PlayParam playParams;

    private String releaseDate;

    private int trackNumber;

    private String composerName;

    private long durationInMillis;

    @JsonProperty("isAppleDigitalMaster")
    private boolean appleDigitalMaster;
}
