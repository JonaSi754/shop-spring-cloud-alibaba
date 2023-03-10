package org.sijinghua.shop.utils.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1497405107265595284L;

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 状态描述
     */
    private String codeMsg;

    /**
     * 返回数据
     */
    private T data;
}
