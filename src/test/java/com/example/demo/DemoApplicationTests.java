package com.example.demo;

import com.example.demo.configuration.Constant;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {
    @Resource
    private Constant constant;
    private long time;
    @Before
    public void beginTime(){
        this.time = System.currentTimeMillis();
    }

    @Test
    public void contextLoads(){
        System.err.println(constant.getMac());
    }

    @After
    public void endTime(){
        System.err.println(System.currentTimeMillis() - time);
    }
}
