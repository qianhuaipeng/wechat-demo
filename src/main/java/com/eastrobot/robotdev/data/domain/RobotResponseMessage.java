package com.eastrobot.robotdev.data.domain;

/**
 * author: alan.peng
 * description:
 * date: create in 10:57 2018/7/30
 * modified By：
 */
public class RobotResponseMessage {

    private int type = -1;
    private String content;
    private String nodeId;
    private String moduleId;
    private String[] tags;
    private float similarity;
    //private RobotCommand[] commands;
    private String[] relatedQuestions;
}
