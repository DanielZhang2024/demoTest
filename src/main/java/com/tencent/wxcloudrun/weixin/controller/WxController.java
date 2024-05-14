package com.tencent.wxcloudrun.weixin.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @Description
 * @author Zxn
 * @Version 1.0
 **/
@RestController
@RequestMapping("/system/weChatLogin")
public class WxController {
    String appId = "wxdfccfc491bf90233";
    String appSecret = "fa4087c4a374a54460836617a996f873";
//    String sym = "http://wzigqy.natappfree.cc";

    final Logger logger;

    public WxController() {
        this.logger = LoggerFactory.getLogger(WxController.class);
    }

//    private String prefix = "system/info";
//
//    private final String TOKEN = "access_token";

//    @Autowired
//    private ITEmployeeInfoService tEmployeeInfoService;
//    /**
//     * 去微信登录页面
//     * @return
//     */
//    @RequestMapping("/wxlogin")
//    public String wxlogin(Model model, String openid) {
//        model.addAttribute("openid",openid);
//        return "WeChatLogin";
//    }
//    /**
//     * 去微信登录页面
//     * @return
//     */
//    @RequestMapping("/register")
//    public String register(Model model, String openid) {
//        logger.debug("openId:"+openid);
//        model.addAttribute("openid",openid);
//        return prefix + "/register";
//    }
//
//    /**
//     * 进入页面(团队知识管理之二级页面)
//     * @return 页面路径
//     */
//    @RequestMapping("/wx_login")
//    public void showquan(HttpServletResponse response ) throws Exception {
//        String path = sym+"/system/weChatLogin/callBack";
//        String url = "https://open.weixin.qq.com/connect/oauth2/authorize" +
//                "?appid=" +appId+
//                "&redirect_uri=" +path+
//                "&response_type=code" +
//                "&scope=snsapi_userinfo" +
//                "&state=STATE" +
//                "#wechat_redirect";
//        response.sendRedirect(url);
//    }
//
//
//    @RequestMapping("/callBack")
//    public void callBack (Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
//        String code = request.getParameter("code");
//        //第二步：通过code换取网页授权access_token
//        //获取code后，请求以下链接获取access_token：
//        String url = "https://api.weixin.qq.com/sns/oauth2/access_token" +
//                "?appid=" +appId+
//                "&secret=" +appSecret+
//                "&code=" +code+
//                "&grant_type=authorization_code";
//        JSONObject jsonObject = null;
//        jsonObject = doGetStr(url);
//        String openid = jsonObject.getString("openid");
//        logger.debug("返回的信息:"+jsonObject);
//        String access_token = jsonObject.getString("access_token");
//        String infoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=" + access_token + "&openid=" + openid
//                + "&lang=zh_CN";
//        //3.获取微信用户信息
//        net.sf.json.JSONObject userInfo = AuthUtil.doGetJson(infoUrl);
//        logger.debug("用户信息:"+userInfo);
//        logger.debug("微信名:"+userInfo.getString("nickname"));
//        logger.debug("微信头像:"+userInfo.getString("headimgurl"));
//        logger.debug("微信性别:"+userInfo.getString("sex"));
//        //model.addAttribute("openid",openid);
//
//        //判断 ，1.如果openid 已经存入到数据库里面，跳转到用户展示页面
//        // 2. 如果数据库没有就让他跳转到登陆
//
//        TEmployeeInfo user  = tEmployeeInfoService.selectTEmployeeInfoByOpenId(openid);
//        if (null == user) {
//            //如果无手机信息,则跳转手机绑定页面
//            response.sendRedirect(sym+"/system/weChatLogin/register?openid="+openid);
//        }else{
//            //否则直接跳转首页
//            response.sendRedirect(sym+"/system/info/add?openId="+openid);
//        }
//    }
//
//    public JSONObject doGetStr(String strUrl) throws Exception {
//        URL url = new URL(strUrl);
//        URI uri = new URI(url.getProtocol(), url.getHost(), url.getPath(), url.getQuery(), null);
//        HttpClient httpClient;
//        JSONObject jsonObject = null;
//        try {
//            httpClient = HttpClientBuilder.create().build();
//            HttpGet get = new HttpGet(uri);
//            HttpResponse execute = httpClient.execute(get);
//            HttpEntity entity = execute.getEntity();
//            if (entity != null) {
//                String result= EntityUtils.toString(entity,"UTF-8");
//                jsonObject = JSON.parseObject(result);
//            }
//            System.out.println(jsonObject);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return jsonObject;
//    }

//    @RequestMapping("/getToken")
//    public void getToken(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
//        System.out.println("-----开始校验签名-----");
//
//        /**
//         * 接收微信服务器发送请求时传递过来的参数
//         */
//        String signature = req.getParameter("signature");
//        String timestamp = req.getParameter("timestamp");
//        String nonce = req.getParameter("nonce"); //随机数
//        String echostr = req.getParameter("echostr");//随机字符串
//
//        /**
//         * 将token、timestamp、nonce三个参数进行字典序排序
//         * 并拼接为一个字符串
//         */
//        String sortStr = sort(TOKEN,timestamp,nonce);
//        /**
//         * 字符串进行shal加密
//         */
//        String mySignature = shal(sortStr);
//        /**
//         * 校验微信服务器传递过来的签名 和  加密后的字符串是否一致, 若一致则签名通过
//         */
//        if(!"".equals(signature) && !"".equals(mySignature) && signature.equals(mySignature)){
//            System.out.println("-----签名校验通过-----");
//            resp.getWriter().write(echostr);
//        }else {
//            System.out.println("-----校验签名失败-----");
//        }
//    }

}
