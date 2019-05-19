package com.lc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication//springboot启动器注解
@ComponentScan(basePackages = {"com.lc.config","com.lc.service"})
@EnableCaching
public class Starter {
public static void main(String[] args) {
	SpringApplication.run(Starter.class, args);
}
}
