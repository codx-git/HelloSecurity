package com.example.service;


import org.quartz.SchedulerException;

public interface ScheduleJobService {
    public void changeStatus(Long jobId, Boolean status) throws SchedulerException, ClassNotFoundException;
}
