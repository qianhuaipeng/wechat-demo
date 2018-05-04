package com.eastrobot.robotdev.servlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.jdom.CDATA;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.eastrobot.robotdev.service.WeixinService;
import com.eastrobot.robotdev.utils.WeixinUtil;
import com.eastrobot.robotdev.utils.XmlStrUtil;

public class WeixinServlet extends HttpServlet {
	private Logger logger = LoggerFactory.getLogger(WeixinServlet.class); 
	
	
	private WeixinService weixinService;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		 WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
		 weixinService = (WeixinService)context.getBean(WeixinService.class);
	}
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String sig = req.getParameter("signature");
		String ts = req.getParameter("timestamp");
		String nonce = req.getParameter("nonce");
		String echo = req.getParameter("echostr");
		String acc = (String) req.getAttribute("target_acc");
		String token = req.getHeader("X-TOKEN-EXPIRE");
		logger.info("X-TOKEN-EXPIRE: " + token);
		logger.info((new StringBuilder(String.valueOf(sig))).append("|")
				.append(ts).append("|").append(nonce).append("|").append(echo)
				.toString());
		if (sig != null && ts != null && nonce != null && echo != null) {
			if (logger.isInfoEnabled())
				logger.info("weixin signature validation success.");
			resp.getWriter().write(echo);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		long startTime = System.currentTimeMillis();
		resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/xml");
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		SAXBuilder bui;
		Element root;
		String msgType;
		String userId;
		String robotId;
		String msgId;
		String mediaId;
		IOUtils.copy(req.getInputStream(), bos);
		String xmlRawInput = new String(bos.toByteArray(), "UTF-8");
		bui = new SAXBuilder();
		try {
			Document doc = bui.build(new StringReader(xmlRawInput));
			logger.info(xmlRawInput);
			root = doc.getRootElement();
			String encrypt = root.getChildText("Encrypt");
			msgType = root.getChildText("MsgType");
			userId = root.getChildText("FromUserName");
			robotId = root.getChildText("ToUserName");
			msgId = root.getChildText("MsgId");
			String createTime = root.getChildText("CreateTime");
			String msg = "";
			if (WeixinUtil.isMsgProcessing(msgId)) {
				print(resp, "");
				return;
			}
			if (StringUtils.equals("text", msgType)) {
				String question = root.getChildText("Content");
				//msg = weixinService.sendTextMessage(question, robotId, userId, startTime);
				msg = weixinService.sendMsg(question,robotId,userId,startTime);
				print(resp, msg);
			} else if (StringUtils.equals("image", msgType)){
				mediaId = root.getChildText("MediaId");
				msg = weixinService.sendImageMessage(robotId,userId,mediaId);
			} else if (StringUtils.equals("voice", msgType)){

			} else if (StringUtils.equals("event", msgType)){
				String event = root.getChildText("Event");
				String label = root.getChildText("label");
				if (StringUtils.equals("LOCATION", event) && StringUtils.isNotBlank(label)) {
					String location_X = root.getChildText("Location_X");
					String location_Y = root.getChildText("Location_Y");
					msg = weixinService.sendLocationMessage(robotId, userId, startTime,location_X,location_Y);
				} else if (StringUtils.equals("LOCATION", event)) {

				}
			} else if (StringUtils.equals("location", msgType)){
				String location_X = root.getChildText("Location_X");
				String location_Y = root.getChildText("Location_Y");
				msg = weixinService.sendLocationMessage(robotId, userId, startTime,location_X,location_Y);
			}
			req.getSession().setAttribute(userId, "");
			logger.info("message：" + msg);
			print(resp, msg);
		} catch (JDOMException e) {
			e.printStackTrace();
		}
	}
	
	private void print(HttpServletResponse response, String msg) throws IOException{
		PrintWriter out = response.getWriter();
		out.write(msg);
	}

	public static void main(String[] args) {
		WeixinServlet servlet = new WeixinServlet();
		//String string = servlet.sendTxtMsg("你好", "1", "2");
		//System.out.println(string);
		
	}
}
