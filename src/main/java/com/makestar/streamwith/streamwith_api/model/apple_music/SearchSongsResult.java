package com.makestar.streamwith.streamwith_api.model.apple_music;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class SearchSongsResult {
    private String href;

    private String next;

    @JsonProperty("data")
    private TrackInformation[] trackList;
}
