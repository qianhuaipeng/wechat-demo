package com.eastrobot.robotdev.data.poetry.service;

import com.eastrobot.robotdev.data.poetry.domain.Poetry;
import com.eastrobot.robotdev.data.poetry.repository.PoetryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author: alan.peng
 * description:
 * date: create in 18:42 2018/7/4
 * modified By：
 */
@Service
public class PoetryServiceImpl implements PoetryService {

    @Autowired
    private PoetryRepository poetryRepository;

    @Override
    public void save(Poetry poetry) {
        poetryRepository.save(poetry);
    }

    @Override
    public Poetry findByTitle(String title) {

        return poetryRepository.findByTitleLike(title);
    }
}
