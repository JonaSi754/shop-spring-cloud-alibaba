package org.sijinghua.shop.order.feign.fallback;

import org.sijinghua.shop.bean.User;
import org.sijinghua.shop.order.feign.UserService;
import org.springframework.stereotype.Component;

@Component
public class UserServiceFallBack implements UserService {
    @Override
    public User getUser(Long uid) {
        User user = new User();
        user.setId(-1L);
        return user;
    }
}
