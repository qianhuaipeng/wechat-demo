package com.eastrobot.robotdev.utils;

import org.junit.Test;

/**
 * author: alan.peng
 * description:
 * date: create in 11:25 2018/7/4
 * modified By：
 */
public class JsoupUtilsTest {

    @Test
    public void test(){
        String url = "https://so.gushiwen.org/gushi/shijing.aspx";
        JsoupUtils.parseUrl(url);
    }
}
