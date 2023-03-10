package org.sijinghua.shop.utils.exception;

import org.sijinghua.shop.utils.constants.HttpCode;
import org.sijinghua.shop.utils.resp.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestCtrlExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(RestCtrlExceptionHandler.class);

    /**
     * 全局异常处理，统一返回状态码
     */
    @ExceptionHandler(Exception.class)
    public Result<String> handleException(Exception e) {
        logger.error("服务器抛出异常: ", e);
        return new Result<>(HttpCode.FAILURE, "执行失败", e.getMessage());
    }
}
