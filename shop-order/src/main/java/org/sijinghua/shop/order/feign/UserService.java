package org.sijinghua.shop.order.feign;

import org.sijinghua.shop.bean.User;
import org.sijinghua.shop.order.feign.fallback.UserServiceFallBack;
import org.sijinghua.shop.order.feign.fallback.factory.UserServiceFallBackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 调用用户微服务的接口
 */
// @FeignClient(value = "server-user", fallback = UserServiceFallBack.class)
@FeignClient(value = "server-user", fallbackFactory = UserServiceFallBackFactory.class)
public interface UserService {
    /**
     * 获取用户信息
     *
     * @param uid 用户id
     * @return 用户基本信息
     */
    @GetMapping("/user/get/{uid}")
    User getUser(@PathVariable("uid")Long uid);
}
