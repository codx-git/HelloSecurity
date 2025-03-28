package com.example.controller;

import com.example.entity.ScheduleJob;
import com.example.service.ScheduleJobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "job-controller", description = "定时任务控制器")
@RequestMapping("/quartz")
public class JobController {
    @Autowired
    private ScheduleJobService scheduleJobService;
    @PostMapping("addTask")
    @Operation(summary = "",description = "添加定时任务")
    public String addTask(@RequestBody ScheduleJob job){
        return "success";
    }

    @PostMapping("changeStatus")
    public String changeStatus(@RequestBody ScheduleJob job) throws SchedulerException, ClassNotFoundException {
        scheduleJobService.changeStatus(job.getId(),job.getJobStatus());
        return "success";
    }

    @PostMapping("/test")
    public String test() {
        return "success";
    }
}
