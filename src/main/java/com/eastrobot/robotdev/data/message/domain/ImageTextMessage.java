package com.eastrobot.robotdev.data.message.domain;

import lombok.Data;

/**
 * author: alan.peng
 * description:
 * date: create in 16:26 2018/7/25
 * modified By：
 */
@Data
public class ImageTextMessage {

    private String title;
    private String description;
    private String image;
    private String url;
}
