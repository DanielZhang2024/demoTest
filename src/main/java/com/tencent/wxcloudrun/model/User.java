package com.tencent.wxcloudrun.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user")
public class User {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String nickname;
    private String headImgUrl;
    private Integer level;
    private String headBox;
    private Integer sex;
    private String city;
    private String province;
    private String country;
    private String openid;
    private String language;
    private String mobile;
    private Integer curShopId;
}
