package com.eastrobot.robotdev.data.message.domain;

import com.eastrobot.robotdev.config.BaseBean;
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
public class RobotMessage extends BaseBean{

    @Id
    private String id;
    private String question;
    @Indexed
    private String content;
    private int type;
    private Object object;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

}
