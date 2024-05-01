package com.makestar.streamwith.streamwith_api.model.apple_music;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class AppleSongDigest {
    private String id;

    private String songName;

    private String albumName;

    private String artist;
}
