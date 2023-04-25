package org.sijinghua.shop.user.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.sijinghua.shop.bean.User;
import org.sijinghua.shop.user.mapper.UserMapper;
import org.sijinghua.shop.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserById(Long userId) {
        return userMapper.selectById(userId);
    }

    @Async
    @Override
    public void async() {
        log.info("执行了异步任务...");
    }
}
