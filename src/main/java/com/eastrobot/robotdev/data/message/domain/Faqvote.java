package com.eastrobot.robotdev.data.message.domain;

import lombok.Data;

/**
 * author: alan.peng
 * description:
 * date: create in 18:33 2018/7/30
 * modified By：
 */
@Data
public class Faqvote {

    private String solve;
    private String unsolve;
    private String solve_icon="icon_yjj.png";
    private String solve_icon_clicked = "icon_yjjh.png";
    private String unsolve_icon = "icon_wjj.png";
    private String unsolve_icon_clicked= "icon_wjjh.png";
    private boolean isClick = false;
}
