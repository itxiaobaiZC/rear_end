package com.itzc.rear_end.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import com.itzc.rear_end.common.Result;
import javax.annotation.Resource;
import java.util.List;

import com.itzc.rear_end.service.IPolicyService;
import com.itzc.rear_end.entity.Policy;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author itzc
 * @since 2023-11-12
 */
@RestController
@RequestMapping("/policy")
public class PolicyController {

    @Resource
    private IPolicyService policyService;

    @PostMapping("/save")
    public Result save(@RequestBody Policy policy){
        return Result.success(policyService.saveOrUpdate(policy));
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
            return Result.success(policyService.removeById(id));
    }
    @PostMapping("/deleteMul")
    public Result deleteMul(@RequestBody List<Integer> ids){
            return Result.success(policyService.removeByIds(ids));
    }

    @GetMapping("/findAll")
    public Result findAll(){
            return Result.success(policyService.list());
    }

    @GetMapping("/findById/{id}")
    public Result findById(@PathVariable Integer id){
            return Result.success(policyService.getById(id));
    }

    @GetMapping("/page")
    public Page<Policy> findPage(@RequestParam Integer pageNum,
                                    @RequestParam Integer pageSize){
        QueryWrapper<Policy> objectQueryWrapper = new QueryWrapper<>();
        return policyService.page(new Page<>(pageNum,pageSize),objectQueryWrapper);
    }




}

