package com.eastrobot.robotdev.service;

import com.eastrobot.robotdev.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * author: alan.peng
 * description:
 * date: create in 17:24 2018/7/6
 * modified By：
 */
public class WeixinServiceTest extends BaseTest {

    @Autowired
    public WeixinService weixinService;

    @Test
    public void sendMsg(){
        String str = weixinService.sendMsg("葛覃","test","app");
        System.out.println(str);
    }
}
