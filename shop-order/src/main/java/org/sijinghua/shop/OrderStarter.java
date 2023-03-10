package org.sijinghua.shop;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement(proxyTargetClass = true)
@MapperScan(value = "org.sijinghua.shop.order.mapper")
public class OrderStarter {
    public static void main(String[] args) {
        SpringApplication.run(OrderStarter.class, args);
    }
}
