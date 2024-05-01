package com.makestar.streamwith.streamwith_api.controller;


import com.makestar.streamwith.streamwith_api.service.AppleMusicConvertService;
import com.makestar.streamwith.streamwith_api.service.AppleMusicConvertServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ListConvertController {
    private final AppleMusicConvertService appleMusicConvertService;

    @Autowired
    public ListConvertController(AppleMusicConvertServiceImpl appleMusicConvertService){
        this.appleMusicConvertService = appleMusicConvertService;
    }

    @GetMapping("/converted/apple-music")
    @ResponseBody
    public ResponseEntity<String> convertListToAppleMusic(){
        return  appleMusicConvertService.getAppleMusicList();
    }
}
