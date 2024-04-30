package com.makestar.streamwith.streamwith_api.model.apple_music;


import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Attribute {

    @JsonAlias("name")
    private String songName;

    private String albumName;

    private String artistName;

    private String[] genreNames;

    private int trackNumber;

    private long durationInMillis;

    private String releaseDate;

    private String isrc;

    private String composerName;

    @JsonAlias("url")
    private String attributeUrl;

    private int discNumber;

    private boolean hasCredits;

    @JsonAlias("isAppleDigitalMaster")
    private boolean appleDigitalMaster;

    private boolean hasLyrics;

    private ArtWork artwork;

    private PlayParam playParams;

    private Preview[] previews;

    private String contentRating;


}
