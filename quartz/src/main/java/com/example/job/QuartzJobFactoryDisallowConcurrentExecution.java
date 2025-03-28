package com.example.job;

import com.example.entity.ScheduleJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *
 * 若一个方法一次执行不完下次轮转时则等待该方法执行完后才执行下一次操作
 *  Spring调度任务 (重写 quartz 的 QuartzJobBean 类原因是在使用 quartz+spring 把 quartz 的 task 实例化进入数据库时，会产生： serializable 的错误)
 */
@Deprecated
@DisallowConcurrentExecution
public class QuartzJobFactoryDisallowConcurrentExecution implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        ScheduleJob scheduleJob = (ScheduleJob) jobExecutionContext.getMergedJobDataMap().get("scheduleJob");
        //JobUtils.invokMethod(scheduleJob,context);
    }
}
