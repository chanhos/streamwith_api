package com.makestar.streamwith.streamwith_api.service;

import com.makestar.streamwith.streamwith_api.model.vo.ResponseVo;
import org.springframework.http.ResponseEntity;

public interface AppleMusicConvertService {
    public ResponseEntity<ResponseVo> getAppleMusic();
}
