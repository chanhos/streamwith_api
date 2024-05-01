package com.makestar.streamwith.streamwith_api.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.makestar.streamwith.streamwith_api.component.AppleMusicListConverter;
import com.makestar.streamwith.streamwith_api.model.apple_music.StreamWithAppleMusicList;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class AppleMusicConvertServiceImpl implements AppleMusicConvertService {

    @Resource
    private AppleMusicListConverter appleMusicListConverter;

    @Override
    public ResponseEntity<String> getAppleMusicList() {
        ObjectMapper mapper = new ObjectMapper();
        StreamWithAppleMusicList list =appleMusicListConverter.convert();

        if (list != null) {
            try {
                String str = mapper.writeValueAsString(list);
                return new ResponseEntity<>(str, HttpStatus.OK);
            }catch (JsonProcessingException e) {
                return new ResponseEntity<>(String.format("Internal Server error : %s",e), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }else{
            return new ResponseEntity<>("Internal Server error ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
