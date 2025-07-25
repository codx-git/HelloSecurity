package com.example.utils;

import com.example.entity.ScheduleJob;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Slf4j
public class JobUtils {
    public static void invokeMethod(ScheduleJob job, JobExecutionContext jobExecutionContext){
        try{
            if(job.getBeanClass() != null && job.getMethodName() != null){
                Class clazz = Class.forName(job.getBeanClass());
                //clazz.newInstance()被启用]确地处理异常
                Method method = clazz.getMethod(job.getMethodName());
                method.invoke(clazz.getConstructor().newInstance());
                //激活带JobExecutionContext的入参的接口
                //Method method = clazz.getMethod(job.getMethodName(),jobExecutionContext.getClass());
                //method.invoke(clazz.getConstructor().newInstance(),jobExecutionContext.getClass());
            }else {
                log.error("invoke job failed Schedule.BeanClass is null or Schedule.MethodName is null");
            }
        }catch (ClassNotFoundException e) {
            log.error("jobId:{},creat class error:{}",job.getId(),job.getBeanClass());
            throw new RuntimeException(e);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            log.error("jobId:{},creat method error:{}",job.getId(),job.getMethodName());
            throw new RuntimeException(e);
        }
    }
}
