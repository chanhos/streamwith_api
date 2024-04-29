package com.makestar.streamwith.streamwith_api.model.spotify;

public enum InfoType {
    TRACK("track") ,
    ALBUM("album") ,
    ARTIST("artist");

    private final String label;

    InfoType(String type){
        this.label = type;
    }

    public String label(){
        return label;
    }
}
