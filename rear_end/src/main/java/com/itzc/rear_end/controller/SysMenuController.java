package com.itzc.rear_end.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itzc.rear_end.common.Constants;
import com.itzc.rear_end.common.Result;
import com.itzc.rear_end.entity.Dict;
import com.itzc.rear_end.mapper.DictMapper;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

import com.itzc.rear_end.service.ISysMenuService;
import com.itzc.rear_end.entity.SysMenu;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author itzc
 * @since 2023-10-31
 */
@RestController
@RequestMapping("/sys-menu")
public class SysMenuController {
    @Resource
    private ISysMenuService sysMenuService;
    @Resource
    private DictMapper dictMapper;
    @PostMapping("/save")
    public Result save(@RequestBody SysMenu sysMenu){
        return Result.success(sysMenuService.saveOrUpdate(sysMenu));
    }
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
            return Result.success(sysMenuService.removeById(id));
    }
    @PostMapping("/deleteMul")
    public Result deleteMul(@RequestBody List<Integer> ids){
            return Result.success(sysMenuService.removeByIds(ids));
    }
    @GetMapping("/findAll")
    public Result findAll(@RequestParam(defaultValue = "") String name){
        return Result.success(sysMenuService.findMenus(name));
    }
    @GetMapping("/findById/{id}")
    public Result findById(@PathVariable Integer id){
            return Result.success(sysMenuService.getById(id));
    }
    @GetMapping("/findAllIds")
    public Result findAllIds(){
        return Result.success(sysMenuService.list().stream().map(SysMenu::getId));
    }
    @GetMapping("/page")
    public Page<SysMenu> findPage(@RequestParam Integer pageNum,
                                    @RequestParam Integer pageSize,
                                  @RequestParam String name){
        QueryWrapper<SysMenu> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.like("name",name);
        return sysMenuService.page(new Page<>(pageNum,pageSize),objectQueryWrapper);
    }
    @GetMapping("/icons")
    public Result getIcons(){
        QueryWrapper<Dict> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.eq("type", Constants.DICT_TYPE_ICON);
        return Result.success(dictMapper.selectList(null));
    }




}

