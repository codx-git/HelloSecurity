package com.example.controller;

import com.example.entity.ScheduleJob;
import com.example.service.ScheduleJobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "job-controller", description = "定时任务控制器")
@RequestMapping("/quartz")
public class JobController {
    @Autowired
    private ScheduleJobService scheduleJobService;
    @GetMapping("/getAllTask")
    @Operation(summary = "",description = "查询所有任务清单")
    public List<ScheduleJob> getAllTask(){
        return scheduleJobService.getAllTask();
    }
    @PostMapping("/addTask")
    @Operation(summary = "",description = "添加定时任务")
    public String addTask(@RequestBody ScheduleJob job){
        scheduleJobService.addTask(job);
        return "success";
    }

    @PostMapping("/updateTaskStatus")
    @Operation(summary = "",description = "更改任务状态")
    public String updateTaskStatus(@RequestBody Long jobId) throws SchedulerException{
        scheduleJobService.changeStatus(jobId);
        return "success";
    }
    @PostMapping("/deleteTask")
    @Operation(summary = "",description = "更改任务状态")
    public String deleteTask(@RequestBody Long jobId) throws SchedulerException{
        scheduleJobService.deleteTask(jobId);
        return "success";
    }

    @PostMapping("/pauseJob")
    @Operation(summary = "",description = "暂停执行中的任务")
    public String pauseJob(@RequestBody ScheduleJob job) throws SchedulerException {
        scheduleJobService.pauseJob(job);
        return "success";
    }

    @PostMapping("/resumeJob")
    @Operation(summary = "",description = "恢复暂停的任务")
    public String resumeJob(@RequestBody ScheduleJob job) throws SchedulerException {
        scheduleJobService.resumeJob(job);
        return "success";
    }
    @PostMapping("/test")
    public String test() {
        return "success";
    }
}
