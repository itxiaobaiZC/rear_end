package com.itzc.rear_end.service;

import com.itzc.rear_end.common.Result;
import com.itzc.rear_end.controller.dto.UserDTO;
import com.itzc.rear_end.controller.dto.UserPasswordDTO;
import com.itzc.rear_end.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.lang.reflect.InvocationTargetException;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author itzc
 * @since 2023-10-27
 */
public interface ISysUserService extends IService<SysUser> {

    UserDTO login(UserDTO userDTO) throws InvocationTargetException, IllegalAccessException;

    SysUser sgin(UserDTO userDTO);

    void updatePassword(UserPasswordDTO userPasswordDTO);

    SysUser findByName(String name);
}
