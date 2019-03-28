package com.hpm.blog.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hpm.blog.service.RedisUserService;
import com.hpm.blog.model.RedisUser;

@RestController
public class RedisUserApi {

    @Autowired
    RedisUserService redisUserService;
    //查找redis中数据
    @GetMapping("/api/Redis/findRedisUser")
    public RedisUser finduser() {
        return redisUserService.findUser("lisi");
    }
    //增加及修改redis数据
    @GetMapping("/api/Redis/addRedisUser")
    public RedisUser adduser() {
        RedisUser redisUser = new RedisUser();
        redisUser.setName("lisi1");
        redisUser.setAge("100");
        redisUser.setId(2);
        return redisUserService.saveUser(redisUser);
    }
    //删除redis数据
    @GetMapping("/api/Redis/deleteRedisUser")
    public void removeUser() {
        redisUserService.removeUser("lisi1");
    }
}
