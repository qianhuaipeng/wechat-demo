package com.eastrobot.robotdev.data.controller;

import com.eastrobot.robotdev.common.BaseController;
import com.eastrobot.robotdev.data.poetry.domain.Poetry;
import com.eastrobot.robotdev.data.poetry.service.PoetryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * author: alan.peng
 * description:
 * date: create in 14:42 2018/7/13
 * modified By：
 */
@RestController
@RequestMapping("data/poetry/")
public class PoetryController extends BaseController {

    @Autowired
    private PoetryService poetryService;

    @RequestMapping("findByTitle")
    public void findByTitle(String title, HttpServletResponse response){
        Poetry poetry = poetryService.findByTitle(title);
        writeJson(response,poetry);
    }

}
