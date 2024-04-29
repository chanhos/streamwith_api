package com.makestar.streamwith.streamwith_api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
@NoArgsConstructor
public class ExternalUrl {
    HashMap<String,String>  exUrls;

    ExternalUrl(String vendor, String url ){
        this.exUrls.put(vendor,url);
    }
}
