package com.eastrobot.robotdev.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class BaseController {
	
	
	
    /**
     * @Description
     * <b>(直接输出json)</b></br>
     * <p>
     * TODO(使用fastjons输出json字符串)
     * </p>
     * @param response
     * @param obj 
     * void 
     * @since 2016-9-20    
    */
    protected void writeJson(HttpServletResponse response, Object obj){
        if( obj != null ){
            String result = JSON.toJSONString( obj );
            response.setContentType("text/json");
            response.setCharacterEncoding("utf-8");
            try {
                PrintWriter out = response.getWriter();
                out.print( result );
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * @Description
     * <b>(直接输出字符串)</b></br>
     * @param response
     * @param str 
     * void 
     * @since 2016-9-20    
    */
    protected void writeString(HttpServletResponse response, String str){
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");
        
        try {
            PrintWriter out = response.getWriter();
            out.print( str );
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * @Description
     * <b>(直接输出json)</b></br>
     * <p>
     * TODO(使用fastjons输出json字符串)
     * </p>
     * @param response
     * @param obj 
     * void 
     * @since 2016-9-20    
    */
    protected void writeFail(HttpServletResponse response){
            response.setContentType("text/json");
            response.setCharacterEncoding("utf-8");
            try {
                PrintWriter out = response.getWriter();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("success", false);
                out.print( jsonObject );
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    /**
     * @Description
     * <b>(直接输出json)</b></br>
     * <p>
     * TODO(使用fastjons输出json字符串)
     * </p>
     * @param response
     * @param obj
     * void
     * @since 2016-9-20
     */
    protected void writeFail(HttpServletResponse response,String message){
        response.setContentType("text/json");
        response.setCharacterEncoding("utf-8");
        try {
            PrintWriter out = response.getWriter();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("success", false);
            jsonObject.put("message",message);
            out.print( jsonObject );
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    /**
     * @Description
     * <b>(直接输出json)</b></br>
     * <p>
     * TODO(使用fastjons输出json字符串)
     * </p>
     * @param response
     * void
     * @since 2016-9-20
     */
    protected void writeSuccess(HttpServletResponse response){
        response.setContentType("text/json");
        response.setCharacterEncoding("utf-8");
        try {
            PrintWriter out = response.getWriter();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("success", false);
            out.print( jsonObject );
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
