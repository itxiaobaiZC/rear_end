package com.itzc.rear_end.controller;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itzc.rear_end.entity.Policycomment;
import com.itzc.rear_end.until.TokenUtils;
import org.springframework.web.bind.annotation.*;
import com.itzc.rear_end.common.Result;
import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.itzc.rear_end.service.IPolicycommentService;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author itzc
 * @since 2023-11-22
 */
@RestController
@RequestMapping("/policycomment")
public class PolicycommentController {

    @Resource
    private IPolicycommentService policycommentService;

    @PostMapping("/save")
    public Result save(@RequestBody Policycomment  policycomment){
        if (policycomment.getId() == null) { // 新增评论
            policycomment.setUserId(TokenUtils.getCurrentsysUser().getId());
            policycomment.setTime(DateUtil.now());
            if (policycomment.getPid() != null) {  // 判断如果是回复，进行处理
                Integer pid = policycomment.getPid();
                Policycomment policyComment = policycommentService.getById(pid);
                if (policyComment.getOriginId() != null) {  // 如果当前回复的父级有祖宗，那么就设置相同的祖宗
                    policycomment.setOriginId(policyComment.getOriginId());
                } else {  // 否则就设置父级为当前回复的祖宗
                    policycomment.setOriginId(policycomment.getPid());
                }
            }

        }
        return Result.success(policycommentService.saveOrUpdate(policycomment));
    }

    @GetMapping("/tree/{articleId}")
    public Result findTree(@PathVariable Integer articleId) {
        List<Policycomment> articleComments = policycommentService.findCommentDetail(articleId);  // 查询所有的评论和回复数据
        // 查询评论数据（不包括回复）
        List<Policycomment> originList = articleComments.stream().filter(comment -> comment.getOriginId() == null).collect(Collectors.toList());

        // 设置评论数据的子节点，也就是回复内容
        for (Policycomment origin : originList) {
            List<Policycomment> comments = articleComments.stream().filter(comment -> origin.getId().equals(comment.getOriginId())).collect(Collectors.toList());  // 表示回复对象集合
            comments.forEach(pcomment -> {
                Optional<Policycomment> pComment = articleComments.stream().filter(c1 -> c1.getId().equals(pcomment.getPid())).findFirst();  // 找到当前评论的父级
                pComment.ifPresent((v -> {  // 找到父级评论的用户id和用户昵称，并设置给当前的回复对象
                    pcomment.setPUserId(v.getUserId());
                    pcomment.setPNickname(v.getUname());
                }));
            });
            origin.setChildren(comments);
        }
        return Result.success(originList);
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
            return Result.success(policycommentService.removeById(id));
    }
    @PostMapping("/deleteMul")
    public Result deleteMul(@RequestBody List<Integer> ids){
            return Result.success(policycommentService.removeByIds(ids));
    }

    @GetMapping("/findAll")
    public Result findAll(){
            return Result.success(policycommentService.list());
    }

    @GetMapping("/findById/{id}")
    public Result findById(@PathVariable Integer id){
            return Result.success(policycommentService.getById(id));
    }

    @GetMapping("/page")
    public Page<Policycomment> findPage(@RequestParam Integer pageNum,
                                    @RequestParam Integer pageSize){
        QueryWrapper<Policycomment> objectQueryWrapper = new QueryWrapper<>();
        return policycommentService.page(new Page<>(pageNum,pageSize),objectQueryWrapper);
    }




}

