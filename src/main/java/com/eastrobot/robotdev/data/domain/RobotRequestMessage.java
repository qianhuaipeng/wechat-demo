package com.eastrobot.robotdev.data.domain;

import lombok.Data;

/**
 * author: alan.peng
 * description:
 * date: create in 10:51 2018/7/30
 * modified By：
 */
@Data
public class RobotRequestMessage {

    private String question;
    private String userId;
    private String format;
    private String platform;
}
