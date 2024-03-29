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

/**
 * 不再从固定的url获取服务，该从注册中心获取服务地址
 */
@Service("OrderServiceV2")
@Slf4j
public class OrderServiceV2Impl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    private String userServer = "server-user";
    private String productServer = "server-product";

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrder(OrderParams orderParams) {
        if (orderParams.isEmpty()) {
            throw new RuntimeException("参数异常: " + JSONObject.toJSONString(orderParams));
        }

        // 从Nacos服务中获取用户服务和商品服务的地址
        String userUrl = this.getServiceUrl(userServer);
        String productUrl = this.getServiceUrl(productServer);

        User user = restTemplate.getForObject("http://" + userUrl + "/user/get/" + orderParams.getUserId(), User.class);
        if (user == null) {
            throw new RuntimeException("未获取到用户信息: " + JSONObject.toJSONString(orderParams));
        }
        Product product = restTemplate.getForObject("http://" + productUrl + "/product/get/" + orderParams.getProductId(), Product.class);
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

        Result<Integer> result = restTemplate.getForObject("http://" + productUrl + "/product/update_count/" +
                orderParams.getProductId() + "/" + orderParams.getCount(), Result.class);
        if (result == null || result.getCode() != HttpCode.SUCCESS) {
            throw new RuntimeException("库存扣减失败");
        }
        log.info("库存扣减成功");
    }

    private String getServiceUrl(String serviceName) {
        ServiceInstance serviceInstance = discoveryClient.getInstances(serviceName).get(0);
        return serviceInstance.getHost() + ":" + serviceInstance.getPort();
    }
}
