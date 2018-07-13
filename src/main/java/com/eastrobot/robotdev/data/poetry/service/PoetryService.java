package com.eastrobot.robotdev.data.poetry.service;

import com.eastrobot.robotdev.data.poetry.domain.Poetry;

/**
 * author: alan.peng
 * description:
 * date: create in 18:42 2018/7/4
 * modified By：
 */
public interface PoetryService {

    public void save(Poetry poetry);

    public Poetry findByTitle(String title);
}
