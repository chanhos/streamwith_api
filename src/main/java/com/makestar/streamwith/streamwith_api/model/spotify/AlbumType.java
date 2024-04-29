package com.makestar.streamwith.streamwith_api.model.spotify;


public enum AlbumType {
    Single("single") ;

    private final String label;

    AlbumType(String type){
        this.label = type;
    }

    public String label(){
        return label;
    }
}