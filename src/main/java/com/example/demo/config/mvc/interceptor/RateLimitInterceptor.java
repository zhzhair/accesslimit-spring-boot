package com.example.demo.config.mvc.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.common.dto.BaseResponse;
import com.example.demo.configuration.Constant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStreamWriter;
import java.util.concurrent.TimeUnit;

/**
 * 限流拦截器
 */
@Component
public class RateLimitInterceptor implements HandlerInterceptor {
    @Resource(name = "stringRedisTemplate")
    private RedisTemplate<String, String> redisTemplate;
    @Resource
    private Constant constant;
    @Value("${app.access.max-count}")
    public int maxCount;
    @Value("${app.access.seconds}")
    private int seconds;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String mac = constant.getMac();
        String string = redisTemplate.opsForValue().get(mac);
        Integer count = Integer.valueOf(string == null? "0":string);
        if(count > maxCount){
            System.err.println("当前访问量太大，访问频率：" + count/seconds);
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setCode(-4);
            baseResponse.setMsg("当前服务器访问量太大，请稍后再试");
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(httpServletResponse.getOutputStream());
            outputStreamWriter.write(JSONObject.toJSONString(baseResponse));
            outputStreamWriter.flush();
            outputStreamWriter.close();
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        String key = constant.getMac();
        redisTemplate.opsForValue().increment(key,1);
        redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
    }
}
