package org.sijinghua.shop.order.feign;

import org.sijinghua.shop.bean.Product;
import org.sijinghua.shop.order.feign.fallback.ProductServiceFallBack;
import org.sijinghua.shop.order.feign.fallback.factory.ProductServiceFallBackFactory;
import org.sijinghua.shop.utils.resp.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 调用商品微服务的接口
 */
// @FeignClient(value = "server-product", fallback = ProductServiceFallBack.class)
@FeignClient(value = "server-product", fallbackFactory = ProductServiceFallBackFactory.class)
public interface ProductService {

    /**
     * 获取商品信息
     *
     * @param pid 商品id
     * @return 商品基本信息
     */
    @GetMapping("/product/get/{pid}")
    Product getProduct(@PathVariable("pid")Long pid);

    /**
     * 更新库存数量
     *
     * @param pid 商品id
     * @param count 操作数额
     * @return 操作结果
     */
    @GetMapping("/product/update_count/{pid}/{count}")
    Result<Integer> updateCount(@PathVariable("pid")Long pid, @PathVariable("count")Integer count);
}
