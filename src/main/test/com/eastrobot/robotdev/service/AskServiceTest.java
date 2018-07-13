package com.eastrobot.robotdev.service;

import com.eastrobot.robotdev.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author alan.peng
 * @date 2017-12-06 23:42
 */
public class AskServiceTest extends BaseTest {

    @Autowired
    private AskService askService;

    @Test
    public void test(){
       String res =  askService.askToXml("test","你好","","ask_text");
        System.out.println(res);
    }
}
