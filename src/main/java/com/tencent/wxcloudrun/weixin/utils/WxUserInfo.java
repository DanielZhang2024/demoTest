package com.tencent.wxcloudrun.weixin.utils;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class WxUserInfo {
    private String openid;
    private String nickname;
    private Integer sex;
    private String province;
    private String city;
    private String country;
    private String headimgurl;
    private String unionid;
    private String language;
    private List<String> privilege;

    @Override
    public String toString() {
        return "WxUserInfo{" +
                "openid='" + openid + '\'' +
                ", nickname='" + nickname + '\'' +
                ", sex=" + sex +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", headimgurl='" + headimgurl + '\'' +
                ", unionid='" + unionid + '\'' +
                ", language='" + language + '\'' +
                ", privilege=" + privilege +
                '}';
    }
}
