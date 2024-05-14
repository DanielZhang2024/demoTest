package com.tencent.wxcloudrun.weixin.service;

import com.tencent.wxcloudrun.weixin.message.resp.Article;
import com.tencent.wxcloudrun.weixin.message.resp.NewsMessage;
import com.tencent.wxcloudrun.weixin.message.resp.TextMessage;
import com.tencent.wxcloudrun.weixin.utils.MessageUtil;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CoreService {

    /**
     * 处理微信发来的请求
     *
     * @param request
     * @return
     */
    public static String processRequest(HttpServletRequest request) {
        String respMessage = null;
        try {
            // 默认返回的文本消息内容
            String respContent = "123";

            // xml请求解析
            Map<String, String> requestMap = MessageUtil.parseXml(request);

            // 发送方帐号（open_id）
            String fromUserName = requestMap.get("FromUserName");
            // 公众帐号
            String toUserName = requestMap.get("ToUserName");
            // 消息类型
            String msgType = requestMap.get("MsgType");

            String msgContent = requestMap.get("Content");
            // 回复文本消息

            TextMessage textMessage = new TextMessage();
            textMessage.setToUserName(fromUserName);
            textMessage.setFromUserName(toUserName);
            textMessage.setCreateTime(System.currentTimeMillis()/1000);
            textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
            System.out.println("消息类型======="+msgType);
            System.out.println("公众帐号======="+toUserName);
            System.out.println("发送方帐号======="+fromUserName);

            if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
            	/*if("登录".equals(msgContent)) {
            		String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxfcf5fc9e29e0d4ec&redirect_uri=http://www.aibosiqin.com/user&response_type=code&scope=snsapi_userinfo#wechat_redirect";
            		respContent="点击<a href=\""+url+"\">这里</a>登录";
            	}*/
            }
            // 图片消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
                respContent = "您发送的是图片消息！";
            }
            // 地理位置消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
                respContent = "您发送的是地理位置消息！";
            }
            // 链接消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
                respContent = "您发送的是链接消息！";
            }
            // 音频消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
                respContent = "您发送的是音频消息！";
            }
            // 事件推送
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
                // 事件类型
                String eventType = requestMap.get("Event");
                // 订阅
                if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
                    respContent = "感谢您的关注！";
                }
                // 取消订阅
                else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
                    //取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
                }
                // 自定义菜单点击事件
                else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
                    respContent = dealClick(requestMap);
                }
                else if(eventType.equals(MessageUtil.EVENT_TYPE_SCAN)){
                    String eventKey = requestMap.get("EventKey");
                    Article article = new Article();
                    article.setTitle("测试标题");
                    article.setDescription("这是一句测试文案，参数是:"+eventKey);
                    article.setPicUrl("https://7072-prod-7gln35vf511d8e79-1326501488.tcb.qcloud.la/0001.jpg?sign=5ffc77968440200f059ae3b6b96db22a&t=1715529933");
                    article.setUrl("https://prod-7gln35vf511d8e79-1326501488.tcloudbaseapp.com");
                    List<Article> articles = new ArrayList<>();
                    articles.add(article);
                    NewsMessage newsMessage = new NewsMessage();
                    newsMessage.setFromUserName(toUserName);
                    newsMessage.setToUserName(fromUserName);
                    newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
                    newsMessage.setCreateTime(System.currentTimeMillis()/1000);
                    newsMessage.setArticleCount(1);
                    newsMessage.setArticles(articles);
                    return MessageUtil.newsMessageToXml(newsMessage);
                }
            }

            textMessage.setContent(respContent);
//            respMessage = ParseXml.textMessageToXml(textMessage);
            respMessage = MessageUtil.textMessageToXml(textMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return respMessage;
    }

    /**
     *关闭连接
     * @see
     * @param rs
     * @param stmt
     * @param conn
     */
    public static void closeConnection(ResultSet rs, Statement stmt, Connection conn) {
        try {
            if (rs != null && !rs.isClosed()) {
                rs.close();
            }
        } catch (SQLException e) {
            rs = null;
        }
        try {
            if (stmt != null && !stmt.isClosed()) {
                stmt.close();
            }
        } catch (SQLException e) {
            stmt = null;
        }
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            conn = null;
        }
    }

    /**
     * 处理事件推送-处理click类型的菜单按钮
     * @param requestMap 请求信息
     * @return 返回内容
     */
    private static String dealClick(Map<String, String> requestMap) {

        String respContent = "";
        String key = requestMap.get("EventKey");
        switch (key) {
            case "2":
                respContent = "点击了医院信息";
                break;
            case "3":
                respContent = "点击了个人中心";
                break;
            case "13":
                respContent = "点击了单位信息";
                break;
            default:
                break;
        }
        return respContent;
    }
}