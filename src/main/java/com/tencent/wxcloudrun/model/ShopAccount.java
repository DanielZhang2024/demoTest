package com.tencent.wxcloudrun.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ShopAccount implements Serializable {

    private Integer id;
    private String name;
    private String password;
    private String phone;
    private String email;
    private String address;
    private String province;
    private String city;
    private String area;
    private Date createTime;
    private Date updateTime;

}
