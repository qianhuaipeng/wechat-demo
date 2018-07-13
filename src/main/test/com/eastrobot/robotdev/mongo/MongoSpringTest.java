package com.eastrobot.robotdev.mongo;

import com.alibaba.fastjson.JSONObject;
import com.eastrobot.robotdev.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;

/**
 * author: alan.peng
 * description:
 * date: create in 14:56 2018/7/4
 * modified By：
 */
public class MongoSpringTest extends BaseTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 插入用户信息
     */
    @Test
    public void testAddUser() {
        User zhanggc = new User();
        zhanggc.setName("zhangguochen");
        zhanggc.setAge(29);
        List<String> interests = new ArrayList<String>();
        interests.add("stuty");
        interests.add("hadoop");
        zhanggc.setInterest(interests);
        // 插入数据
        mongoTemplate.insert(zhanggc);
    }


    /**
     * 查询用户信息
     */
    @Test
    public void testQueryUser() {
        // 查询主要用到Query和Criteria两个对象
        Query query = new Query();
        Criteria criteria = where("age").gt(22);    // 大于

        // criteria.and("name").is("cuichongfei");等于
        // List<String> interests = new ArrayList<String>();
        // interests.add("study");
        // interests.add("linux");
        // criteria.and("interest").in(interests); in查询
        // criteria.and("home.address").is("henan"); 内嵌文档查询
        // criteria.and("").exists(false); 列存在
        // criteria.and("").lte(); 小于等于
        // criteria.and("").regex(""); 正则表达式
        // criteria.and("").ne(""); 不等于
        // 多条件查询
        // criteria.orOperator(Criteria.where("key1").is("0"),Criteria.where("key1").is(null));

        query.addCriteria(criteria);
        List<User> userList1 = mongoTemplate.find(query, User.class);
        System.out.println(JSONObject.toJSONString(userList1));
        //printList(userList1);

        // 排序查询sort方法,按照age降序排列
        // query.sort().on("age", Order.DESCENDING);
        // List<User> userList2 = mongoTemplate.find(query, User.class);
        // printList(userList2);

//　　　　　　Query query = new Query();
//　　　　　　query.with(new Sort(Direction.ASC, "priority").and(new Sort(Direction.ASC, "create_time")));




        // 指定字段查询,只查询age和name两个字段
        // query.fields().include("age").include("name");
        // List<User> userList3 = mongoTemplate.find(query, User.class);
        // printList(userList3);

        // 分页查询
        // query.skip(2).limit(3);
        // List<User> userList4 = mongoTemplate.find(query, User.class);
        // printList(userList4);

        // 查询所有
        // printList(mongoTemplate.findAll(User.class));

        // 统计数据量
        // System.out.println(mongoTemplate.count(query, User.class));

    }

    public class User {

        private String name;
        private int age;
        private List<String> interest;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public List<String> getInterest() {
            return interest;
        }

        public void setInterest(List<String> interest) {
            this.interest = interest;
        }
    }
}
