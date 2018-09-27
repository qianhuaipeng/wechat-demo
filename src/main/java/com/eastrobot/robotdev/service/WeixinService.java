package com.eastrobot.robotdev.service;

import com.eastrobot.robotdev.data.message.domain.RobotMessage;
import com.eastrobot.robotdev.data.poetry.domain.Poetry;
import com.eastrobot.robotdev.data.poetry.service.PoetryService;
import com.eastrobot.robotdev.utils.DateUtils;
import com.eastrobot.robotdev.utils.MiniProgramUtils;
import org.jdom.CDATA;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.eastrobot.robotdev.service.task.TaskPool;
import com.eastrobot.robotdev.service.task.impl.SendWXtxtMsg;
import com.eastrobot.robotdev.utils.XmlStrUtil;

import java.io.StringReader;
import java.util.Map;

@Service
public class WeixinService implements InitializingBean{
	
	private static final Logger logger = LoggerFactory.getLogger(WeixinService.class);
	@Autowired
	private AskService askService;

	@Autowired
	private PoetryService poetryService;

	public String sendMsg(String question, String userId, String platform){
		Poetry poetry = poetryService.findByTitle(question);
		if (poetry != null) {
			RobotMessage robotMessage = new RobotMessage();
			robotMessage.setType(15);
			robotMessage.setContent(poetry.getContent());
			robotMessage.setObject(poetry);
			return JSONObject.toJSONString(robotMessage);
		} else {
			JSONObject jsonObject = askService.askToJson(userId, question, platform);
			jsonObject = MiniProgramUtils.convert(jsonObject);
			return jsonObject.toJSONString();
		}
	}

	public JSONObject ask(Map params){
		JSONObject jsonObject = askService.askToJson(params);
		jsonObject.put("msgId", String.valueOf(DateUtils.msgId()));
		jsonObject = MiniProgramUtils.convert(jsonObject);
		return jsonObject;
	}
	
	public String sendMsg(String question, String toUser, String fromUser, long startTime){
		String msg = "";
		JSONObject jsonObject = askService.askToJson(fromUser, question);
		JSONArray commandArray = jsonObject.getJSONArray("commands");
		if (commandArray != null) {
			for (int i = 0; i < commandArray.size(); i++) {
				String name = commandArray.getJSONObject(i).getString("name");
				if ("imgtxtmsg".equalsIgnoreCase(name)) {
					JSONArray argsArray = commandArray.getJSONObject(i).getJSONArray("args");
					String imgtxtmsg = argsArray.getString(3);
					//imgtxtmsg = imgtxtmsg.replaceFirst("<![CDATA[<Articles", "");
					//imgtxtmsg = imgtxtmsg.replaceAll("</Articles>]]>", "</Articles>");
					//int start = imgtxtmsg.indexOf("<![CDATA[");
					int end = imgtxtmsg.lastIndexOf("]]>");
					imgtxtmsg = imgtxtmsg.substring(9, end);
					imgtxtmsg = imgtxtmsg.replaceAll("\n", "");
					logger.debug(imgtxtmsg);
					try {
						//Document document = XmlStrUtil.string2Doc(imgtxtmsg);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					msg = createImgtxtMsg(imgtxtmsg, toUser, fromUser);
					return msg;
				}
			}
		}
		String content = jsonObject.getString("content");
		msg = createTxtMsg(content, toUser, fromUser);
		long endTime = System.currentTimeMillis();
		if (isTimeout(endTime,startTime)) {
			TaskPool.getInstance().addTask(new SendWXtxtMsg(content,fromUser));
			return "";
		}
		return msg;
	}

	public String sendTextMessage(String question, String toUser, String fromUser, long startTime){
		String xml = askService.askToXml(fromUser,question,"","ask_text");
		SAXBuilder bui;
		bui = new SAXBuilder();
		Document doc = null;
		String msg = "";
		try {
			doc = bui.build(new StringReader(xml));
			logger.info(xml);
			Element root = doc.getRootElement();
			String content = root.getChildText("content");
			msg = createTxtMsg(content, toUser, fromUser);
			long endTime = System.currentTimeMillis();
			if (isTimeout(startTime,endTime)) {
				TaskPool.getInstance().addTask(new SendWXtxtMsg(content,fromUser));
				return "";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return msg;
	}

	public String sendImageMessage(String toUser, String fromUser, String mediaId){
		Element root = new Element("xml");
		root.addContent(new Element("ToUserName").addContent(new CDATA(fromUser)));
		root.addContent(new Element("FromUserName").addContent(new CDATA(toUser)));
		root.addContent(new Element("CreateTime").setText(createTime()));
		root.addContent(new Element("MsgType").addContent(new CDATA("image")));
		root.addContent(new Element("Image").addContent(new Element("MediaId").addContent(new CDATA(mediaId))));
		Document document = new Document();
		document.addContent(root);
		String msg = null;
		try {
			msg = XmlStrUtil.doc2String(document);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return msg;
	}

	public String sendLocationMessage(String toUser, String fromUser, long startTime,String locationX,String locationY){
		//String question = "<Location_X>" +locationX +"</Location_X><Location_Y>"+locationY+"</Location_Y>";
		String xml = askService.askToXml(fromUser,"",locationX,locationY,"ask_location");
		SAXBuilder bui;
		bui = new SAXBuilder();
		Document doc = null;
		String msg = "";
		try {
			doc = bui.build(new StringReader(xml));
			logger.info(xml);
			Element root = doc.getRootElement();
			String content = root.getChildText("content");
			msg = createTxtMsg(content, toUser, fromUser);
			long endTime = System.currentTimeMillis();
			if (isTimeout(endTime,startTime)) {
				TaskPool.getInstance().addTask(new SendWXtxtMsg(content,fromUser));
				return "";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return msg;
	}


	
	public String createTxtMsg(String content, String toUser, String fromUser) {
		Element root = new Element("xml");
		root.addContent(new Element("ToUserName").addContent(new CDATA(fromUser)));
		root.addContent(new Element("FromUserName").addContent(new CDATA(toUser)));
		root.addContent(new Element("CreateTime").setText(createTime()));
		root.addContent(new Element("MsgType").addContent(new CDATA("text")));
		root.addContent(new Element("Content").addContent(new CDATA(content)));
		Document document = new Document();
		document.addContent(root);
		String msg = null;
		try {
			msg = XmlStrUtil.doc2String(document);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return msg;
	}
	
	public String createImgtxtMsg(String content, String toUser, String fromUser){
		StringBuilder sb = new StringBuilder("<xml>");
		sb.append("<ToUserName><![CDATA[").append(fromUser).append("]]></ToUserName>");
		sb.append("<FromUserName><![CDATA[").append(toUser).append("]]></FromUserName>");
		sb.append("<CreateTime>").append(createTime()).append("</CreateTime>");
		sb.append("<MsgType><![CDATA[").append("news").append("]]></MsgType>");
		sb.append("<ArticleCount>").append("1").append("</ArticleCount>");
		sb.append(content);
		sb.append("</xml>");
		Element root = new Element("xml");
		root.addContent(new Element("ToUserName").addContent(new CDATA(fromUser)));
		root.addContent(new Element("FromUserName").addContent(new CDATA(toUser)));
		root.addContent(new Element("CreateTime").setText(createTime()));
		root.addContent(new Element("MsgType").addContent(new CDATA("new")));
		Document document = new Document();
		document.addContent(root);
		String msg = null;
		try {
			msg = XmlStrUtil.doc2String(document);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	
	}
	
	
	private String createTime() {
		return System.currentTimeMillis() + "";
	}
	
	
	private boolean isTimeout(long startTime, long endTime){
		System.out.println(endTime + "-" +startTime +"=" + (endTime - startTime));
		if ((endTime - startTime) > 4500) {
			return true;
		}
		return false;
	}
	
	public static void main(String[] args) {
		
	}


	@Override
	public void afterPropertiesSet() throws Exception {
		//String msg = sendMsg("test", "test", "test");
		//System.out.println(msg);
	}
}
