package com.example.demo.user.controller;

import com.example.demo.annotations.AccessLimit;
import com.example.demo.common.dto.BaseResponse;
import com.example.demo.user.dto.response.LoginResponse;
import com.example.demo.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.UUID;

@AccessLimit(seconds = 2, maxCount = 10)
@RestController
@RequestMapping("user")
@Api(description = "基础 -- 用户")
public class UserController {
    @Resource
    private UserService userService;

    @AccessLimit//默认一个客户端在5秒内最多允许调用5次
    @ApiOperation(value = "登录", notes = "用户调用接口限流测试")
    @RequestMapping(value = "/login", method = {RequestMethod.POST}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public BaseResponse<LoginResponse> login() {
        BaseResponse<LoginResponse> baseResponse = new BaseResponse<>();
        LoginResponse loginResponse = userService.login();
        if(loginResponse != null){
            baseResponse.setCode(0);
            baseResponse.setMsg("登录成功");
            baseResponse.setData(loginResponse);
        }else{
            baseResponse.setCode(1);
            baseResponse.setMsg("用户名或密码不正确");
        }
        return baseResponse;
    }

    @AccessLimit(maxCount = 5)//同一个客户端60秒内最多允许调用接口6次
    @ApiOperation(value = "注册", notes = "用户调用接口限流测试")//swagger注释
    @RequestMapping(value = "/register", method = {RequestMethod.POST}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public BaseResponse<Object> register() {
        BaseResponse<Object> baseResponse = new BaseResponse<>();
        Integer flag = userService.register();
        if(flag == 0){
            baseResponse.setCode(0);
            baseResponse.setMsg("注册成功");
        }else if(flag == 1){
            baseResponse.setCode(1);
            baseResponse.setMsg("手机号已被注册");
        }else{
            baseResponse.setCode(2);
            baseResponse.setMsg("邮箱已被注册");
        }
        baseResponse.setData(UUID.randomUUID());
        return baseResponse;
    }

    @ApiOperation(value = "网站限流测试", notes = "网站限流测试")//swagger注释
    @RequestMapping(value = "/rateLimiterTest", method = {RequestMethod.GET}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public BaseResponse<Object> rateLimiterTest() {
        BaseResponse<Object> baseResponse = new BaseResponse<>();
        userService.register();
        userService.login();
        baseResponse.setCode(0);
        baseResponse.setMsg("success");
        baseResponse.setData(UUID.randomUUID());
        return baseResponse;
    }
}
