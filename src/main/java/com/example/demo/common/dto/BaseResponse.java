package com.example.demo.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class BaseResponse<T> {
    @ApiModelProperty(value = "状态码，0:成功, -1:token过期, -2:参数错误, -3:其他已知错误, -4:其他未知错误。 其他状态码见方法说明", example = "-2", required = true)
    private int code = -4;
    @ApiModelProperty(value = "处理结果描述", example = "参数错误")
    private String msg = "未知错误";
    @ApiModelProperty(value = "详细数据")
    private T data;

    public void ok(T data){
        this.code = 0;
        this.msg = "success";
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
