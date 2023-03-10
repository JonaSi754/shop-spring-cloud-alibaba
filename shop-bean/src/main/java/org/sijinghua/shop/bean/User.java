package org.sijinghua.shop.bean;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import org.sijinghua.shop.utils.id.SnowFlakeFactory;

import java.io.Serializable;

@Data
@TableName("t_user")
public class User implements Serializable {
    private static final long serialVersionUID = -7032479567987350240L;

    /**
     * 数据id
     */
    @TableId(value = "id", type = IdType.INPUT)
    @TableField(value = "id", fill = FieldFill.INSERT)
    private Long id;

    /**
     * 用户名
     */
    @TableField("t_username")
    private String username;

    /**
     * 密码
     */
    @TableField("t_password")
    private String password;

    /**
     * 电话号码
     */
    @TableField("t_phone")
    private String phone;

    /**
     * 地址
     */
    @TableField("t_address")
    private String address;

    public User() {
        this.id = SnowFlakeFactory.getSnowFlakeFromCache().nextId();
        // TODO 默认密码
        this.password = "123456";
    }
}
