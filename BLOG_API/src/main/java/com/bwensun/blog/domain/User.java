package com.bwensun.blog.domain;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户表
 *
 * @author 郑建雄
 * @date 2019/12/18
 */
@Getter
@Setter
@ToString
public class User implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    private static final long serialVersionUID = 1L;
}