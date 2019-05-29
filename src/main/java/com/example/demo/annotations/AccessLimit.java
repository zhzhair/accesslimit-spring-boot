package com.example.demo.annotations;

import com.example.demo.constant.CountType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 防刷注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.TYPE})
public @interface AccessLimit {
    int seconds() default CountType.seconds;//统计接口调用次数的时间
    int maxCount() default CountType.maxCount;//最大调用次数
}
