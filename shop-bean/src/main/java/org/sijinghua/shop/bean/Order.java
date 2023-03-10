package org.sijinghua.shop.bean;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import org.sijinghua.shop.utils.id.SnowFlakeFactory;

import java.math.BigDecimal;

@Data
@TableName("t_order")
public class Order {
    private static final long serialVersionUID = -2907409980909070073L;

    /**
     * 数据id
     */
    @TableId(value = "id", type = IdType.INPUT)
    @TableField(value = "id", fill = FieldFill.INSERT)
    private Long id;

    /**
     * 用户id
     */
    @TableField("t_user_id")
    private Long userId;

    /**
     * 用户名称
     */
    @TableField("t_user_name")
    private String userName;

    /**
     * 手机号
     */
    @TableField("t_phone")
    private String phone;

    /**
     * 地址
     */
    @TableField("t_address")
    private String address;

    /**
     * 商品价格（总价）
     */
    @TableField("t_total_price")
    private BigDecimal totalPrice;

    public Order() {
        this.id = SnowFlakeFactory.getSnowFlakeFromCache().nextId();
    }
}
