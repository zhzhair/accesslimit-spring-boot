package com.example.demo.user.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@ApiModel
public class RegisterRequest {
    @ApiModelProperty(value = "手机号",required = true,example = "18201481809")
    @Size(min = 11,max = 11)
    //    @Pattern(regexp="",message = "手机号格式不对")
    private String mobile;
    @ApiModelProperty(value = "邮箱",required = true,example = "495359612@qq.com")
    @NotBlank
    private String email;
    @ApiModelProperty(value = "密码",required = true,example = "123456")
    @NotBlank
    private String password;
    @ApiModelProperty(value = "姓名",required = true,example = "隔壁小王")
    @NotBlank
    private String name;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
