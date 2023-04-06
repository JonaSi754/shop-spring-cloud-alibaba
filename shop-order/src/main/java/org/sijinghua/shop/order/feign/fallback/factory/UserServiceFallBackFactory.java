package org.sijinghua.shop.order.feign.fallback.factory;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.sijinghua.shop.bean.User;
import org.sijinghua.shop.order.feign.UserService;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserServiceFallBackFactory implements FallbackFactory<UserService> {
    @Override
    public UserService create(Throwable cause) {
        log.error("触发了用户容错逻辑: ", cause);
        return new UserService() {
            @Override
            public User getUser(Long uid) {
                User user = new User();
                user.setId(-1L);
                return user;
            }
        };
    }
}
