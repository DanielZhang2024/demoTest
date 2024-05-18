package com.tencent.wxcloudrun.common.weixin.message.req;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

/** 
 * 文本消息 
 *
 * @author TYF
 * @date 2022-07-29
 */
@Data
@XStreamAlias("xml")
public class TextMessage extends BaseMessage {
    // 文本消息内容
    private String Content;
    private Integer MsgDataId;
    private String Idx;

}