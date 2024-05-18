package com.tencent.wxcloudrun.common.weixin.utils;


import lombok.Data;

import java.io.Serializable;

@Data
public class AccessTokenH5 implements Serializable {
    private String access_token;
    private String expires_in;
    private String refresh_token;
    private String openid;
    private String scope;
    private String unionid;
}
