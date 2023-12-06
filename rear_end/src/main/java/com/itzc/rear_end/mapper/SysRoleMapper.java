package com.itzc.rear_end.mapper;

import com.itzc.rear_end.entity.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author itzc
 * @since 2023-10-31
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

    @Select("select id from sys_role where flag=#{flag}")
    Integer selectByFlag(@Param("flag") String flag);
}
