package com.itzc.rear_end.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itzc.rear_end.common.Constants;
import com.itzc.rear_end.controller.dto.UserDTO;
import com.itzc.rear_end.controller.dto.UserPasswordDTO;
import com.itzc.rear_end.entity.SysMenu;
import com.itzc.rear_end.entity.SysUser;
import com.itzc.rear_end.exception.ServiceException;
import com.itzc.rear_end.mapper.RoleMenuMapper;
import com.itzc.rear_end.mapper.SysRoleMapper;
import com.itzc.rear_end.mapper.SysUserMapper;
import com.itzc.rear_end.service.ISysMenuService;
import com.itzc.rear_end.service.ISysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.itzc.rear_end.until.MD5Utils;
import com.itzc.rear_end.until.TokenUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author itzc
 * @since 2023-10-27
 */
@Service

public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Autowired
    private ISysMenuService iSysMenuService;

    @Override
    public UserDTO login(UserDTO userDTO){

        SysUser one = getUserInfo(userDTO);
        if (one!=null){
            try {
                BeanUtils.copyProperties(userDTO,one);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            //设置token
            String token = TokenUtils.getToken(one.getId().toString(), one.getPassword());
            userDTO.setToken(token);

            String role = one.getRole();
            //设置用户菜单列表
            List<SysMenu> roleMenus = getRoleMenus(role);
            userDTO.setMenus(roleMenus);
            return userDTO;
        }else{
            throw new ServiceException(Constants.CODE_600,"用户名或密码错误");
        }


    }
    @Override
    public SysUser sgin(UserDTO userDTO) {
        SysUser one = getUserInfo(userDTO);
        if (one==null){
            try {
                one=new SysUser();
                String password = userDTO.getPassword();
                String code = MD5Utils.code(password);
                userDTO.setPassword(code);
                BeanUtils.copyProperties(one,userDTO);
                save(one);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }else{
            throw new ServiceException(Constants.CODE_600,"用户名或密码错误");
        }
        return one;
    }


    @Override
    //修改密码
    public void updatePassword(UserPasswordDTO userPasswordDTO) {
        String code=MD5Utils.code(userPasswordDTO.getPassword());
        String newcode=MD5Utils.code(userPasswordDTO.getNewPassword());
        userPasswordDTO.setPassword(code);
        userPasswordDTO.setNewPassword(newcode);
        int update=sysUserMapper.updatePassword(userPasswordDTO);
        if (update<1){
            throw new ServiceException(Constants.CODE_600,"密码错误");
        }
    }

    @Override
    public SysUser findByName(String name) {
        return sysUserMapper.selectByName(name);
    }

    private  SysUser getUserInfo(UserDTO userDTO){
        //从数据库取数据
        QueryWrapper<SysUser> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.eq("name",userDTO.getName());
        objectQueryWrapper.eq("password", MD5Utils.code(userDTO.getPassword()));
        SysUser one;
        try {
            one = getOne(objectQueryWrapper);

        }catch (Exception e){
            e.printStackTrace();
            throw new ServiceException(Constants.CODE_500,"系统错误");
        }
        return one;
    }

    //获取当前局的的菜单项
    private List<SysMenu> getRoleMenus(String roleFlag){
        Integer roleId = sysRoleMapper.selectByFlag(roleFlag);
        //当前局的的所有菜单id集合
        List<Integer> menuIds = roleMenuMapper.selectByRoleId(roleId);
        //查询系统所有菜单
        List<SysMenu> menus = iSysMenuService.findMenus("");
        //筛选完成之后的list
        List<SysMenu> roleMenus = new ArrayList<>();
        //筛选当前用户菜单
        for (SysMenu menu : menus) {
            if (menuIds.contains(menu.getId())){
                roleMenus.add(menu);
            }
            List<SysMenu> children = menu.getChildren();
            children.removeIf(child->!menuIds.contains(child.getId()));
        }
        return roleMenus;
    }
}
