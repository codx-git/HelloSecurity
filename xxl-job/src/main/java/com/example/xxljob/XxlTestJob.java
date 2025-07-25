package com.example.xxljob;

import org.springframework.stereotype.Component;
import com.xxl.job.core.handler.annotation.XxlJob;

@Component
public class XxlTestJob {
    @XxlJob(value = "testJob", init = "star", destroy = "close")
    public void testJob(){
        String s="";
        for (int i = 0; i < 1000; i++) {
            s= "sdfsfds" +i;
        }
        System.out.println("这是一个定时认为测试！！！！！！");
    }

    @XxlJob("testJob2")
    public void testJob2(){
        System.out.println("这是一个定时认为测试！！！！！！");
    }
}
