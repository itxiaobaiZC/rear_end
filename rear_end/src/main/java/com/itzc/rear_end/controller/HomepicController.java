package com.itzc.rear_end.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itzc.rear_end.entity.Files;
import com.itzc.rear_end.mapper.HomepicMapper;
import org.springframework.web.bind.annotation.*;
import com.itzc.rear_end.common.Result;
import javax.annotation.Resource;
import java.util.List;

import com.itzc.rear_end.service.IHomepicService;
import com.itzc.rear_end.entity.Homepic;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author itzc
 * @since 2023-11-18
 */
@RestController
@RequestMapping("/homepic")
public class HomepicController {

    @Resource
    private IHomepicService homepicService;

    @Resource
    private HomepicMapper homepicMapper;

    @PostMapping("/save")
    public Result save(@RequestBody Homepic homepic){
        return Result.success(homepicService.saveOrUpdate(homepic));
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
            return Result.success(homepicService.removeById(id));
    }
    @PostMapping("/deleteMul")
    public Result deleteMul(@RequestBody List<Integer> ids){
            return Result.success(homepicService.removeByIds(ids));
    }

    @GetMapping("/findAll")
    public Result findAll(){
            return Result.success(homepicService.list());
    }

    @GetMapping("/findById/{id}")
    public Result findById(@PathVariable Integer id){
            return Result.success(homepicService.getById(id));
    }

    @GetMapping("/findByEnable")
    public Result findByEnable(){
        return Result.success(homepicService.findByEnable());
    }
    @PostMapping("/updateById")
    public Result updateById(@RequestBody Homepic homepic){
        return Result.success(homepicMapper.updateById(homepic));
    }

    @GetMapping("/page")
    public Page<Homepic> findPage(@RequestParam Integer pageNum,
                                    @RequestParam Integer pageSize){
        QueryWrapper<Homepic> objectQueryWrapper = new QueryWrapper<>();
        return homepicService.page(new Page<>(pageNum,pageSize),objectQueryWrapper);
    }




}

