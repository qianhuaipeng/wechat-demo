package com.eastrobot.robotdev.service;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.eastrobot.robotdev.Constants;
import com.eastrobot.robotdev.utils.HttpUtils;

import java.util.Map;

@Service
public class AskService{
	
	@Value("#{configProperties['ask_url']}")
	private String ask_url;

	@Value("#{configProperties['brand']}")
	private String brand_;
	
	private static final Logger logger = LoggerFactory.getLogger(AskService.class);

	public JSONObject askToJson(Map params){
		String result = HttpUtils.getPostContent(ask_url,params,"utf-8");
		JSONObject jsonObject = JSONObject.parseObject(result);
		return jsonObject;
	}

	public String ask(String userId,String question){
		String url = new StringBuffer().append(ask_url).append("?userId=" + userId).append("&question=" + question).append("&platform=" + Constants.WEIXIN_PLATFORM).append("&format=json").toString();
		String result = HttpUtils.doGet(url, null);
		JSONObject jsonObject = JSONObject.parseObject(result);
		String answer = jsonObject.getString("content");
		return answer;
	}

	public JSONObject askToJson(String userId,String question){
		String url = new StringBuffer().append(ask_url).append("?userId=" + userId).append("&question=" + question).append("&platform=" + Constants.WEIXIN_PLATFORM).append("&format=json").toString();
		logger.info("url:" + url);
		String result = HttpUtils.doGet(url, null);
		logger.info("result:" + result);
		JSONObject jsonObject = JSONObject.parseObject(result);

		return jsonObject;
	}

	public JSONObject askToJson(String userId,String question,String platform){
		String url = new StringBuffer().append(ask_url).append("?userId=" + userId).append("&question=" + question).append("&platform=" + platform).append("&format=json").toString();
		logger.info("url:" + url);
		String result = HttpUtils.doGet(url, null);
		logger.info("result:" + result);
		JSONObject jsonObject = JSONObject.parseObject(result);

		return jsonObject;
	}

	public String askToXml(String userId,String question,String brand,String msgType){
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("request");
		root.addElement("action").setText(msgType);
		root.addElement("userid").setText(userId);
		root.addElement("question").setText(question);
		root.addElement("platform").setText("weixin");
		root.addElement("location").setText("location");
		root.addElement("brand").setText(brand_);
		String result = HttpUtils.sendRequestBody(ask_url,doc.asXML(),"utf-8");
		return result;
	}

	public String askToXml(String userId,String brand,String locationX,String locationY,String msgType){
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("request");
		root.addElement("action").setText(msgType);
		root.addElement("userid").setText(userId);

		//root.addElement("question").addElement("");
		Element question = root.addElement("question");
		question.addElement("Location_X").setText(locationX);
		question.addElement("Location_Y").setText(locationY);
		root.addElement("platform").setText("weixin");
		root.addElement("location").setText("location");
		//root.addElement("brand").setText(brand_);
		String result = HttpUtils.sendRequestBody(ask_url,doc.asXML(),"utf-8");
		return result;
	}
}
