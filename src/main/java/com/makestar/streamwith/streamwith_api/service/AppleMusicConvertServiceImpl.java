package com.makestar.streamwith.streamwith_api.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.makestar.streamwith.streamwith_api.component.AppleMusicListConverter;
import com.makestar.streamwith.streamwith_api.model.apple_music.StreamWithAppleMusicList;

import com.makestar.streamwith.streamwith_api.model.vo.ResponseVo;
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
    public ResponseVo getAppleMusicList() {
        ObjectMapper mapper = new ObjectMapper();
        try{
            StreamWithAppleMusicList list =appleMusicListConverter.convert();
            if ( list != null) {
                String str = mapper.writeValueAsString(list);
                return new ResponseVo("success",str);
            }else{
                return new ResponseVo("Convert failed",null);
            }
        }catch (Exception e){
            return new ResponseVo(String.format("Internal Server error : %s",e), null);
        }
    }
}
