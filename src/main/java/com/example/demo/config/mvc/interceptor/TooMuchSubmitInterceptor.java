package com.example.demo.config.mvc.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.annotations.AccessLimit;
import com.example.demo.common.dto.BaseResponse;
import com.example.demo.constant.CountType;
import com.example.demo.util.WebUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStreamWriter;
import java.util.concurrent.TimeUnit;

/**
 * 防止恶意刷接口的拦截器
 */
@Component
public class TooMuchSubmitInterceptor implements HandlerInterceptor {
    @Resource(name = "stringRedisTemplate")
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        HandlerMethod method = (HandlerMethod)o;
        Class<?> clazz = method.getMethod().getDeclaringClass();
        AccessLimit accessLimit = method.getMethodAnnotation(AccessLimit.class);
        if((clazz.getDeclaredAnnotation(RestController.class) != null || clazz.getDeclaredAnnotation(Controller.class) != null) && accessLimit != null){
            AccessLimit accessLimit0 = clazz.getDeclaredAnnotation(AccessLimit.class);
            int seconds = (int)this.getValues(accessLimit0,accessLimit)[0];
            int maxCount = (int)this.getValues(accessLimit0,accessLimit)[1];
            String ip = WebUtil.getRemoteAddr(httpServletRequest);
            String key = httpServletRequest.getServletPath() + ":" + httpServletRequest.getMethod() + ":" + ip;
            String string = redisTemplate.opsForValue().get(key);
            Integer count = Integer.valueOf(string == null? "0":string);
            if(count > maxCount){
                BaseResponse baseResponse = new BaseResponse();
                baseResponse.setCode(-4);
                baseResponse.setMsg("请求过于频繁，请" + seconds + "秒后再试");
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(httpServletResponse.getOutputStream());
                outputStreamWriter.write(JSONObject.toJSONString(baseResponse));
                outputStreamWriter.flush();
                outputStreamWriter.close();
                return false;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        HandlerMethod method = (HandlerMethod)o;
        Class<?> clazz = method.getMethod().getDeclaringClass();
        AccessLimit accessLimit = method.getMethodAnnotation(AccessLimit.class);
        if((clazz.getDeclaredAnnotation(RestController.class) != null || clazz.getDeclaredAnnotation(Controller.class) != null) && accessLimit != null){
            AccessLimit accessLimit0 = clazz.getDeclaredAnnotation(AccessLimit.class);
            int seconds = (int)this.getValues(accessLimit0,accessLimit)[0];
            String ip = WebUtil.getRemoteAddr(httpServletRequest);
            String key = httpServletRequest.getServletPath() + ":" + httpServletRequest.getMethod() + ":" + ip;
            redisTemplate.opsForValue().increment(key,1);
            redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
        }
    }

    private Object[] getValues(AccessLimit accessLimit0,AccessLimit accessLimit){
        int seconds = accessLimit.seconds();
        int maxCount = accessLimit.maxCount();
        if(accessLimit0 != null && seconds == CountType.seconds && maxCount == CountType.maxCount){
            seconds = accessLimit0.seconds();
            maxCount = accessLimit0.maxCount();
        }
        return new Object[]{seconds,maxCount};
    }
}
