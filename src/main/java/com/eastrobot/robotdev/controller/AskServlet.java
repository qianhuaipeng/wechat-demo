package com.eastrobot.robotdev.controller;

import com.eastrobot.robotdev.service.WeixinService;
import com.eastrobot.robotdev.utils.HttpUtils;
import com.eastrobot.robotdev.utils.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * author: alan.peng
 * description:
 * date: create in 18:22 2018/7/3
 * modified By：
 */
public class AskServlet extends HttpServlet {

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
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String question = req.getParameter("question");
        String userId = req.getParameter("userId");
        String platform = req.getParameter("platform");
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter out = null;
        try {
            out = resp.getWriter();
            String res = weixinService.sendMsg(question, userId, platform);
            out.write(res);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            out.flush();
            out.close();
        }
    }
}
