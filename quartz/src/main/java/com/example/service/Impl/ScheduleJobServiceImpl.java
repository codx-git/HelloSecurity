package com.example.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.entity.ScheduleJob;
import com.example.job.QuartzJobFactory;
import com.example.job.QuartzJobFactoryDisallowConcurrentExecution;
import com.example.mapper.ScheduleJobMapper;
import com.example.service.ScheduleJobService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Wrapper;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.Condition;

@Slf4j
@Service
public class ScheduleJobServiceImpl implements ScheduleJobService {
    @Autowired
    private ScheduleJobMapper scheduleJobMapper;
    @Autowired
    private Scheduler scheduler;

    /**
     * 项目启动后加载定时任务
     * @PostConstruct 是 Java EE 中定义的注解，Spring 也使用该注解来表示某个方法应该在 Bean 初始化后执行一次。该注解可以标注在一个没有参数的
     */
    @PostConstruct
    private void init() throws SchedulerException {
        List<ScheduleJob> scheduleJobs = scheduleJobMapper.selectAll();
        if(scheduleJobs == null || scheduleJobs.size() == 0){
            log.info("项目启动后无可执行的定时任务");
        }
        for(ScheduleJob job:scheduleJobs){
            if(job.getJobStatus()){
                addJob(job);
            }else {
                deleteJob(job);
            }
        }
    }

    /**
     * 从数据库中查询job
     */
    public List<ScheduleJob> getAllTask() {
        return scheduleJobMapper.selectAll();
    }

    /**
     * 添加到数据库中 区别于addJob
     */
    public void addTask(ScheduleJob job) {
        job.setCreateTime(new Date());
        job.setLastModifyTime(new Date());
        scheduleJobMapper.insert(job);
    }
    /**
     * 从数据库中删除，并终止任务
     */
    public void deleteTask(Long jobId) throws SchedulerException {
        ScheduleJob job = getTaskById(jobId);
        if (job == null) {
            return;
        }
        deleteJob(job);
        scheduleJobMapper.deleteById(jobId);
    }
    /**
     * 从数据库中查询job
     */
    public ScheduleJob getTaskById(Long jobId) {
        return scheduleJobMapper.selectById(jobId);
    }

    /**
     * 更改任务状态
     */
    public void changeStatus(Long jobId) throws SchedulerException {
        ScheduleJob job = getTaskById(jobId);
        if (job == null) {
            return;
        }
        if (!job.getJobStatus()) {
            job.setJobStatus(true);
            addJob(job);
        } else {
            job.setJobStatus(false);
            deleteJob(job);
        }
        job.setLastModifyTime(new Date());
        scheduleJobMapper.updateById(job);
    }

    /**
     * 添加任务
     */
    public void addJob(ScheduleJob job) throws SchedulerException {
        if (job == null || !job.getJobStatus()) {
            return;
        }
        TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());

        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

        // 不存在，创建一个
        if (null == trigger) {
            Class clazz = job.getIsConcurrent() ? QuartzJobFactory.class : QuartzJobFactoryDisallowConcurrentExecution.class;
            //Class clazz = Class.forName(job.getMethodName());

            JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(job.getJobName(), job.getJobGroup()).build();
            //存入数据库表QRTZ_JOB_DETAILS的job_data字段内scheduleJob
            jobDetail.getJobDataMap().put("scheduleJob", job);

            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());

            trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobName(), job.getJobGroup()).withSchedule(scheduleBuilder).build();
            //此方法的主要功能是将一个作业（JobDetail）和一个触发器（Trigger）关联起来，并将其注册到调度器（Scheduler）中
            scheduler.scheduleJob(jobDetail, trigger);
        } else {
            // Trigger已存在，那么更新相应的定时设置
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());

            // 按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

            // 按新的trigger重新设置job执行
            scheduler.rescheduleJob(triggerKey, trigger);
        }
    }

    /**
     * 删除一个job
     */
    public void deleteJob(ScheduleJob scheduleJob) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
        scheduler.deleteJob(jobKey);
    }

    /**
     * 暂停一个job
     */
    public void pauseJob(Long jobId) throws SchedulerException {
        ScheduleJob job = getTaskById(jobId);
        if (job == null) {
            return;
        }
        JobKey jobKey = JobKey.jobKey(job.getJobName(), job.getJobGroup());
        scheduler.pauseJob(jobKey);
    }

    /**
     * 恢复一个job
     */
    public void resumeJob(Long jobId) throws SchedulerException {
        ScheduleJob job = getTaskById(jobId);
        if (job == null) {
            return;
        }
        JobKey jobKey = JobKey.jobKey(job.getJobName(), job.getJobGroup());
        scheduler.resumeJob(jobKey);
    }

    /**
     * 立即执行job,并且只执行一次
     */
    public void runAJobNow(Long jobId) throws SchedulerException {
        ScheduleJob job = getTaskById(jobId);
        if (job == null) {
            return;
        }
        JobKey jobKey = JobKey.jobKey(job.getJobName(), job.getJobGroup());
        //该方法的作用是立即触发一个已经存在于 !调度器(scheduler)中! 的作业
        scheduler.triggerJob(jobKey);
    }

    /**
     * 更新job时间表达式
     */
    @Deprecated
    public void updateJobCron(ScheduleJob scheduleJob) throws SchedulerException {

        TriggerKey triggerKey = TriggerKey.triggerKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());

        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression());

        trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

        scheduler.rescheduleJob(triggerKey, trigger);
    }

    /**
     * 所有正在运行的job
     */
    public List<ScheduleJob> getRunningJob() throws SchedulerException {
        List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
        List<ScheduleJob> jobList = new ArrayList<ScheduleJob>(executingJobs.size());
        for (JobExecutionContext executingJob : executingJobs) {
            ScheduleJob job = new ScheduleJob();
            JobDetail jobDetail = executingJob.getJobDetail();
            JobKey jobKey = jobDetail.getKey();
            Trigger trigger = executingJob.getTrigger();
            job.setJobName(jobKey.getName());
            job.setJobGroup(jobKey.getGroup());
            Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
            job.setDescription("触发器:" + trigger.getKey() + ";状态:" + triggerState.name());
            //job.setJobStatus(triggerState.name());
            if (trigger instanceof CronTrigger) {
                CronTrigger cronTrigger = (CronTrigger) trigger;
                String cronExpression = cronTrigger.getCronExpression();
                job.setCronExpression(cronExpression);
            }
            jobList.add(job);
        }
        return jobList;
    }
}
