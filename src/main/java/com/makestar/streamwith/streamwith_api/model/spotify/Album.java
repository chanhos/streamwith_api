package com.makestar.streamwith.streamwith_api.model.spotify;


import com.makestar.streamwith.streamwith_api.model.ExternalUrl;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Album {
    String id;
    String uri;
    String href;
    String name;
    InfoType type;
    List<AlbumImage> images;
    List<Artist> artists;
    AlbumType albumType;
    String releaseDate;
    int totalTracks;
    ExternalUrl externalUrls;
    String[] availableMarkets;
}
