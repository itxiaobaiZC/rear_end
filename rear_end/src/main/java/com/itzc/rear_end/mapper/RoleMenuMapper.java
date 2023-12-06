package com.itzc.rear_end.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itzc.rear_end.entity.RoleMenu;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RoleMenuMapper extends BaseMapper<RoleMenu> {
    @Delete("delete from sys_role_menu where role_id=#{roldId}")
    int deleteByRoleId(@Param("roldId") Integer roldId);

    @Select("select menu_id from sys_role_menu where role_id=#{roleId}")
    List<Integer> selectByRoleId(@Param("roleId") Integer roleId);
}
