package org.sijinghua.shop.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.sijinghua.shop.bean.Product;

public interface ProductMapper extends BaseMapper<Product> {

    /**
     * 扣减商品库存
     */
    int updateProductStockById(@Param("count") Integer count, @Param("id") Long id);
}
