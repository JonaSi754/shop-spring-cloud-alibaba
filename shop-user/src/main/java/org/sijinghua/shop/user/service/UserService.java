package org.sijinghua.shop.user.service;

import org.sijinghua.shop.bean.User;

public interface UserService {
    /**
     * 根据id获取用户信息
     */
    User getUserById(Long userId);
}
