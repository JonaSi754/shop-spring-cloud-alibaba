package org.sijinghua.shop.user.service.impl;

import org.sijinghua.shop.bean.User;
import org.sijinghua.shop.user.mapper.UserMapper;
import org.sijinghua.shop.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserById(Long userId) {
        return userMapper.selectById(userId);
    }
}
