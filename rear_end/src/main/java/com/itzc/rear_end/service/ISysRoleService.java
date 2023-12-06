package com.itzc.rear_end.service;

import com.itzc.rear_end.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author itzc
 * @since 2023-10-31
 */
public interface ISysRoleService extends IService<SysRole> {

    void setRoleMenu(Integer roleId, List<Integer> menuIds);

    List<Integer> getRoleMenu(Integer roleId);
}
