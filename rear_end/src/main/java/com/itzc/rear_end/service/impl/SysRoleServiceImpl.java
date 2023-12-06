package com.itzc.rear_end.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itzc.rear_end.entity.RoleMenu;
import com.itzc.rear_end.entity.SysMenu;
import com.itzc.rear_end.entity.SysRole;
import com.itzc.rear_end.mapper.RoleMenuMapper;
import com.itzc.rear_end.mapper.SysRoleMapper;
import com.itzc.rear_end.service.ISysMenuService;
import com.itzc.rear_end.service.ISysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author itzc
 * @since 2023-10-31
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    @Resource
    private RoleMenuMapper roleMenuMapper;
    @Resource
    private ISysMenuService iSysMenuService;

    @Transactional  //绑定执行
    @Override
    public void setRoleMenu(Integer roleId, List<Integer> menuIds) {
//        QueryWrapper<RoleMenu> objectQueryWrapper = new QueryWrapper<>();
//        objectQueryWrapper.eq("role_id",roldId);
//        roleMenuMapper.delete(objectQueryWrapper);
        //先删除
        roleMenuMapper.deleteByRoleId(roleId);
        //增加
        List<Integer> menuIdsCopy= CollUtil.newArrayList(menuIds);
        for (Integer menuId : menuIds) {
            SysMenu menu = iSysMenuService.getById(menuId);
            //二级菜单 没有父级id
            if (menu.getPid() != null && !menuIdsCopy.contains(menu.getPid())){
                //补上父级id
                RoleMenu roleMenu = new RoleMenu();
                roleMenu.setRoleId(roleId);
                roleMenu.setMenuId(menu.getPid());
                roleMenuMapper.insert(roleMenu);
                menuIdsCopy.add(menu.getPid());
            }
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(menuId);
            roleMenuMapper.insert(roleMenu);
        }
    }

    @Override
    public List<Integer> getRoleMenu(Integer roleId) {
        return roleMenuMapper.selectByRoleId(roleId);
    }
}
