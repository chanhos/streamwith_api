package com.makestar.streamwith.streamwith_api.service;


import com.makestar.streamwith.streamwith_api.component.ExternalAPICaller;
import com.makestar.streamwith.streamwith_api.model.vo.ResponseVo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class AppleMusicConvertServiceImpl implements AppleMusicConvertService {

    @Resource
    private ExternalAPICaller externalAPICaller;

    @Override
    public ResponseEntity<ResponseVo> getAppleMusic() {
        externalAPICaller.getApiCall("https://api.music.apple.com/v1/catalog/us/search","new+jeans","ditto");
        return null;
    }
}
