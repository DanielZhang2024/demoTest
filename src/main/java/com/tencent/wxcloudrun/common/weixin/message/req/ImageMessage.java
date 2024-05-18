package com.tencent.wxcloudrun.common.weixin.message.req;

import lombok.Data;

/**
 * 图片消息 
 *
 * @author TYF
 * @date 2022-07-29
 */
@Data
public class ImageMessage extends BaseMessage {
    // 图片链接  
    private String PicUrl;
    private String MediaId;

}  