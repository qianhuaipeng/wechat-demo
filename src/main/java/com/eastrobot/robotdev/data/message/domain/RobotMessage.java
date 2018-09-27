package com.eastrobot.robotdev.data.message.domain;

import com.eastrobot.robotdev.config.BaseBean;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


/**
 * author: alan.peng
 * description:
 * date: create in 17:04 2018/7/4
 * modified By：
 */
@Document(collection = "robot_message")
@Data
public class RobotMessage extends BaseBean{

    @Id
    private String id;
    private String question;
    @Indexed
    private String content;
    private int type;
    private Object object;




}
