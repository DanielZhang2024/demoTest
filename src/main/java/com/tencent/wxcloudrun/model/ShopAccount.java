package com.tencent.wxcloudrun.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@Data
@TableName("shop_account")
public class ShopAccount implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private String password;
    private String phone;
    private String email;
    private String address;
    private String province;
    private String city;
    private String area;
    @TableField("createTime")
    private Date createTime;
    @TableField("updateTime")
    private Date updateTime;
    @TableField("qrCode")
    private String qrCode;
}
