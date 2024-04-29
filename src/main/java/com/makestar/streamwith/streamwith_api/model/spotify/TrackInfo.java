package com.makestar.streamwith.streamwith_api.model.spotify;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TrackInfo {
    String id;
    String uri;
    String href;
    String name;
    InfoType type;
}
