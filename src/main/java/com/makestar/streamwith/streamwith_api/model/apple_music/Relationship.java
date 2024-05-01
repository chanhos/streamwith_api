package com.makestar.streamwith.streamwith_api.model.apple_music;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Relationship {
    private Album albums;

    private Artist artists;
}
