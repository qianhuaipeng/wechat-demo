package com.eastrobot.robotdev.data.poetry.service;

import com.alibaba.fastjson.JSONObject;
import com.eastrobot.robotdev.BaseTest;
import com.eastrobot.robotdev.data.poetry.domain.Poetry;
import com.eastrobot.robotdev.data.poetry.repository.PoetryRepository;
import com.eastrobot.robotdev.data.poetry.utils.PoetryUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.regex.Pattern;

/**
 * author: alan.peng
 * description:
 * date: create in 15:45 2018/7/6
 * modified By：
 */
public class PoetryServiceTest  extends BaseTest {

    @Autowired
    private PoetryRepository poetryRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private PoetryService poetryService;

    @Test
    public void save(){
        Document document = PoetryUtils.getDocument(PoetryUtils.SHI_JING_URL);
        System.out.println(document.title());
        Elements divs = document.select("div.sons");
        for (Element element : divs) {
            //System.out.println(element.html());
            Elements spans = element.select("div.typecont span");
            for (Element element1: spans) {
                String href = element1.select("a").attr("abs:href");
                System.out.println(href);
                if (StringUtils.isNotBlank(href)){
                    Document document1 = PoetryUtils.getDocument(href);
                    String title = document1.select(".cont h1").html();
                    String content = document1.select(".contson").first().html();
                    String comment = document1.select(".contyishang").first().select("p").first().html();
                    comment = comment.replaceAll("<[^>]*>","");
                    content = content.replaceAll("<[^>]*>","");
                    System.out.println(comment);
                    System.out.println(content);
                    Poetry poetry = new Poetry();
                    poetry.setTitle(title);
                    poetry.setContent(content.replace("<br>",""));
                    poetry.setComment(comment.replace("<br>","\n").replace("译文","").replaceFirst("\n",""));
                    poetryService.save(poetry);
                    //break;
                }
            }
        }
    }

    @Test
    public void findByName(){
        Poetry poetry = poetryService.findByTitle("s关雎");
        System.out.println(JSONObject.toJSONString(poetry));
    }

    @Test
    public void findByTitleLike(){
        String keywords = "关雎";
        //模糊匹配
        Pattern pattern = Pattern.compile("^.*"+keywords+".*$");
        Query query = Query.query(Criteria.where("title").regex(pattern));
        //Query query = Query.query(Criteria.where("title").is(keywords));
        List<Poetry> poetry = mongoTemplate.find(query,Poetry.class);
        System.out.println(JSONObject.toJSONString(poetry));
    }
    @Test
    public void saves(){
        Poetry poetry = new Poetry();
        poetry.setTitle("你好1");
        poetry.setContent("nihao");
        poetryService.save(poetry);
    }

    @Test
    public void update(){
      List<Poetry> poetryList =  poetryRepository.findAll();
        for (int i = 0; i < poetryList.size(); i++) {
            Poetry poetry = poetryList.get(i);
            poetry.setContent(poetry.getContent().replace("<br>",""));
            poetry.setComment(poetry.getComment().replace("<br>",""));
            poetryService.save(poetry);
        }
    }

    @Test
    public void del(){
        poetryRepository.deleteAll();
    }
}
