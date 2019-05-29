package com.example.demo.common.exception;

import com.example.demo.common.dto.BaseResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice // 加强Controller
@ResponseBody
public class GlobalExceptionHandler {

	// https://jira.spring.io/browse/SPR-14651
	// 4.3.5 supports RedirectAttributes redirectAttributes	注: Model/ModelMap亲求转发带参数
	@ExceptionHandler(Exception.class) // 全局捕获异常
	public BaseResponse<Object> handleError(Exception e) {
		BaseResponse<Object> baseResponse = new BaseResponse<>();
		if(e instanceof BusinessException){
			baseResponse.setCode(-5);
			baseResponse.setMsg("自定义错误信息：" + e.getMessage());
		}else{
			baseResponse.setCode(-4);
			baseResponse.setMsg("错误信息：" + e.getMessage());
		}
		baseResponse.setData(e.getStackTrace());
		return baseResponse;
	}

}
