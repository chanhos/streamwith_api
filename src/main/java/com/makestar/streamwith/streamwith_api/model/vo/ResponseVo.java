package com.makestar.streamwith.streamwith_api.model.vo;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class ResponseVo {

    private String responseMsg = "Success";

    private Object data;

    @Builder
    public ResponseVo(String responseMsg, Object data) {
        this.responseMsg = responseMsg;
        this.data = data;
    }
}