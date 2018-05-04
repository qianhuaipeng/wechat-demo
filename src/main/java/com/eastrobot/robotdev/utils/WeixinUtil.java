package com.eastrobot.robotdev.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.eastrobot.robotdev.LRUTCache;

public class WeixinUtil {

	private static final String APP_ID;
	private static final String APP_SECRET;
	private static final String ACCESS_TOKEN = "accessToken";
	private static final String ACCESS_TOKEN_EXPIRE = "accessTokenExpire";
	private static LRUTCache msgIdCache;
	private static Map accessTokenCache;
	private static Map accessTokenExpireCache;
	private static Logger logger = LoggerFactory.getLogger(WeixinUtil.class);
	static {
		// WeiXinUtil
		APP_ID = PropertiesUtil.getValue("appId");
		APP_SECRET = PropertiesUtil.getValue("appSecret");
		accessTokenCache = new ConcurrentHashMap();
		accessTokenExpireCache = new ConcurrentHashMap();
		msgIdCache = new LRUTCache(500);
	}
	
	public static boolean isMsgProcessing(String msgId) {
        boolean containsKey = msgIdCache.get(msgId) != null;
        if(!containsKey)
            msgIdCache.put(msgId, "1");
        return containsKey;
    }

	public static String getToken() {
		Long accessTokenExpire = accessTokenExpire();
		if (accessTokenExpire == null) {
			return accessToken();
		}
		if (accessTokenExpire != null && System.currentTimeMillis() <= accessTokenExpire.longValue()) {
			return (String)accessTokenCache.get(ACCESS_TOKEN);
		}
		return accessToken();
	}
	
	public static String accessToken(){
		String url;
        InputStream in = null;
        OutputStream out;
        url = (new StringBuilder("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=")).append(APP_ID).append("&secret=").append(APP_SECRET).toString();
        try {
			URLConnection conn = new URL(url).openConnection();
			conn.setDoInput(true);
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(10000);
            ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
            in = conn.getInputStream();
            IOUtils.copy(in, bytesOut);
            in.close();
	        in = conn.getInputStream();
	        IOUtils.copy(in, bytesOut);
	        String ret = new String(bytesOut.toByteArray(), "UTF-8");
	        Map m = (Map)(new ObjectMapper()).readValue(new String(bytesOut.toByteArray(), "UTF-8"),Map.class);
            Integer errCode = (Integer)m.get("errcode");
            if(errCode == null) {
                String accessToken = (String)m.get("access_token");
                Long accessTokenExpire = Long.valueOf((long)((Integer)m.get("expires_in")).intValue() * 1000L + System.currentTimeMillis());
                accessTokenCache.put(ACCESS_TOKEN, accessToken);
                accessTokenExpireCache.put(ACCESS_TOKEN_EXPIRE, accessTokenExpire);
                logger.info("accesstoken:" + accessToken);
                return accessToken;
            } else {
            	if (logger.isDebugEnabled()) {
            	    logger.error((new StringBuilder("accessToken error: code=")).append(errCode).append(", errMsg=").append(m.get("errmsg")).toString());
				}
            	accessTokenCache.remove(ACCESS_TOKEN);
                accessTokenExpireCache.remove(ACCESS_TOKEN_EXPIRE);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static boolean pushTxtMsg(String openId, String msg){
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("touser", openId);
			map.put("msgtype", "text");
			Map<String, String> content = new HashMap<String, String>();
			content.put("content", msg);
			map.put("text", content);
			String postBody = JSONObject.toJSONString(map);
			String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + accessToken();
			URLConnection conn = new URL(url).openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestProperty("X-TXE", "UTF-8");
			conn.addRequestProperty("Content-Type", "text/plain");
			OutputStream out = conn.getOutputStream();
			out.write(postBody.getBytes());
			out.flush();
			out.close();
			ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
			InputStream in = conn.getInputStream();
			IOUtils.copy(in, bytesOut);
			in.close();
			String ret = new String(bytesOut.toByteArray(), "UTF-8");
			//System.out.println("返回结果ret：" + ret);
			logger.info( ret);
			JSONObject object = JSONObject.parseObject(ret);
			if (object.getInteger("errcode") != null && object.getInteger("errcode") == 0) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	public static Long accessTokenExpire() {
		return (Long) accessTokenExpireCache.get(ACCESS_TOKEN_EXPIRE);
	}



	public static void main(String[] args) {
		pushTxtMsg("obLTptx0TemXWRqRNM-QKOZx1RKo", "nihao");
	}
 
}
