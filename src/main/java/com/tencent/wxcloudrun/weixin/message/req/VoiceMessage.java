package com.tencent.wxcloudrun.weixin.message.req;

/**
 * 音频消息 
 *
 * @author TYF
 * @date 2022-07-29
 */  
public class VoiceMessage extends BaseMessage {
    // 媒体ID  
    private String MediaId;  
    // 语音格式  
    private String Format;  
  
    public String getMediaId() {  
        return MediaId;  
    }  
  
    public void setMediaId(String mediaId) {  
        MediaId = mediaId;  
    }  
  
    public String getFormat() {  
        return Format;  
    }  
  
    public void setFormat(String format) {  
        Format = format;  
    }  
}  