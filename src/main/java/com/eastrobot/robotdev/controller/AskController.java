package com.eastrobot.robotdev.controller;

import com.alibaba.fastjson.JSONObject;
import com.eastrobot.robotdev.common.BaseController;
import com.eastrobot.robotdev.service.WeixinService;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * author: alan.peng
 * description:
 * date: create in 10:56 2018/7/30
 * modified By：
 */
@RestController
public class AskController extends BaseController{

    @Autowired
    private WeixinService weixinService;

    private static final Logger logger = LoggerFactory.getLogger( AskController.class );

    @RequestMapping("ask.do")
    public void ask( HttpServletRequest req , HttpServletResponse response){
        String content = null;
    	try{
            content = IOUtils.toString( req.getInputStream(), "UTF-8" );
        }catch( IOException e ){
            logger.error( "", e );
            e.printStackTrace();
        }
        JSONObject jsonObject = JSONObject.parseObject(content);
        String question = jsonObject.getString("question");
        String userId = jsonObject.getString("userId");
        String platform = jsonObject.getString("platform");
        String format = jsonObject.getString("format");
        Map params = new HashMap();
        params.put("question",question);
        params.put("userId",userId);
        params.put("platform",platform);
        params.put("format",format);
        JSONObject result = weixinService.ask(params);
        writeJson(response,result);
    }
}
