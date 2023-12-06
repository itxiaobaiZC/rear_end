package com.itzc.rear_end.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itzc.rear_end.common.Result;
import com.itzc.rear_end.entity.SysUser;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

import com.itzc.rear_end.service.ISysRoleService;
import com.itzc.rear_end.entity.SysRole;

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
@RequestMapping("/sys-role")
public class SysRoleController {

    @Resource
    private ISysRoleService sysRoleService;

    @PostMapping("/save")
    public Result save(@RequestBody SysRole sysRole){
        return Result.success(sysRoleService.saveOrUpdate(sysRole));
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
            return Result.success(sysRoleService.removeById(id));
    }
    @PostMapping("/deleteMul")
    public Result deleteMul(@RequestBody List<Integer> ids){
            return Result.success(sysRoleService.removeByIds(ids));
    }

    @GetMapping("/findAll")
    public Result findAll(){
            return  Result.success(sysRoleService.list());
    }

    @GetMapping("/findById/{id}")
    public Result findById(@PathVariable Integer id){
            return Result.success(sysRoleService.getById(id));
    }

    @GetMapping("/page")
    public Page<SysRole> findPage(@RequestParam Integer pageNum,
                                    @RequestParam Integer pageSize,
                                  @RequestParam String name){
        QueryWrapper<SysRole> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.like("name",name);
        return sysRoleService.page(new Page<>(pageNum,pageSize),objectQueryWrapper);
    }

    //绑定角色和菜单的关系
    @PostMapping("/roleMenu/{roleId}")
    public Result roleMenu(@PathVariable Integer roleId,
                           @RequestBody List<Integer> menuIds){
        sysRoleService.setRoleMenu(roleId,menuIds);
        return Result.success();
    }

    @GetMapping("/getRoleMenu/{roleId}")
    public Result getRoleMenu(@PathVariable Integer roleId){
        return Result.success(sysRoleService.getRoleMenu(roleId));
    }

}

