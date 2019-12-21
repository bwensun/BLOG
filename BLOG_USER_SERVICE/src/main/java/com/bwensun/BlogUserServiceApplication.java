package com.bwensun;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 郑建雄
 * @date 2019/12/14
 */
@SpringBootApplication
@EnableDubbo
public class BlogUserServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogUserServiceApplication.class, args);
    }
}
