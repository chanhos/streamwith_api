package com.makestar.streamwith.streamwith_api.controller;


import com.makestar.streamwith.streamwith_api.model.vo.ResponseVo;
import com.makestar.streamwith.streamwith_api.service.AppleMusicConvertService;
import com.makestar.streamwith.streamwith_api.service.AppleMusicConvertServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @GetMapping("/v1/convert/apple-music")
    @ResponseBody
    public ResponseEntity<String> convertListToAppleMusic(){
        ResponseVo response =  appleMusicConvertService.getAppleMusicList();

        if (response.getData() != null){
            return new ResponseEntity<>(response.getData().toString(), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(response.getResponseMsg(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
