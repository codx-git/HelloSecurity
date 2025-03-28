package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.ScheduleJob;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ScheduleJobMapper extends BaseMapper<ScheduleJob> {
    List<ScheduleJob> selectAll();
}
