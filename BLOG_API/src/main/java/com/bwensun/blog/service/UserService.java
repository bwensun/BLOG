package com.bwensun.blog.service;


import com.bwensun.blog.domain.User;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;

/**
 * @author 郑建雄
 * @date 2019/12/14
 */
@Component
public interface UserService {

    /**
     * 用户信息分页查询
     * @return 分页用户对象
     */
    PageInfo<User> getUserList();
}
