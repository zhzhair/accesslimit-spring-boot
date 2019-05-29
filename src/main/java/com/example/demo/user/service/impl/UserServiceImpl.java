package com.example.demo.user.service.impl;

import com.example.demo.common.exception.BusinessException;
import com.example.demo.user.dto.request.RegisterRequest;
import com.example.demo.user.dto.response.LoginResponse;
import com.example.demo.user.service.UserService;
import com.example.demo.util.StringTools;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Random;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public LoginResponse login() {
        int k = Math.abs(UUID.randomUUID().hashCode())%16;
        if(k == 0){
            throw new BusinessException("用户不存在");
        }
        if(k > 0){
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setUserId(k);
            loginResponse.setExpire(System.currentTimeMillis() + 3600 * 1000L);
            loginResponse.setToken(UUID.randomUUID().toString());
            try {
                Thread.sleep(new Random().nextInt(500));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return loginResponse;
        }
        return null;
    }

    @Override
    public Integer register() {
        int k = Math.abs(UUID.randomUUID().hashCode())%16;
        if(k > 0){
            int a = new Random().nextInt(10);
            if(a > 0){
                RegisterRequest registerRequest = new RegisterRequest();
                String password = DigestUtils.md5DigestAsHex("123456".getBytes());
                registerRequest.setPassword(password);
                String mobile = StringTools.getMobileStr();
                registerRequest.setName("user"+mobile);
                registerRequest.setMobile(mobile);
                registerRequest.setEmail("asdf@qq.com");
                try {
                    Thread.sleep(new Random().nextInt(500));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return 0;
            }
            return 2;
        }
        return 1;
    }

}
