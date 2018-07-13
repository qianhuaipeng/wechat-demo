package com.eastrobot.robotdev.data.controller;

import com.eastrobot.robotdev.ControllerBaseTest;
import org.junit.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * author: alan.peng
 * description:
 * date: create in 14:54 2018/7/13
 * modified By：
 */
public class PoetryControllerTest extends ControllerBaseTest {

    @Test
    public void getHost() throws Exception{
        String responseStr = mockMvc.perform(post("/data/poetry/findByTitle.do").param("title","关雎")).andDo(print())
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        System.out.println(responseStr);
    }
}
