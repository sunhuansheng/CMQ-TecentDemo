package com.hpm.blog.api;


import com.alibaba.fastjson.JSONObject;
import com.hpm.blog.model.User;
import com.hpm.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.hpm.blog.annotation.PassToken;
import com.hpm.blog.annotation.UserLoginToken;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

//控制层:用户注册
@RestController
public class UserApi {
    private UserService userService;

    @Autowired
    public UserApi(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/api/addUser")
    @PostMapping("")
    public Object add(@RequestBody User user) {
        JSONObject jsonObject = new JSONObject();
        if (userService.findByUserName(user.getName()) != null) {
            jsonObject.put("error","用户名重复");
            return jsonObject;
        }else {
            userService.addUser(user);
            String token = userService.getToken(user);
            jsonObject.put("token", token);
            jsonObject.put("result",0);
            return jsonObject;
        }


    }
//    public Object add(HttpServletRequest request) {
//        String name =request.getParameter("name");
//        String password =request.getParameter("password");
//        User user = new User();
//        user.setPassword(password);
//        user.setName(name);
//        if (userService.findByName(user.getName()) != null) {
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("error","用户名重复");
//            return jsonObject;
//        }
//        return userService.add(user);
//    }

    @RequestMapping("/api/authenticationUser")
    @PostMapping("")
    public Object login(@RequestBody User user) {
        User userInDataBase = userService.findByUserName(user.getName());
        JSONObject jsonObject = new JSONObject();
        if (userInDataBase == null) {
            jsonObject.put("message", "用户不存在");
        } else if (!userService.comparePassword(user, userInDataBase)) {
            jsonObject.put("message", "密码不正确");
        } else {
            String token = userService.getToken(userInDataBase);
            jsonObject.put("token", token);
            jsonObject.put("result",0);
        }
        return jsonObject;
    }

    @UserLoginToken
    @GetMapping("/api/tokenLogin")
    public Object getMessage(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message","你已通过验证");
        jsonObject.put("result",0);
        return jsonObject;
    }

    @RequestMapping("/api/updateUser")
    @PostMapping("")
    public Object updateUser(@RequestBody User user) {
        User userInDataBase = userService.findByUserName(user.getName());
        JSONObject jsonObject = new JSONObject();
        if (userInDataBase == null) {
            jsonObject.put("message", "用户不存在");
        } else if (!userService.comparePassword(user, userInDataBase)) {
            jsonObject.put("message", "密码不正确");
        } else if (user.getPassword().equals(user.getNewPassword())) {
            jsonObject.put("message", "新旧密码不允许相同");
        }else {
            String passwordHash =  userService.passwordToHash(user.getNewPassword());
            user.setPassword(passwordHash);
            user.setId(userInDataBase.getId());
            userService.updatePassword(user);
            User updateUserInDataBase = userService.findByUserName(userInDataBase.getName());
            String token = userService.getToken(updateUserInDataBase);
            jsonObject.put("token", token);
            jsonObject.put("result",0);
        }
        return jsonObject;
    }

    @RequestMapping("/api/allUser")
    @GetMapping("")
    public Object allUser() {
        List<User> userInDataBase = userService.allUser();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user", userInDataBase);
        jsonObject.put("result",0);
        return jsonObject;
    }

    @RequestMapping("/api/deleteUser")
    @PostMapping("")
    public Object deleteUser(@RequestBody User user) {
        JSONObject jsonObject = new JSONObject();
        User userInDataBase = userService.findByUserName(user.getName());
        user.setId(userInDataBase.getId());
        int result = userService.deleteUser(user);
        if (result == 1){
            jsonObject.put("message", "删除成功");
            jsonObject.put("result",0);
        }else {
            jsonObject.put("message", "删除异常");
            jsonObject.put("result",result);
        }
        return jsonObject;
    }



    @GetMapping("{id}")
    public Object findById(@PathVariable int id) {
        return userService.findByUserId(id);
    }
}
