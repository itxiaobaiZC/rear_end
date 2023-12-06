package com.itzc.rear_end.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itzc.rear_end.until.TokenUtils;
import org.springframework.web.bind.annotation.*;
import com.itzc.rear_end.common.Result;
import javax.annotation.Resource;
import java.util.List;

import com.itzc.rear_end.service.IArticleService;
import com.itzc.rear_end.entity.Article;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author itzc
 * @since 2023-11-07
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Resource
    private IArticleService articleService;
    //保存或者修改
    @PostMapping("/save")
    public Result save(@RequestBody Article article){
        if (article.getId()==null){
            article.setUser(TokenUtils.getCurrentsysUser().getUname());
        }
        return Result.success(articleService.saveOrUpdate(article));
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
            return Result.success(articleService.removeById(id));
    }
    @PostMapping("/deleteMul")
    public Result deleteMul(@RequestBody List<Integer> ids){
            return Result.success(articleService.removeByIds(ids));
    }

    @GetMapping("/findAll")
    public Result findAll(){
            return Result.success(articleService.list());
    }

    @GetMapping("/findById/{id}")
    public Result findById(@PathVariable Integer id){
            return Result.success(articleService.getById(id));
    }

    @GetMapping("/page")
    public Page<Article> findPage(@RequestParam Integer pageNum,
                                  @RequestParam Integer pageSize,
                                  @RequestParam(defaultValue = "") String title){
        QueryWrapper<Article> objectQueryWrapper = new QueryWrapper<>();
        if (StrUtil.isNotBlank(title)) {
            objectQueryWrapper.like("title", title);
        }
        return articleService.page(new Page<>(pageNum,pageSize),objectQueryWrapper);
    }




}

