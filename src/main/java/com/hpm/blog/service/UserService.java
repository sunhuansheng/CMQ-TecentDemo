package com.hpm.blog.service;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.hpm.blog.mapper.UserMapper;
import com.hpm.blog.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Calendar;
import java.util.Date;

//服务层
@Service
public class UserService {
    private UserMapper userMapper;

    @Autowired
    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public User addUser(User user) {
        String passwordHash =  passwordToHash(user.getPassword());
        user.setPassword(passwordHash);
        userMapper.add(user);
        return findByUserId(user.getId());
    }
    public User updatePassword(User user) {
        userMapper.updatePassword(user);
        return user;
    }

    public List<User> allUser() {
        List<User>  userList =  userMapper.findAll();
        return userList;
    }

    public int deleteUser(User user) {
        int result =   userMapper.deleteUser(user);
        return result;
    }

    public String passwordToHash(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(password.getBytes());
            byte[] src = digest.digest();
            StringBuilder stringBuilder = new StringBuilder();
            for (byte aSrc : src) {
                String s = Integer.toHexString(aSrc & 0xFF);
                if (s.length() < 2) {
                    stringBuilder.append('0');
                }
                stringBuilder.append(s);
            }
            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException ignore) {
        }
        return null;
    }

    public User findByUserId(int id) {
        User user = new User();
        user.setId(id);
        return userMapper.findOne(user);
    }

    public User findByUserName(String name) {
        User param = new User();
        param.setName(name);
        return userMapper.findOne(param);
    }

    public boolean comparePassword(User user, User userInDataBase) {
        return passwordToHash(user.getPassword())
                .equals(userInDataBase.getPassword());
    }

    public String getToken(User user) {
        String token = "";
        //日期转字符串
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND,30 ); //特定时间的年后
        Date date = calendar.getTime();
        long currentTimeMillis = System.currentTimeMillis();
        long expireTime = 1 * 60 * 60 * 1000;
        Date expireDate = new Date(currentTimeMillis + expireTime);

        try {
            token = JWT.create()
                    .withAudience(user.getId().toString())          // 将 user id 保存到 token 里面
                    .withIssuedAt(new Date(currentTimeMillis))      // 签名时间
                   .withExpiresAt(expireDate)                       // 过期时间
                    .sign(Algorithm.HMAC256(user.getPassword()));   // 以 password 作为 token 的密钥
        } catch (UnsupportedEncodingException ignore) {
        }
        return token;
    }
}
