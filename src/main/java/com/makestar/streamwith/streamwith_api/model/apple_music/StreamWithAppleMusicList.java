package com.makestar.streamwith.streamwith_api.model.apple_music;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Setter
@Getter
public class StreamWithAppleMusicList {

    @JsonProperty("apple_music_playlist")
    private List<String> appleMusicPlaylist;


    @JsonProperty("apple_music_track_information")
    private Map<String, TrackInformation> appleMusicTrackInformation;


    @JsonProperty("not_matched_apple_music_track_information")
    private Map<String, NotMatchedMusic > notMatchedAppleMusicTrackInformation;

    public StreamWithAppleMusicList(){
        this.appleMusicPlaylist = new ArrayList<>();
        this.appleMusicTrackInformation = new HashMap<>();
        this.notMatchedAppleMusicTrackInformation = new HashMap<>();
    }
}
