package com.example.service;


import com.example.entity.ScheduleJob;
import org.quartz.SchedulerException;

import java.util.List;

public interface ScheduleJobService {
    //获取数据库内任务清单
    public List<ScheduleJob> getAllTask();
    //获取正在执行的任务
    public List<ScheduleJob> getRunningJob() throws SchedulerException;
    //添加定时任务到数据库中
    public void addTask(ScheduleJob job);
    //从数据库中删除，并终止任务
    public void deleteTask(Long jobId) throws SchedulerException;

    //获取定时任务信息
    public ScheduleJob getTaskById(Long jobId);

    //更改任务状态
    public void changeStatus(Long jobId) throws SchedulerException;

    //暂停一个任务
    public void pauseJob(Long jobId) throws SchedulerException;

    //恢复一个任务
    public void resumeJob(Long jobId) throws SchedulerException;

    //立刻执行一个任务并且只执行一次
    public void runAJobNow(Long jobId) throws SchedulerException;

    //更新任务定时时间
    public void updateJobCron(ScheduleJob scheduleJob) throws SchedulerException;

}
