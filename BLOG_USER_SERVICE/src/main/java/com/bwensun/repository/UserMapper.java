package com.bwensun.repository;

import com.bwensun.blog.domain.User;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 用户表
 *
 * @author 郑建雄
 * @date 2019-12-18
 */
@Repository
@Mapper
public interface UserMapper {
    /**
     * 根据主键删除用户表记录
     *
     * @param id 主键
     * @return 受影响的记录数
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 新增记录至用户表
     *
     * @param record 记录
     * @return 受影响的记录数
     */
    int insert(User record);

    /**
     * 根据主键查询用户表记录
     *
     * @param id 主键
     * @return 用户表记录
     */
    User selectByPrimaryKey(Integer id);

    /**
     * 查询用户表所有记录
     *
     * @return 用户表记录
     */
    List<User> selectAll();

    /**
     * 根据主键更新用户表记录
     *
     * @param record 记录
     * @return 受影响的记录数
     */
    int updateByPrimaryKey(User record);
}