package org.sijinghua.shop.user.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.sijinghua.shop.bean.User;
import org.sijinghua.shop.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/get/{uid}")
    public User getUser(@PathVariable("uid") Long uid) {
        User user = userService.getUserById(uid);
        log.info("获取到的用户信息为：{}", JSONObject.toJSONString(user));
        return user;
    }

    @GetMapping(value = "/api1/demo1")
    public String api1Demo1() {
        log.info("访问了api1Demo1");
        return "api1Demo1";
    }

    @GetMapping(value = "/api1/demo2")
    public String api1Demo2() {
        log.info("访问了api1Demo2");
        return "api1Demo2";
    }

    @GetMapping(value = "/api2/demo1")
    public String api2Demo1() {
        log.info("访问了api2Demo1");
        return "api2Demo1";
    }

    @GetMapping(value = "/api2/demo2")
    public String api2Demo2() {
        log.info("访问了api2Demo2");
        return "api2Demo2";
    }
}
