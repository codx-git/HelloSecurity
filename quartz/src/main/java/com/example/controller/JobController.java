package com.example.controller;

import com.example.service.ScheduleJobService;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/quartz")
public class JobController {
    @Autowired
    private ScheduleJobService scheduleJobService;

    @RequestMapping("add")
    public String addJob() throws SchedulerException, ClassNotFoundException {
        scheduleJobService.changeStatus(12L,true);
        return "success";
    }

    @PostMapping("/test")
    public String test() {
        return "success";
    }
}
