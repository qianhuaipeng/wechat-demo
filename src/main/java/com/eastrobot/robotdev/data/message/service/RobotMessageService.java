package com.eastrobot.robotdev.data.message.service;

import com.eastrobot.robotdev.data.message.domain.RobotMessage;
import org.springframework.data.domain.Page;

/**
 * author: alan.peng
 * description:
 * date: create in 18:49 2018/7/4
 * modified By：
 */
public interface RobotMessageService {

    public void save(RobotMessage robotMessage);

    public RobotMessage findById(String id);

    public RobotMessage findByQuestion(String question);

    public Page<RobotMessage> findByNameLike(String name);


}
