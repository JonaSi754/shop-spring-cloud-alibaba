package org.sijinghua.shop.order.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.sijinghua.shop.order.service.OrderService;
import org.sijinghua.shop.params.OrderParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping(value = "submit_order")
    public String submitOrder(OrderParams orderParams) {
        log.info("提交订单时传递的参数: {}", JSONObject.toJSONString(orderParams));
        orderService.saveOrder(orderParams);
        return "success";
    }
}
