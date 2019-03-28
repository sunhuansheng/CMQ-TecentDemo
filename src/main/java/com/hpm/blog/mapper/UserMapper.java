package com.hpm.blog.mapper;

import com.hpm.blog.model.User;

import java.util.List;

//mapper层：操作数据库
public interface UserMapper {
//用户注册
    int add(User user);
//查找用户是否已存在
    User findOne(User user);
//更新用户密码
    void updatePassword(User user);
//查询所有用户信息
    List<User> findAll();
//删除用户
    int deleteUser(User user);
}
