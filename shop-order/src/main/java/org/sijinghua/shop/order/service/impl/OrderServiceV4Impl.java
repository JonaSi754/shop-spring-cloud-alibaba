package org.sijinghua.shop.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.sijinghua.shop.bean.Order;
import org.sijinghua.shop.bean.OrderItem;
import org.sijinghua.shop.bean.Product;
import org.sijinghua.shop.bean.User;
import org.sijinghua.shop.order.mapper.OrderItemMapper;
import org.sijinghua.shop.order.mapper.OrderMapper;
import org.sijinghua.shop.order.service.OrderService;
import org.sijinghua.shop.params.OrderParams;
import org.sijinghua.shop.utils.constants.HttpCode;
import org.sijinghua.shop.utils.resp.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

/**
 * 客户端负载均衡
 * 使用Ribbon实现负载均衡
 */
@Service("OrderServiceV4")
@Slf4j
public class OrderServiceV4Impl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;

    /**
     * 依赖restTemplate中定义的@LoadBalanced注释实现实例的自动选择
     */
    @Autowired
    private RestTemplate restTemplate;
    private String userServer = "server-user";
    private String productServer = "server-product";

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrder(OrderParams orderParams) {
        if (orderParams.isEmpty()) {
            throw new RuntimeException("参数异常: " + JSONObject.toJSONString(orderParams));
        }

        User user = restTemplate.getForObject("http://" + userServer + "/user/get/" + orderParams.getUserId(), User.class);
        if (user == null) {
            throw new RuntimeException("未获取到用户信息: " + JSONObject.toJSONString(orderParams));
        }
        Product product = restTemplate.getForObject("http://" + productServer + "/product/get/" + orderParams.getProductId(), Product.class);
        if (product == null) {
            throw new RuntimeException("未获取到商品信息: " + JSONObject.toJSONString(orderParams));
        }
        if (product.getProStock() < orderParams.getCount()) {
            throw new RuntimeException("商品库存不足: " + JSONObject.toJSONString(orderParams));
        }

        Order order = new Order();
        order.setAddress(user.getAddress());
        order.setPhone(user.getPhone());
        order.setUserId(user.getId());
        order.setUserName(user.getUsername());
        order.setTotalPrice(product.getProPrice().multiply(BigDecimal.valueOf(orderParams.getCount())));
        orderMapper.insert(order);

        OrderItem orderItem = new OrderItem();
        orderItem.setNumber(orderParams.getCount());
        orderItem.setOrderId(order.getId());
        orderItem.setProId(product.getId());
        orderItem.setProName(product.getProName());
        orderItem.setProPrice(product.getProPrice());
        orderItemMapper.insert(orderItem);

        Result<Integer> result = restTemplate.getForObject("http://" + productServer + "/product/update_count/" +
                orderParams.getProductId() + "/" + orderParams.getCount(), Result.class);
        if (result == null || result.getCode() != HttpCode.SUCCESS) {
            throw new RuntimeException("库存扣减失败");
        }
        log.info("库存扣减成功");
    }
}
