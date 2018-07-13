package com.eastrobot.robotdev.data.message.service;

import com.eastrobot.robotdev.data.message.domain.RobotMessage;
import com.eastrobot.robotdev.data.message.repository.RobotMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * author: alan.peng
 * description:
 * date: create in 18:49 2018/7/4
 * modified By：
 */
@Service
public class RobotMessageServiceImpl implements RobotMessageService {

    @Autowired
    private RobotMessageRepository robotMessageRepository;

    @Override
    public void save(RobotMessage robotMessage) {
        robotMessageRepository.save(robotMessage);
    }

    @Override
    public RobotMessage findById(String id) {
        return robotMessageRepository.findOne(id);
    }

    @Override
    public RobotMessage findByQuestion(String question) {
        return robotMessageRepository.findByQuestion(question);
    }

    @Override
    public Page<RobotMessage> findByNameLike(String name) {
        Pageable pageable = new PageRequest(1, 10);
        try {
            return robotMessageRepository.findByQuestion(name,pageable);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
