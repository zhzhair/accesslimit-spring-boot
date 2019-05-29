package com.example.demo.configuration;

import com.example.demo.util.WebUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConstantConfig {
    @Bean//每次都获取mac地址非常耗时间，所以初始化mac地址的值
    public Constant getConstant(){
        Constant constant = new Constant();
        constant.setMac(WebUtil.getMACAddress());
        Logger logger = LoggerFactory.getLogger(ConstantConfig.class);
        logger.info("初始化常量成功");
        return constant;
    }
}
