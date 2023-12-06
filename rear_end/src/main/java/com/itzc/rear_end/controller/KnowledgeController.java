package com.itzc.rear_end.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import com.itzc.rear_end.common.Result;
import javax.annotation.Resource;
import java.util.List;

import com.itzc.rear_end.service.IKnowledgeService;
import com.itzc.rear_end.entity.Knowledge;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author itzc
 * @since 2023-11-14
 */
@RestController
@RequestMapping("/knowledge")
public class KnowledgeController {

    @Resource
    private IKnowledgeService knowledgeService;

    @PostMapping("/save")
    public Result save(@RequestBody Knowledge knowledge){
        return Result.success(knowledgeService.saveOrUpdate(knowledge));
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
            return Result.success(knowledgeService.removeById(id));
    }
    @PostMapping("/deleteMul")
    public Result deleteMul(@RequestBody List<Integer> ids){
            return Result.success(knowledgeService.removeByIds(ids));
    }

    @GetMapping("/findAll")
    public Result findAll(){
            return Result.success(knowledgeService.list());
    }

    @GetMapping("/findById/{id}")
    public Result findById(@PathVariable Integer id){
            return Result.success(knowledgeService.getById(id));
    }

    @GetMapping("/page")
    public Page<Knowledge> findPage(@RequestParam Integer pageNum,
                                    @RequestParam Integer pageSize,
                                    @RequestParam(defaultValue = "") String title    ){
        QueryWrapper<Knowledge> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.like("title",title);
        return knowledgeService.page(new Page<>(pageNum,pageSize),objectQueryWrapper);
    }




}

