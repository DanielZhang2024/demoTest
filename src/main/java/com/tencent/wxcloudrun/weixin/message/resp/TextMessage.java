package com.tencent.wxcloudrun.weixin.message.resp;


import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@XStreamAlias("xml")
public class TextMessage extends BaseMessage {
    // 回复的消息内容  
    private String Content;
}  