package com.eastrobot.robotdev.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * author: alan.peng
 * description:
 * date: create in 11:15 2018/7/4
 * modified By：
 */
public class JsoupUtils {

    public static String parseUrl(String url){
        try {
            Document document = Jsoup.connect(url).get();
            System.out.println(document.title());
            Elements divs = document.select("div.sons");
            for (Element element : divs) {
                //System.out.println(element.html());
                Elements spans = element.select("div.typecont span");
                for (Element element1: spans) {
                    System.out.println(element1.select("a").attr("abs:href"));

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static Document getDocument(String url){
        try {
            Document document = Jsoup.connect(url).get();
            return document;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
