package com.bwensun.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bwensun.blog.base.exception.BusinessException;
import com.bwensun.blog.service.TestService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.http.HTTPException;
import java.util.concurrent.ExecutionException;

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

    @RequestMapping
    public void exceptionTest(){
        throw new BusinessException("-1", "错误");
    }
}
