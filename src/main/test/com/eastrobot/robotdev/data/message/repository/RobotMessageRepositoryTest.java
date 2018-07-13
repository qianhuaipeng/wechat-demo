package com.eastrobot.robotdev.data.message.repository;

import com.alibaba.fastjson.JSONObject;
import com.eastrobot.robotdev.BaseTest;
import com.eastrobot.robotdev.data.message.domain.RobotMessage;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * author: alan.peng
 * description:
 * date: create in 13:52 2018/7/6
 * modified By：
 */
public class RobotMessageRepositoryTest extends BaseTest {

    @Autowired
    private RobotMessageRepository robotMessageRepository;

    @Test
    public void findByQuesionLike() {
        List<RobotMessage> list = robotMessageRepository.findByQuestionLike("你好");
        System.out.println(JSONObject.toJSONString(list));
    }

    @Test
    public void findByQuestion(){
        RobotMessage robotMessage = robotMessageRepository.findByQuestion("你好");
        System.out.println(JSONObject.toJSONString(robotMessage));
    }

    @Test
    public void delete(){
    //    robotMessageRepository.deleteAll();
    }

}
