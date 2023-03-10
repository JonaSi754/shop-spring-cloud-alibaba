package org.sijinghua.shop.order.service;

import org.sijinghua.shop.params.OrderParams;

public interface OrderService {
    /**
     * 保存订单
     * @param orderParams 订单参数
     */
    void saveOrder(OrderParams orderParams);
}
