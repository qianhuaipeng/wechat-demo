package com.eastrobot.robotdev.data.message.repository;

import com.eastrobot.robotdev.data.message.domain.RobotMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * author: alan.peng
 * description:
 * date: create in 18:48 2018/7/4
 * modified By：
 */
@Repository
public interface RobotMessageRepository extends MongoRepository<RobotMessage,String> {

    public Page<RobotMessage> findByQuestion(String keyword, Pageable pageable);

    public List<RobotMessage> findByQuestionLike(String question);

    public RobotMessage findByQuestion(String question);
}
