package com.itzc.rear_end.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import com.itzc.rear_end.common.Result;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import com.itzc.rear_end.service.IDailyTimeService;
import com.itzc.rear_end.entity.DailyTime;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author itzc
 * @since 2023-11-11
 */
@RestController
@RequestMapping("/daily-time")
public class DailyTimeController {

    @Resource
    private IDailyTimeService dailyTimeService;

    @PostMapping("/save")
    public Result save(@RequestBody DailyTime dailyTime){
        return Result.success(dailyTimeService.saveOrUpdate(dailyTime));
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
            return Result.success(dailyTimeService.removeById(id));
    }
    @PostMapping("/deleteMul")
    public Result deleteMul(@RequestBody List<Integer> ids){
            return Result.success(dailyTimeService.removeByIds(ids));
    }

    @GetMapping("/findAll")
    public Result findAll(){
            return Result.success(dailyTimeService.listDesc());
    }

    @GetMapping("/findById/{id}")
    public Result findById(@PathVariable Integer id){
            return Result.success(dailyTimeService.getById(id));
    }

    @GetMapping("/page")
    public Page<DailyTime> findPage(@RequestParam Integer pageNum,
                                    @RequestParam Integer pageSize){
        QueryWrapper<DailyTime> objectQueryWrapper = new QueryWrapper<>();
        return dailyTimeService.page(new Page<>(pageNum,pageSize),objectQueryWrapper);
    }




}

