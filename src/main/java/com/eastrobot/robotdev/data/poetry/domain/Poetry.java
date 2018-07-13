package com.eastrobot.robotdev.data.poetry.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * author: alan.peng
 * description:
 * date: create in 18:08 2018/7/4
 * modified By：
 */

@Document(collection = "poetry")
public class Poetry {

    @Id
    @Indexed
    private String title;
    private String era;
    private String content;
    private String comment;
    private String appreciate;
    private String background;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEra() {
        return era;
    }

    public void setEra(String era) {
        this.era = era;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getAppreciate() {
        return appreciate;
    }

    public void setAppreciate(String appreciate) {
        this.appreciate = appreciate;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public class Main {
        private String title;
        private String era;
        private String content;
    }

    public class Comment {

    }
}
