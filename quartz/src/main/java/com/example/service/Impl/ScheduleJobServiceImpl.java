package com.example.service.Impl;

import com.example.entity.ScheduleJob;
import com.example.job.QuartzJobFactory;
import com.example.job.QuartzJobFactoryDisallowConcurrentExecution;
import com.example.mapper.ScheduleJobMapper;
import com.example.service.ScheduleJobService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class ScheduleJobServiceImpl implements ScheduleJobService {
    @Autowired
    private ScheduleJobMapper scheduleJobMapper;
    @Autowired
    private Scheduler scheduler;

    /**
     * 添加到数据库中 区别于addJob
     */
    public void addTask(ScheduleJob job) {
        job.setCreateTime(new Date());
        job.setLastModifyTime(new Date());
        scheduleJobMapper.insert(job);
    }

    /**
     * 从数据库中查询job
     */
    public ScheduleJob getTaskById(Long jobId) {
        return scheduleJobMapper.selectById(jobId);
    }

    /**
     * 更改任务状态
     *
     * @throws SchedulerException
     */
    public void changeStatus(Long jobId, Boolean status) throws SchedulerException, ClassNotFoundException {
        ScheduleJob job = getTaskById(jobId);
        if (job == null) {
            return;
        }
        if (status) {
            job.setJobStatus(status);
            addJob(job);
        } else {
            job.setJobStatus(status);
            deleteJob(job);
        }
        job.setLastModifyTime(new Date());
        scheduleJobMapper.updateById(job);
    }
    /**
     * 添加任务
     *
     * @throws SchedulerException
     */
    public void addJob(ScheduleJob job) throws SchedulerException, ClassNotFoundException {
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

            jobDetail.getJobDataMap().put("scheduleJob", job);

            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());

            trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobName(), job.getJobGroup()).withSchedule(scheduleBuilder).build();

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
     *
     * @param scheduleJob
     * @throws SchedulerException
     */
    public void deleteJob(ScheduleJob scheduleJob) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
        scheduler.deleteJob(jobKey);
    }
    /**
     * 暂停一个job
     *
     * @param scheduleJob
     * @throws SchedulerException
     */
    public void pauseJob(ScheduleJob scheduleJob) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
        scheduler.pauseJob(jobKey);
    }

    /**
     * 恢复一个job
     *
     * @param scheduleJob
     * @throws SchedulerException
     */
    public void resumeJob(ScheduleJob scheduleJob) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
        scheduler.resumeJob(jobKey);
    }
    /**
     * 立即执行job
     *
     * @param scheduleJob
     * @throws SchedulerException
     */
    public void runAJobNow(ScheduleJob scheduleJob) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
        scheduler.triggerJob(jobKey);
    }

    /**
     * 更新job时间表达式
     *
     * @param scheduleJob
     * @throws SchedulerException
     */
    public void updateJobCron(ScheduleJob scheduleJob) throws SchedulerException {;
        TriggerKey triggerKey = TriggerKey.triggerKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());

        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression());

        trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

        scheduler.rescheduleJob(triggerKey, trigger);
    }
}
