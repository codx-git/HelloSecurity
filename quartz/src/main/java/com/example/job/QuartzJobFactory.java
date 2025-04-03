package com.example.job;

import com.example.entity.ScheduleJob;
import com.example.utils.JobUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *
 * 计划任务执行处 无状态
 *  Spring调度任务 (重写 quartz 的 QuartzJobBean 类原因是在使用 quartz+spring 把 quartz 的 task 实例化进入数据库时，会产生： serializable 的错误)
 */
//@Deprecated
public class QuartzJobFactory implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //JobDataMap会从数据库表QRTZ_JOB_DETAILS的job_data获取数据
        //blob是一种用于存储二进制大对象的数据类型。它适合存储像图片、音频、视频、文档等二进制数据
        ScheduleJob scheduleJob = (ScheduleJob) jobExecutionContext.getMergedJobDataMap().get("scheduleJob");
        JobUtils.invokeMethod(scheduleJob,jobExecutionContext);
    }
}