package com.tencent.wxcloudrun.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("dscRecord")
public class DscRecord {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    private String headImgUrl;
    private Integer level;
    private String headBox;
    private String nickname;
    private String content;
    private Integer dscId;
    private String dscImgUrl;
    private Date createTime;
    private Integer type;
    private Integer shopId;
}
