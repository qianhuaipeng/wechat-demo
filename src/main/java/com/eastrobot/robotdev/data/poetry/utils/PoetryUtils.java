package com.eastrobot.robotdev.data.poetry.utils;

import com.eastrobot.robotdev.utils.JsoupUtils;
import org.jsoup.nodes.Document;

/**
 * author: alan.peng
 * description:
 * date: create in 15:22 2018/7/6
 * modified By：
 */
public class PoetryUtils {
    public static final String SHI_JING_URL = "https://so.gushiwen.org/gushi/shijing.aspx";
    public static final String XIAO_XUE = "https://so.gushiwen.org/gushi/xiaoxue.aspx";



    public static Document getDocument(String url){
        return JsoupUtils.getDocument(url);
    }
}
