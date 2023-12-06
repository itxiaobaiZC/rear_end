package com.itzc.rear_end.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itzc.rear_end.entity.SysMenu;
import com.itzc.rear_end.mapper.SysMenuMapper;
import com.itzc.rear_end.service.ISysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author itzc
 * @since 2023-10-31
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {

    @Override
    public List<SysMenu> findMenus(String name) {

        QueryWrapper<SysMenu> objectQueryWrapper = new QueryWrapper<>();
        if (StrUtil.isNotBlank(name)){
            objectQueryWrapper.like("name",name);
        }
        //查询所有数据
        List<SysMenu> list = list(objectQueryWrapper);
        //找出pidwei null 的一级菜单
        List<SysMenu> prentNodes = list.stream().filter(menu -> menu.getPid() == null).collect(Collectors.toList());
        //一级 子菜单
        for (SysMenu sysMenu : prentNodes){
            sysMenu.setChildren(list.stream().filter(m->sysMenu.getId().equals((m.getPid()))).collect(Collectors.toList()));
        }
        return prentNodes;
    }
}
