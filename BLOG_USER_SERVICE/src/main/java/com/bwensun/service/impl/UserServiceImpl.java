package com.bwensun.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.bwensun.blog.domain.User;
import com.bwensun.blog.service.UserService;
import com.bwensun.repository.UserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 郑建雄
 * @date 2019/12/14
 */
@Component
@Service
public class UserServiceImpl implements UserService {

    @Resource
    UserMapper userMapper;

    @Override
    public PageInfo<User> getUserList() {
        PageHelper.startPage(1, 10);
        List<User> users = userMapper.selectAll();
        return new PageInfo<>(users);
    }



}
