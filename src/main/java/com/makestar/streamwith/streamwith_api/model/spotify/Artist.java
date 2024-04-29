package com.makestar.streamwith.streamwith_api.model.spotify;


import com.makestar.streamwith.streamwith_api.model.ExternalUrl;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Artist {
    String id;
    String uri;
    String href;
    String name;
    InfoType type;
    ExternalUrl externalUrls;
}

