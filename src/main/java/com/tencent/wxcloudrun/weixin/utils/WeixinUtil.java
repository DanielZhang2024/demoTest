package com.tencent.wxcloudrun.weixin.utils;


import com.tencent.wxcloudrun.weixin.entity.Menu;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;


/**
 * 公众平台通用接口工具类 
 *  
 * @author DanielZhang
 * @date 2024-05-10
 */  
public class WeixinUtil {

    private final static Logger log = LoggerFactory.getLogger(WeixinUtil.class);

	 /**
     * 发起https请求并获取结果 
     *  
     * @param requestUrl 请求地址 
     * @param requestMethod 请求方式（GET、POST） 
     * @param outputStr 提交的数据 
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值) 
     */  
    public JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {
        JSONObject jsonObject = null;
        StringBuffer buffer = new StringBuffer();  
        try {  
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化  
            TrustManager[] tm = { new MyX509TrustManager() };  
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");  
            sslContext.init(null, tm, new java.security.SecureRandom());  
            // 从上述SSLContext对象中得到SSLSocketFactory对象  
            SSLSocketFactory ssf = sslContext.getSocketFactory();  
  
            URL url = new URL(requestUrl);  
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();  
            httpUrlConn.setSSLSocketFactory(ssf);  
  
            httpUrlConn.setDoOutput(true);  
            httpUrlConn.setDoInput(true);  
            httpUrlConn.setUseCaches(false);  
            // 设置请求方式（GET/POST）  
            httpUrlConn.setRequestMethod(requestMethod);  
  
            if ("GET".equalsIgnoreCase(requestMethod))  
                httpUrlConn.connect();  
  
            // 当有数据需要提交时  
            if (null != outputStr) {  
                OutputStream outputStream = httpUrlConn.getOutputStream();  
                // 注意编码格式，防止中文乱码  
                outputStream.write(outputStr.getBytes("UTF-8"));  
                outputStream.close();  
            }  
  
            // 将返回的输入流转换成字符串  
            InputStream inputStream = httpUrlConn.getInputStream();  
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");  
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);  
  
            String str = null;  
            while ((str = bufferedReader.readLine()) != null) {  
                buffer.append(str);  
            }  
            bufferedReader.close();  
            inputStreamReader.close();  
            // 释放资源  
            inputStream.close();  
            inputStream = null;  
            httpUrlConn.disconnect();  
            jsonObject = JSONObject.fromObject(buffer.toString());
        } catch (ConnectException ce) {  
            log.error("Weixin server connection timed out.");  
        } catch (Exception e) {  
            log.error("https request error:{}", e);  
        }  
        return jsonObject;  
    }  
    
    // 获取access_token的接口地址（GET） 限200（次/天）  
    public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";  
    /** 
     * 获取access_token 
     *  
     * @param appid 凭证 
     * @param appsecret 密钥 
     * @return 
     */  
    public  AccessToken getAccessToken(String appid, String appsecret) {  
        AccessToken accessToken = null;  
      
        String requestUrl = access_token_url.replace("APPID", appid).replace("APPSECRET", appsecret);  
        JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
        // 如果请求成功  
        if (null != jsonObject) {  
            try {  
                accessToken = new AccessToken();  
                accessToken.setToken(jsonObject.getString("access_token"));  
                accessToken.setExpiresIn(jsonObject.getInt("expires_in"));  
            } catch (JSONException e) {
                accessToken = null;  
                // 获取token失败  
                log.error("获取token失败 errcode:{} errmsg:{},"+jsonObject.getInt("errcode")+","+ jsonObject.getString("errmsg"));  
            }  
        }  
        return accessToken;  
    }  
    
    // 菜单创建（POST） 限100（次/天）  
    public static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";  
    /** 
     * 创建菜单 
     *  
     * @param menu 菜单实例 
     * @param accessToken 有效的access_token 
     * @return 0表示成功，其他值表示失败 
     */  
    public  String createMenu(Menu menu, String accessToken) {
//        int result = 0;  
      
        // 拼装创建菜单的url  
        String url = menu_create_url.replace("ACCESS_TOKEN", accessToken);  
        // 将菜单对象转换成json字符串  
        String jsonMenu = JSONObject.fromObject(menu).toString();
        System.out.println(jsonMenu);
        // 调用接口创建菜单  
        return httpRequest(url, "POST", jsonMenu).toString();  
//      
    }  
    /** 
     * 删除自定义菜单 
     *  
     * 
     * @param accessToken 有效的access_token 
     * @return  
     */
    public static String del_menu_url = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN"; 
    
    public  String delMenu(String accessToken) {  
      
        // 拼装创建菜单的url  
        String url = del_menu_url.replace("ACCESS_TOKEN", accessToken);  
      
        return httpRequest(url, "GET", null).toString();  
    } 
    /** 
     * 获取公众号的用户列表 
     *  
     * 
     * @param accessToken 有效的access_token 
     * @return  
     */
    public static String user_list_url = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID"; 
    
    public  String queryUsersBywx(String accessToken,String nextOpenid) {  
      
        // 拼装创建菜单的url  
        String url = user_list_url.replace("ACCESS_TOKEN", accessToken).replace("NEXT_OPENID",nextOpenid);  
      
        return httpRequest(url, "POST", null).toString();  
    } 
    /** 
     * 获取公众号的用户基本信息 
     *  
     * 
     * @param accessToken 有效的access_token 
     * @return  
     */
    public static String user_info_url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN"; 
    public  String queryUserByOPENID(String OPENID,String accessToken) {  
         
       
        String url = user_info_url.replace("ACCESS_TOKEN", accessToken).replace("OPENID",OPENID);  
//        log.info("user_info_url==="+url);
      
        return httpRequest(url, "POST", null).toString();  
    }  
    /** 
     * 删除分组信息 
     *  
     * 
     * @param accessToken 有效的access_token 
     * @return  
     */
    public static String del_group_url = "https://api.weixin.qq.com/cgi-bin/groups/delete?access_token=ACCESS_TOKEN"; 
    public  String delGroup(String group,String accessToken) {  
        String url = del_group_url.replace("ACCESS_TOKEN", accessToken);  
        return httpRequest(url, "POST", group).toString();  
    }  
    /** 
     * 增加分组信息 
     *  
     * 
     * @param accessToken 有效的access_token 
     * @return  
     */
    public static String add_group_url = "https://api.weixin.qq.com/cgi-bin/groups/create?access_token=ACCESS_TOKEN"; 
    public  String addGroup(String group,String accessToken) {  
        String url = add_group_url.replace("ACCESS_TOKEN", accessToken);  
        return httpRequest(url, "POST", group).toString();  
    }  
    /** 
     * 修改分组信息 
     *  
     * 
     * @param accessToken 有效的access_token 
     * @return  
     */
    public static String update_group_url = "https://api.weixin.qq.com/cgi-bin/groups/update?access_token=ACCESS_TOKEN"; 
    public  String updateGroup(String group,String accessToken) {  
        String url = update_group_url.replace("ACCESS_TOKEN", accessToken);  
        return httpRequest(url, "POST", group).toString();  
    }  
    /** 
     * 查询分组信息 
     *  
     * 
     * @param accessToken 有效的access_token 
     * @return  
     */
    public static String query_group_url = "https://api.weixin.qq.com/cgi-bin/groups/get?access_token=ACCESS_TOKEN"; 
    public  String queryGroup(String accessToken) {  
        String url = query_group_url.replace("ACCESS_TOKEN", accessToken);  
        return httpRequest(url, "GET", "").toString();  
    } 
    /** 
     *批量移动用户分组 
     *  
     * 
     * @param accessToken 有效的access_token 
     * @return  
     */
    public static String batchupdate_group_url = "https://api.weixin.qq.com/cgi-bin/groups/members/batchupdate?access_token=ACCESS_TOKEN"; 
    public  String batchupdateGroup(String openIds,String accessToken) {  
        String url = batchupdate_group_url.replace("ACCESS_TOKEN", accessToken);  
        return httpRequest(url, "POST", openIds).toString();  
    } 
    /** 
     *增加客服帐号 
     *  
     * 
     * @param accessToken 有效的access_token 
     * @return  
     * POST数据示例如下：
 {
    "kf_account" : "test1@test",
    "nickname" : "客服1",
    "password" : "96e79218965eb72c92a549dd5a330112"
 }
     */
    public static String add_kf_url = "https://api.weixin.qq.com/customservice/kfaccount/add?access_token=ACCESS_TOKEN"; 
    public  String addKF(String kfInfo,String accessToken) {  
        String url = add_kf_url.replace("ACCESS_TOKEN", accessToken);  
        return httpRequest(url, "POST", kfInfo).toString();  
    } 
    /** 
     *删除客服帐号 
     *  
     * 
     * @param accessToken 有效的access_token 
     * @return  
     * 
    "kf_account" : "test1@test"  客服帐号@微信公众号
   
     */
    public static String del_kf_url = "https://api.weixin.qq.com/customservice/kfaccount/del?access_token=ACCESS_TOKEN&kf_account=KFACCOUNT"; 
    public  String delKF(String kfzh,String accessToken) {  
        String url = del_kf_url.replace("ACCESS_TOKEN", accessToken).replace("KFACCOUNT", kfzh);  
        return httpRequest(url, "GET", "").toString();  
    }
    /** 
     *设置客服帐号 
     *  
     * 
     * @param accessToken 有效的access_token 
     * @return  
     * 
    POST数据示例如下：
 {
    "kf_account" : "test1@test",
    "nickname" : "客服1",
    "password" : "96e79218965eb72c92a549dd5a330112"
 }
   
     */
    public static String update_kf_url = "https://api.weixin.qq.com/customservice/kfaccount/update?access_token=ACCESS_TOKEN"; 
    public  String updateKF(String kfInfo,String accessToken) {  
        String url = update_kf_url.replace("ACCESS_TOKEN", accessToken);  
        return httpRequest(url, "POST",kfInfo).toString();  
    }
    /** 
     *上传客服头像
     *  
     * 
     * @param accessToken 有效的access_token 
     * @return  
     * 
    POST数据示例如下：
 {
    "kf_account" : "test1@test",
    "nickname" : "客服1",
    "password" : "96e79218965eb72c92a549dd5a330112"
 }
   
     */
    public static String uploadimg_kf_url = "http://api.weixin.qq.com/customservice/kfaccount/uploadheadimg?access_token=ACCESS_TOKEN&kf_account=KFACCOUNT"; 

  /*  public  String uploadKFimg(String kfzh,String accessToken,String filePath,String fileName) {
        String url = uploadimg_kf_url.replace("ACCESS_TOKEN", accessToken).replace("KFACCOUNT", kfzh); 
        JSONObject json = httpClientPost(url, filePath, fileName, "media");
        if(json.getInt("errcode")==0)	
		    return "";
        else
        	return "设置客服帐号头像失败";
    }*/
    /*
     * 通过httpClient来实现微信的CURL上传多媒体文件功能
     * 李勇   2015-07-16
     * */
    @SuppressWarnings("deprecation")
	/*public JSONObject httpClientPost(String url,String filePath,String fileName,String partName){
    	 String result="{\"errorcode\":\"1\",\"errormsg\":\"\"}";
    	CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(url);
		try {
			 File file = new File(filePath);  
		     FileBody bin = new FileBody(file); 
			 // 多部分的实体
			MultipartEntity reqEntity = new MultipartEntity();
			// 增加
			reqEntity.addPart(partName, bin);
		    httppost.setEntity(reqEntity);
		    CloseableHttpResponse response = null;
				
		    try {
		    	response = httpclient.execute(httppost);
		        HttpEntity resEntity = response.getEntity();
		        if (resEntity != null) {
		            System.out.println("Response content length: " + resEntity.getContentLength());
		        }
		        
		        
		        if (response.getStatusLine().getStatusCode() == 200) { // 成功  
		            //获取服务器返回值  
		            InputStream input = resEntity.getContent();  
		            StringBuilder sb = new StringBuilder();  
		            int s;  
		            while ((s = input.read()) != -1) {  
		                sb.append((char) s);  
		            }  
		            result = sb.toString(); 
		            System.out.println(result);
		        } 
		        EntityUtils.consume(resEntity);
		    }catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
		        try {
					response.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
		} finally {
		    try {
				httpclient.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			return JSONObject.fromObject(result);
    }*/
    
    /** 
     * 查询分组信息 
     *  
     * 
     * @param accessToken 有效的access_token 
     * @return  
     */
//    public static String query_getrecord_url = "https://api.weixin.qq.com/cgi-bin/customservice/getrecord?access_token=ACCESS_TOKEN";
    public static String query_getrecord_url = "https://api.weixin.qq.com/customservice/msgrecord/getrecord?access_token=ACCESS_TOKEN"; 
    public  String queryDKFRecord(String accessToken,String param) {  
        String url = query_getrecord_url.replace("ACCESS_TOKEN", accessToken);  
        return httpRequest(url, "POST", param).toString();  
    } 
    /** 
     * 根据用户openid群发信息 
     * {
   "touser":[
    "OPENID1",
    "OPENID2"
   ],
    "msgtype": "text",
    "text": { "content": "hello from boxer."}
} 
     * 
     * @param accessToken 有效的access_token 
     * @return  
     */
    public static String sendmess_openid_url = "https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token=ACCESS_TOKEN"; 
    public  String sendMessByOpenid(String accessToken,String param) {  
        String url = sendmess_openid_url.replace("ACCESS_TOKEN", accessToken);  
        return httpRequest(url, "POST", param).toString();  
    } 
    /** 
     * 根据用户分组群发信息 
     * {
		   "filter":{
		      "is_to_all":false
		      "group_id":"2"
		   },
		   "text":{
		      "content":"CONTENT"
		   },
		    "msgtype":"text"
		} 
     * 
     * @param accessToken 有效的access_token 
     * @return  
     */
    public static String sendmess_group_url = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token=ACCESS_TOKEN"; 
    public  String sendMessByGroup(String accessToken,String param) {  
        String url = sendmess_group_url.replace("ACCESS_TOKEN", accessToken);  
        return httpRequest(url, "POST", param).toString();  
    }
    /*上传图文消息内的图片获取URL*/
    public static String upload_image_url ="https://api.weixin.qq.com/cgi-bin/material/add_material?access_token=ACCESS_TOKEN";
//    public static String upload_image_url ="https://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token=ACCESS_TOKEN"; 
   /* public  String uploadImage(String accessToken,String filePath,String fileName) {  
        String url = upload_image_url.replace("ACCESS_TOKEN", accessToken);//.replace("TYPE", "thumb");  
		JSONObject json = httpClientPost(url, filePath, fileName, "media");
		if(json.containsKey("errcode")){
			return "1"+json.getString("errcode");
		}else{
			return "0"+json.getString("media_id");
		}

    }*/
    /*上传图文消息素材*/
  public static String upload_news_url ="https://api.weixin.qq.com/cgi-bin/material/add_news?access_token=ACCESS_TOKEN"; 
  public  String uploadNews(String accessToken,String param) {  
      String url = upload_news_url.replace("ACCESS_TOKEN", accessToken);
      return httpRequest(url, "POST", param).toString();
  }



   /**
    * {
    "touser":"OPENID",
    "msgtype":"text",
    "text":
    {
         "content":"Hello World"
    },
    "customservice":
    {
         "kf_account": "test1@kftest"
    }
}
    */
   public static String kf_sendMes_url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN"; 
   public  String sendMesByKF(String accessToken,String param) {  
       String url = kf_sendMes_url.replace("ACCESS_TOKEN", accessToken);  
       return httpRequest(url, "POST", param).toString();  
   } 
   

   /**
    * 模板消息发送
    */
   public static String temp_sendMes_url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN"; 
   public  String sendMesByTemp(String accessToken,String param) {  
       String url = temp_sendMes_url.replace("ACCESS_TOKEN", accessToken);  
       return httpRequest(url, "POST", param).toString();  
   } 
   
   /**
    * 设置行业
    */
   public static String send_url = "https://api.weixin.qq.com/cgi-bin/template/api_set_industry?access_token=ACCESS_TOKEN"; 
   public  String send(String accessToken,String param) {  
	   String url = send_url.replace("ACCESS_TOKEN", accessToken);  
	   return httpRequest(url, "POST", param).toString();  
   } 
   
   /**
    * 获取设置的行业信息
    */
   public static String get_url = "https://api.weixin.qq.com/cgi-bin/template/get_industry?access_token=ACCESS_TOKEN"; 
	public String get(String token) {
		String url = get_url.replace("ACCESS_TOKEN", token);  
		return httpRequest(url, "GET", "").toString(); 
	} 
   
   
   /**
    * 根据code获取用户openid
    */
   public static String get_openid_url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code"; 
   public  String getOpenIdByCode(String APPID,String SECRET,String CODE) {  
       String url = get_openid_url.replace("APPID", APPID).replace("SECRET", SECRET).replace("CODE", CODE);  
       return httpRequest(url, "POST", "").toString();  
   }
   
   /**
    * 根据code换取access_token
    */
   public static String accessToken_url = " https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code"; 
   public  String getAccessTokenByCode(String APPID,String SECRET,String CODE) {  
       String url = accessToken_url.replace("APPID", APPID).replace("SECRET", SECRET).replace("CODE", CODE);  
       return httpRequest(url, "GET", "").toString();  
   }
   
   /**
    * 拉取用户信息(需scope为 snsapi_userinfo)
    */
   public static String getUser_url = " https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN"; 
   public  String getUser(String token, String openid) {  
	   String url = getUser_url.replace("ACCESS_TOKEN", token).replace("OPENID", openid);  
	   return httpRequest(url, "GET", "").toString(); 
   }
   /**
    * 拉取用户信息(需scope为 snsapi_base)
    */
   public static String getUser_url2 = " https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
   public  String getUser2(String token, String openid) {
	   String url = getUser_url.replace("ACCESS_TOKEN", token).replace("OPENID", openid);
	   return httpRequest(url, "GET", "").toString();
   }

}
