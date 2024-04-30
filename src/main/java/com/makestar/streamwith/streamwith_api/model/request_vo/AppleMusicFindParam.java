package com.makestar.streamwith.streamwith_api.model.request_vo;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import static com.makestar.streamwith.streamwith_api.util.StringUtil.SPACE_REPLACE_CHAR;


@Setter
@Getter
@AllArgsConstructor
public class AppleMusicFindParam {
    private String name;

    private List<String> artists;

    public String getQueryParam(){
        return name+SPACE_REPLACE_CHAR+ String.join(SPACE_REPLACE_CHAR,artists);
    }
}


