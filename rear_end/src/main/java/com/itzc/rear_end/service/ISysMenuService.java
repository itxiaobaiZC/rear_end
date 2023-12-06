package com.itzc.rear_end.service;

import com.itzc.rear_end.entity.SysMenu;
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
public interface ISysMenuService extends IService<SysMenu> {

    List<SysMenu> findMenus(String name);
}
