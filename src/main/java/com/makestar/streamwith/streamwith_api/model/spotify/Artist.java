package com.makestar.streamwith.streamwith_api.model.spotify;


import com.fasterxml.jackson.annotation.JsonAlias;
import com.makestar.streamwith.streamwith_api.model.ExternalUrl;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Artist {
    String id;
    String uri;
    String href;
    String name;
    InfoType type;

    @JsonAlias("external_urls")
    ExternalUrl externalUrls;
}

