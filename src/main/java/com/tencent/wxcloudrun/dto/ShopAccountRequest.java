package com.tencent.wxcloudrun.dto;

import lombok.Data;

@Data
public class ShopAccountRequest {

  private String phone;
  private String password;
  private Integer id;
}
