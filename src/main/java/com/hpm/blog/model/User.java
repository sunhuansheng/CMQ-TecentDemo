package com.hpm.blog.model;

import com.hpm.blog.mapper.*;

//
public class User {
    private Integer id;
    private String name;
    private String password;
    private String newPassword;



    // 下面是 getter 和 setter 方法。。。
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {return name; }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
