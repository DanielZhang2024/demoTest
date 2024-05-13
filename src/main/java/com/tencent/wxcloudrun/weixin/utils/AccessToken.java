package com.tencent.wxcloudrun.weixin.utils;

import lombok.Data;

@Data
public class AccessToken {
    // 获取到的凭证
    private String token;
    // 凭证有效时间，单位：秒
    private int expiresIn;

    private Long expiresTiem;
    /**
     * 判断token是否过期
     * @return
     */
    public boolean isExpired() {
        return System.currentTimeMillis() > expiresTiem;
    }

    @Override
    public String toString() {
        return "AccessToken [token=" + token + ", expiresIn=" + expiresIn + "]";
    }
}