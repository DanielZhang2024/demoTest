package com.tencent.wxcloudrun.common.weixin;

import com.tencent.wxcloudrun.common.utils.ImageUtils;
import com.tencent.wxcloudrun.common.weixin.utils.AccessToken;
import com.tencent.wxcloudrun.common.weixin.utils.AccessTokenH5;
import com.tencent.wxcloudrun.common.weixin.utils.WxUserInfo;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;


@Component
public class WxService {

    @Resource
    private RestTemplate restTemplate;

    final Logger logger = LoggerFactory.getLogger(WxService.class);
    //线上
    private String appid = "wxdfccfc491bf90233";
    private String appSecret = "82af1ab22279415eca7a0e58c962f8d3";
    //本地
//    private String appid = "wx062f4f8f489f57a3";
//    private String appSecret = "4115bc85b36c527bdbbf30baebe98644";
    private AccessToken accessToken = null;
    // 获取access_token的接口地址（GET） 限200（次/天）
    public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    public AccessToken getAccessToken() {
        if(this.accessToken == null || this.accessToken.isExpired()) {
            AccessToken accessToken = null;
            String requestUrl = access_token_url.replace("APPID", this.appid).replace("APPSECRET", this.appSecret);
            JSONObject jsonObject = restTemplate.getForObject(requestUrl, JSONObject.class);
            // 如果请求成功
            if (null != jsonObject) {
                System.out.println(jsonObject.toString());
                try {
                    accessToken = new AccessToken();
                    accessToken.setToken(jsonObject.getString("access_token"));
                    accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
                    this.accessToken = accessToken;
                    this.accessToken.setExpiresTiem(System.currentTimeMillis() + accessToken.getExpiresIn() * 1000L);
                } catch (JSONException e) {
                    accessToken = null;
                    logger.error("获取日志失败{}", e.getMessage());
                }
            }
            return accessToken;
        }else {
            return this.accessToken;
        }

    }

    //大吉大利
    public JSONObject getQrcodeTicket(Integer id){
        String qrcodeTicketUrl = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=ACCESS_TOKEN";

        AccessToken accessToken = getAccessToken();
        String url = qrcodeTicketUrl.replace("ACCESS_TOKEN", accessToken.getToken());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> scene_str = new HashMap<>();
        scene_str.put("scene_str",id.toString());

        Map<String, Map<String, String>> scene = new HashMap<>();
        scene.put("scene",scene_str);

        Map<String, Object> form = new HashMap<>();
        form.put("action_name", "QR_LIMIT_STR_SCENE");
        form.put("action_info",scene);

        HttpEntity<String> entity = new HttpEntity<>(JSONSerializer.toJSON(form).toString(), headers);

        return restTemplate.postForObject(url, entity, JSONObject.class);
    }


    public BufferedImage getQrcode(String ticket){
        String qrCodeUrl2 = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=TICKET";
        String url = qrCodeUrl2.replace("TICKET", URLEncoder.encode(ticket));
        return ImageUtils.getImage(url);
    }

    public String uploadFile(String fileName,byte[] out) {
        AccessToken accessToken = getAccessToken();
        String uploadUrl = "https://api.weixin.qq.com/tcb/uploadfile?access_token=ACCESS_TOKEN";
        uploadUrl = uploadUrl.replace("ACCESS_TOKEN", accessToken.getToken());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> form = new HashMap<>();
        form.put("env", "prod-7gln35vf511d8e79");
        form.put("path","code/"+fileName);
        HttpEntity<String> entity = new HttpEntity<>(JSONSerializer.toJSON(form).toString(), headers);
        JSONObject jo = restTemplate.postForObject(uploadUrl,entity,JSONObject.class);

        if (null != jo) {
            String codeUrl = jo.getString("url");
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
            paramMap.add("key", "code/"+fileName);
            paramMap.add("Signature", jo.getString("authorization"));
            paramMap.add("x-cos-security-token",jo.getString("token"));
            paramMap.add("x-cos-meta-fileid",jo.getString("cos_file_id"));
            paramMap.add("file",out);
            HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(paramMap, headers);
            restTemplate.postForObject(codeUrl, request, String.class);
            return codeUrl;
        }
        return null;
    }

    public WxUserInfo getUserInfo(String code){
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+this.appid+"&secret="+this.appSecret+"&code="+code+"&grant_type=authorization_code";
        AccessTokenH5 accessToken = restTemplate.getForObject(url, AccessTokenH5.class);
        String accessToken1 = accessToken.getAccess_token();
        System.out.println(accessToken1);
        String reUrl = "https://api.weixin.qq.com/sns/userinfo?access_token="+accessToken1+"&openid=OPENID&lang=zh_CN";
        WxUserInfo wxUserInfo = restTemplate.getForObject(reUrl, WxUserInfo.class);
        return wxUserInfo;
    }

    public String getQrCodeUrl(Integer shopId){
        JSONObject qrcodeTicket = getQrcodeTicket(shopId);
        BufferedImage code = getQrcode(qrcodeTicket.getString("ticket"));
        try {
            byte[] bytes = ImageUtils.bufferImageToByteArray(code);
            return uploadFile("code/" + shopId + ".jpg", bytes);
        } catch (IOException e) {
            logger.error("二维码上传出错{}", e.getMessage());
            return null;
        }
    }
}
