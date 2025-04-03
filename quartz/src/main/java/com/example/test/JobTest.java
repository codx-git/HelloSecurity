package com.example.test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JobTest {
    public void run(){
        log.info("JobTest is run");
    }
    public void stop(){
        log.info("JobTest is stop");
    }

    public void running() throws InterruptedException {
        log.info("JobTest is start");
        Thread.sleep(10*1000);
        log.info("JobTest is end");
    }
}
