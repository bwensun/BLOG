package com.bwensun.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bwensun.blog.service.TestService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 郑建雄
 * @date 2019/12/14
 */
@RestController
@RequestMapping("test")
public class TestController {

    @Reference(check = false)
    TestService testService;

    @RequestMapping("sayHello")
    public String sayHello(){
        return testService.sayHello();
    }
}
