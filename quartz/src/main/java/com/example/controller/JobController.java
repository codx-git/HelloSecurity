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
    @Operation(summary = "查询所有任务清单",description = "查询所有任务清单")
    public List<ScheduleJob> getAllTask(){
        return scheduleJobService.getAllTask();
    }

    @GetMapping("/getRunningJob")
    @Operation(summary = "获取正在执行的任务",description = "获取正在执行的任务")
    public List<ScheduleJob> getRunningJob() throws SchedulerException {
        return scheduleJobService.getRunningJob();
    }

    @PostMapping("/addTask")
    @Operation(summary = "添加定时任务",description = "添加定时任务")
    public String addTask(@RequestBody ScheduleJob job){
        scheduleJobService.addTask(job);
        return "success";
    }

    @PostMapping("/updateTaskStatus")
    @Operation(summary = "更改任务状态",description = "更改任务状态")
    public String updateTaskStatus(@RequestParam Long jobId) throws SchedulerException{
        scheduleJobService.changeStatus(jobId);
        return "success";
    }
    @PostMapping("/deleteTask")
    @Operation(summary = "删除一个任务",description = "删除一个任务")
    public String deleteTask(@RequestParam Long jobId) throws SchedulerException{
        scheduleJobService.deleteTask(jobId);
        return "success";
    }

    @PostMapping("/pauseJob")
    @Operation(summary = "暂停执行中的任务",description = "暂停执行中的任务")
    public String pauseJob(@RequestParam Long jobId) throws SchedulerException {
        scheduleJobService.pauseJob(jobId);
        return "success";
    }

    @PostMapping("/resumeJob")
    @Operation(summary = "恢复暂停的任务",description = "恢复暂停的任务")
    public String resumeJob(@RequestParam Long jobId) throws SchedulerException {
        scheduleJobService.resumeJob(jobId);
        return "success";
    }

    @PostMapping("/runAJobNow")
    @Operation(summary = "立刻执行一个任务并且只执行一次",description = "立刻执行一个任务并且只执行一次")
    public String runAJobNow(@RequestParam Long jobId) throws SchedulerException {
        scheduleJobService.runAJobNow(jobId);
        return "success";
    }
    @PostMapping("/test")
    public String test() {
        return "success";
    }
}
