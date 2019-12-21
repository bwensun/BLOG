package com.bwensun.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bwensun.blog.domain.User;
import com.bwensun.blog.service.UserService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 郑建雄
 * @date 2019/12/14
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Reference
    UserService userService;

    @RequestMapping("userList")
    public PageInfo<User> getUserList(){
        return userService.getUserList();
    }
}
