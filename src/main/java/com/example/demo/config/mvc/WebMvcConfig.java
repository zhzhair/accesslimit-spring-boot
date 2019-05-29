package com.example.demo.config.mvc;
import com.example.demo.config.mvc.interceptor.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.annotation.Resource;

@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
    @Resource
    private TooMuchSubmitInterceptor tooMuchSubmitInterceptor;
    @Resource
    private RateLimitInterceptor rateLimitInterceptor;
    /**
     * 添加拦截器，排出不拦截的静态资源
     * @param registry registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tooMuchSubmitInterceptor)
                .excludePathPatterns("/swagger**/**")
                .excludePathPatterns("/v2/api-docs");
        registry.addInterceptor(rateLimitInterceptor)
                .excludePathPatterns("/swagger**/**")
                .excludePathPatterns("/v2/api-docs");
    }

    /**
     * 添加可访问的静态资源
     * @param registry registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

}
