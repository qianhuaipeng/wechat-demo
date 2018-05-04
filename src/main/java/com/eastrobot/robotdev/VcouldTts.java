package com.eastrobot.robotdev;


import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eastrobot.robotdev.utils.HttpUtils;

public class VcouldTts extends HttpServlet {
	

	private static final long serialVersionUID = 7777085988578468476L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String question = request.getParameter("question");
		System.out.println("question===="+question);
		String userId = request.getParameter("userId");
		System.out.println("userId===="+userId);
		String app_key = request.getParameter("app_key");
		System.out.println("app_key==="+app_key);
		String app_secret = request.getParameter("app_secret");
		System.out.println("app_secret===="+app_secret);
		String sign = "error" ; 
		String app_nonce = "";
		String url = "http://vcloud.xiaoi.com/synth.do";
		System.out.println("url===="+url);
		String Content = "text/plain";
		String XAUE = "speex-wb;7";
		String XTXE = "GBK";
		String XAUF = "audio/L16;rate=8000";
		
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("question", question);
		params.put("app_key", app_key);
		params.put("app_secret", app_secret);
		params.put("Content-Type", Content);
		params.put("X-AUE", XAUE);
		params.put("X-TXE", XTXE);
		params.put("X-AUF", XAUF);
		
		//url += "?Content-Type="+Content+"&X-AUE="+XAUE+"&X-TXE="+XTXE+"&X-AUF="+XAUF+"&question="+question+"&app_key="+app_key+"&app_secret="+app_secret;
		//System.out.println("请求url==="+url);
		try {
			String aa = HttpUtils.getContent(url, params,"utf-8");
			System.out.println("aa==="+aa);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
