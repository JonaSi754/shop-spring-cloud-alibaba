package org.sijinghua.shop.product.service;

import org.sijinghua.shop.bean.Product;

public interface ProductService {
    /**
     * 根据商品id获取商品信息
     */
    Product getProductById(Long pid);

    /**
     * 扣减商品库存
     */
    int updateProductStockById(Integer count, Long id);
}
