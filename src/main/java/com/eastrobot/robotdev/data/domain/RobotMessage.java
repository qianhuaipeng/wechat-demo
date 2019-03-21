package com.eastrobot.robotdev.data.domain;

import com.eastrobot.robotdev.config.BaseBean;
import lombok.Data;


/**
 * author: alan.peng
 * description:
 * date: create in 17:04 2018/7/4
 * modified By：
 */
@Data
public class RobotMessage extends BaseBean{

    private String id;
    private String question;
    private String content;
    private int type;
    private Object object;




}
