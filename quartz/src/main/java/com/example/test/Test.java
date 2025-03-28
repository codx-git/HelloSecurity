package com.example.test;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class Test {
    public static void main(String[] args) throws SchedulerException {
        //创建一个scheduler
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.getContext().put("skey","svalue");

        //创建一个trigger
        //trigger还有一个重要的属性misfire；
        //如果scheduler关闭了，或者Quartz线程池中没有可用的线程来执行job，
        //此时持久性的trigger就会错过(miss)其触发时间，即错过触发(misfire)
        Trigger simpleTrigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1","group1")
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(5)
                        .repeatForever()
                        .withRepeatCount(2))
                .usingJobData("t1","tv1")
                .build();
        simpleTrigger.getJobDataMap().put("t2","simple");

        Trigger cronTrigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1","group1")
                .withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ?"))
                .usingJobData("t1","tv1")
                .build();
        cronTrigger.getJobDataMap().put("t2","cron");

        //创建一个job
        JobDetail job = JobBuilder.newJob(HelloJob.class)
                .withIdentity("myjob","mygroup")
                .usingJobData("j1","jv1")
                .build();
        job.getJobDataMap().put("j2","jv2");

        //注册并启动
        //scheduler.scheduleJob(job,simpleTrigger);
        scheduler.scheduleJob(job,cronTrigger);
        scheduler.start();

    }
}
