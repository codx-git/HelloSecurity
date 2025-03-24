package com.example.quartz.job;

import org.quartz.*;

import java.time.LocalDateTime;

public class HelloJob implements Job {
    public void setT1(String t1) {
        this.t1 = t1;
    }

    public void setT2(String t2) {
        this.t2 = t2;
    }

    public void setJ1(String j1) {
        this.j1 = j1;
    }

    public void setJ2(String j2) {
        this.j2 = j2;
    }
    //自动注入JobDataMap数据
    String t1;
    String t2;
    String j1;
    String j2;
    String j3;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
//        Object tv1 = jobExecutionContext.getTrigger().getJobDataMap().get("t1");
//        Object tv2 = jobExecutionContext.getTrigger().getJobDataMap().get("t2");
//        Object jv1 = jobExecutionContext.getJobDetail().getJobDataMap().get("j1");
//        Object jv2 = jobExecutionContext.getJobDetail().getJobDataMap().get("j2");
        //JobKey jobKey = jobExecutionContext.getJobDetail().getKey();
        //JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
        Object sv = null;
        try {
            sv = jobExecutionContext.getScheduler().getContext().get("skey");
            //jobExecutionContext.
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
//        System.out.println(tv1+":"+tv2);
//        System.out.println(jv1+":"+jv2);
        System.out.println(t1+":"+t2);
        System.out.println(j1+":"+j2);
        System.out.println(sv);
        System.out.println("hello:"+ LocalDateTime.now());
    }
}
