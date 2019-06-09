# accesslimit-spring-boot
spring boot + redis 实现网站限流和接口防刷功能。
注解@AccessLimit 实现接口防刷功能，在方法上的注解参数优先于类上注解的参数；

限流需要在配置文件配置多长时间可以通过多少请求，当然你也可以用guava的限流方式。
