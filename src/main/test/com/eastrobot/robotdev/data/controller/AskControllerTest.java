package com.eastrobot.robotdev.data.controller;

import com.alibaba.fastjson.JSONObject;
import com.eastrobot.robotdev.ControllerBaseTest;
import org.junit.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * author: alan.peng
 * description:
 * date: create in 13:45 2018/7/30
 * modified By：
 */
public class AskControllerTest extends ControllerBaseTest {

    @Test
    public void ask() throws Exception{
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("question","ss");
        jsonObject.put("userId","231sdfse3243");
        jsonObject.put("platform","weixin");
        String responseStr = mockMvc.perform(post("/ask1.do").contentType(MediaType.APPLICATION_JSON).content(jsonObject.toString())).andDo(print())
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        System.out.println(responseStr);
    }
}
