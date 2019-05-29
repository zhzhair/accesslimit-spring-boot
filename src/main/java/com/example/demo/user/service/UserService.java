package com.example.demo.user.service;

import com.example.demo.user.dto.response.LoginResponse;

public interface UserService {

    /**
     * 模拟的登录，测试用的
     *
     * @return 信息
     */
    LoginResponse login();

    /**
     * 模拟的注册，测试用的
     *
     * @return 标记状态
     */
    Integer register();

}
