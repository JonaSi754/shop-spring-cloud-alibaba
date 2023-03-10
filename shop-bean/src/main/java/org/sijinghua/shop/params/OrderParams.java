package org.sijinghua.shop.params;

import lombok.Data;

/**
 * 订单参数
 */
@Data
public class OrderParams {
    /**
     * 商品id
     */
    private Long productId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 购买的商品数量
     */
    private Integer count;

    public boolean isEmpty() {
        return (productId == null || productId <= 0) ||
                (userId == null || userId <= 0) ||
                (count == null || count <= 0);
    }
}
