package com.eastrobot.robotdev.data.message.service;

import com.alibaba.fastjson.JSONObject;
import com.eastrobot.robotdev.BaseTest;
import com.eastrobot.robotdev.data.message.domain.RobotMessage;
import com.eastrobot.robotdev.data.message.repository.RobotMessageRepository;
import com.eastrobot.robotdev.data.poetry.domain.Poetry;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;

/**
 * author: alan.peng
 * description:
 * date: create in 18:52 2018/7/4
 * modified By：
 */
public class RobotMessageServiceImplTest extends BaseTest {

    @Autowired
    private RobotMessageService robotMessageService;

    @Autowired
    private RobotMessageRepository robotMessageRepository;



    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void save(){
        RobotMessage robotMessage = new RobotMessage();
        robotMessage.setId("1231qq23111ssss1");
        robotMessage.setContent("你好");
        robotMessage.setQuestion("你好");
        Poetry poetry = new Poetry();
        poetry.setTitle("test");
        poetry.setContent("test");
        poetry.setComment("test");
        robotMessage.setObject(poetry);

        robotMessageService.save(robotMessage);
//        mongoTemplate.save(robotMessage);
    }

    @Test
    public void findAll(){
        robotMessageRepository.findAll();
    }
    @Test
    public void findById(){
        RobotMessage robotMessage = robotMessageService.findById("1231qq23111ssss1");
        System.out.println(JSONObject.toJSONString(robotMessage));
    }

    @Test
    public void findByName(){
        try {
            List<RobotMessage> list = robotMessageRepository.findByQuestionLike("你");
            System.out.println(JSONObject.toJSONString(list));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void query(){
        Query query = new Query();
        Criteria criteria = where("id").gt("12312311");
        query.addCriteria(criteria);
        RobotMessage robotMessage = mongoTemplate.findById("12312311",RobotMessage.class);
        System.out.println(JSONObject.toJSONString(robotMessage));
    }
}
