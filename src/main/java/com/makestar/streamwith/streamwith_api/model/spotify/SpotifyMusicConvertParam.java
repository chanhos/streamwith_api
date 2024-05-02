package com.makestar.streamwith.streamwith_api.model.spotify;


import com.makestar.streamwith.streamwith_api.util.StringUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import static com.makestar.streamwith.streamwith_api.util.StringUtil.SPACE_REPLACE_CHAR;
import static com.makestar.streamwith.streamwith_api.util.StringUtil.replaceWhiteSpace;


@Setter
@Getter
@AllArgsConstructor
public class SpotifyMusicConvertParam {

    //Spotify music id
    private String id;
    // 찾을 곡명
    private String songName;
    // 찾을 곡의 앨범명
    private String albumName;
    // 찾을 곡의 artist 명(복수)
    private List<String> artists;


    public String getAppleMusicApiQueryParam(){
        //곡명+아티스트명
        return replaceWhiteSpace(songName) +SPACE_REPLACE_CHAR+ String.join(SPACE_REPLACE_CHAR,artists);
    }

    public boolean isMatchedSongName(String label){
        String lowerCasedTrimLabel = label.toLowerCase().replaceAll(" ", "");
        String matchRegExp = "^" + StringUtil.convertRegexStr(this.songName)+ "$";

        return  lowerCasedTrimLabel.matches(matchRegExp);
    }

    public boolean isMatchedArtist(String label){
        String lowerCasedTrimLabel = label.toLowerCase().replaceAll(" ", "");
        for (String artist :artists) {
            String matchRegExp = "^.*" +StringUtil.convertRegexStr(artist) + ".*$";
            if ( lowerCasedTrimLabel.matches(matchRegExp) ) {
                return true;
            }
        }
        return  false;
    }

    public boolean isMatchedAlbumName(String label){
        String lowerCasedTrimLabel = label.toLowerCase().replaceAll(" ", "");
        String matchRegExp = "^" + StringUtil.convertRegexStr(this.albumName)+ ".*$";

        return  lowerCasedTrimLabel.matches(matchRegExp);
    }
}


