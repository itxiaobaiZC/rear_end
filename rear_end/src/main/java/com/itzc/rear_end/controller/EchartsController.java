package com.itzc.rear_end.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.Quarter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itzc.rear_end.common.Result;
import com.itzc.rear_end.config.AuthAccess;
import com.itzc.rear_end.entity.Files;
import com.itzc.rear_end.entity.Garbage;
import com.itzc.rear_end.entity.SysUser;
import com.itzc.rear_end.mapper.FileMapper;
import com.itzc.rear_end.service.IGarbageService;
import com.itzc.rear_end.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/echarts")
public class EchartsController {
    @Autowired
    private ISysUserService iSysUserService;

    @Autowired
    private IGarbageService iGarbageService;

    @Autowired
    private FileMapper fileMapper;

    @GetMapping("/example")
    public Result get(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("x", CollUtil.newArrayList("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun "));
        map.put("y",CollUtil.newArrayList(150, 230, 224, 218, 135, 147, 260));
        return Result.success(map);
    }

    @GetMapping("/count")
    public Result getCount(){
        return Result.success(iSysUserService.count());
    }

    @GetMapping("/gcount")
    public Result getGcount(){
        return Result.success(iGarbageService.count());
    }

    @AuthAccess
    @GetMapping("/front/all")
    @Cacheable(value = "files", key="targetClass + getMethodName()")
    public Result frontAll(){
        QueryWrapper<Files> objectQueryWrapper = new QueryWrapper<>();
        //查询未删除的记录
        objectQueryWrapper.eq("is_delete",false);
        return Result.success(fileMapper.selectList(objectQueryWrapper));
    }

    @GetMapping("/member")
    public Result member(){
        List<SysUser> list = iSysUserService.list();
        int q1=0;
        int q2=0;
        int q3=0;
        int q4=0;
        for (SysUser sysUser : list){
            Date createTime=sysUser.getCreateTime();
            Quarter quarter = DateUtil.quarterEnum(createTime);
            switch (quarter){
                case Q1:q1 =q1+1;break;
                case Q2:q2 =q2+1;break;
                case Q3:q3 =q3+1;break;
                case Q4:q4 =q4+1;break;
                default: break;
            }
        }
        return Result.success(CollUtil.newArrayList(q1,q2,q3,q4));
    }

    @GetMapping("/garbage")
    public Result garbage(){
        List<Garbage> list = iGarbageService.list();
        int q1=0;
        int q2=0;
        int q3=0;
        int q4=0;
        for (Garbage garbage : list){
            String category=garbage.getCategory();
            switch (category){
                case "可回收垃圾":q1 =q1+1;break;
                case "有害垃圾":q2 =q2+1;break;
                case "厨余垃圾":q3 =q3+1;break;
                case "其他垃圾":q4 =q4+1;break;
                default: break;
            }
        }
        return Result.success(CollUtil.newArrayList(q1,q2,q3,q4));
    }
}
