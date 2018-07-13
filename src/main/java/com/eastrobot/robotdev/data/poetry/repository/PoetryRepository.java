package com.eastrobot.robotdev.data.poetry.repository;

import com.eastrobot.robotdev.data.poetry.domain.Poetry;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * author: alan.peng
 * description:
 * date: create in 18:40 2018/7/4
 * modified By：
 */
@Repository
public interface PoetryRepository extends MongoRepository<Poetry,String>{

    public Poetry findByTitle(String title);

    public Poetry findByTitleLike(String title);
}
