package com.tencent.wxcloudrun.weixin;

import com.tencent.wxcloudrun.weixin.service.CoreService;
import com.tencent.wxcloudrun.weixin.utils.SignUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@RestController
@RequestMapping(value = "/system/weChatLogin")
public class CoreServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4023599543575398057L;

	/** 
     * 确认请求来自微信服务器 
     */
    @GetMapping(value = "/check")
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws  IOException {
		// 微信加密签名  
        String signature = request.getParameter("signature");  
        // 时间戳  
        String timestamp = request.getParameter("timestamp");  
        // 随机数  
        String nonce = request.getParameter("nonce");  
        // 随机字符串  
        String echostr = request.getParameter("echostr");  
        System.out.println("signature:"+signature+"  timestamp:"+timestamp+"  nonce:"+nonce+"  echostr:"+echostr);
  
        PrintWriter out = response.getWriter();  
        // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败  
        if (SignUtil.checkSignature(signature, timestamp, nonce)) {
            out.print(echostr);  
        }  
        out.close();
	}

	/** 
     * 处理微信服务器发来的消息
     <com.tencent.wxcloudrun.weixin.message.resp.NewsMessage>
     <ToUserName>oPDSl6u2vSWfCNgiCHia9H65AKww</ToUserName>
     <FromUserName>gh_ef008b1bf35e</FromUserName>
     <CreateTime>1715531651</CreateTime>
     <MsgType>news</MsgType>
     <ArticleCount>1</ArticleCount>
     <Articles>
     <com.tencent.wxcloudrun.weixin.message.resp.Article>
     <Title>测试标题</Title>
     <Description>这是一句测试文案，参数是:1</Description>
     <PicUrl>https://7072-prod-7gln35vf511d8e79-1326501488.tcb.qcloud.la/0001.jpg?sign=5ffc77968440200f059ae3b6b96db22a&amp;t=1715529933</PicUrl>
     <Url>https://www.baidu.com</Url>
     </com.tencent.wxcloudrun.weixin.message.resp.Article>
     </Articles>
     </com.tencent.wxcloudrun.weixin.message.resp.NewsMessage>
     */
    @PostMapping(value = "/check")
	public String doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// TODO 消息的接收、处理、响应    
		// 将请求、响应的编码均设置为UTF-8（防止中文乱码）  
        request.setCharacterEncoding("UTF-8");  
        response.setCharacterEncoding("UTF-8");  
  
        // 调用核心业务类接收消息、处理消息  
        String respMessage = CoreService.processRequest(request);
        System.out.println(respMessage);
        // 响应消息
        System.out.println(respMessage);
        return respMessage;
	}



}
