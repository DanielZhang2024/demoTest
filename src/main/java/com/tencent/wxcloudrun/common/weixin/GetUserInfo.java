package com.tencent.wxcloudrun.common.weixin;

import com.tencent.wxcloudrun.common.weixin.utils.WeixinUtil;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author DanielZhang
 * @date 2024-05-10
 */
@WebServlet("/user")
public class GetUserInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String APPID = "";
	private static final String SECRET = "";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetUserInfo() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取code
		String code = request.getParameter("code");
		// 调用接口获取access_token  
        WeixinUtil wxu = new WeixinUtil();
		String result = wxu.getAccessTokenByCode(APPID, SECRET, code );
		String at = JSONObject.fromObject(result).getString("access_token");
		String openid = JSONObject.fromObject(result).getString("openid");
		result = wxu.getUser(at, openid);
		System.out.println(result);
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
