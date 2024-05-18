package com.tencent.wxcloudrun.common.weixin.message.resp;

/**
 * 音乐消息 
 *
 * @author TYF
 * @date 2022-07-29
 */  
public class MusicMessage extends BaseMessage {
    // 音乐  
    private Music Music;

    public Music getMusic() {
        return Music;
    }

    public void setMusic(Music music) {
        Music = music;  
    }  
} 
