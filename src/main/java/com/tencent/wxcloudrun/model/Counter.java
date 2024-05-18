package com.tencent.wxcloudrun.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("Counters")
public class Counter implements Serializable {

  @TableId
  private Integer id;

  private Integer count;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;
}
