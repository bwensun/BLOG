package com.bwensun.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.bwensun.blog.service.TestService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @author 郑建雄
 * @date 2019/12/14
 */
@Component
@Service
public class TestServiceImpl implements TestService {

    @Override
    public String sayHello() {
        return "Hello Dubbo!";
    }


}
