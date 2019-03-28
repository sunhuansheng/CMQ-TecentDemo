package com.hpm.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@MapperScan("com.hpm.blog.mapper")
@EnableCaching
public class SpringBootBlogApplication {

    public static void main(String[] args) {

        SpringApplication.run(SpringBootBlogApplication.class, args);
    }
}
