package com.itzc.rear_end.mapper;

import com.itzc.rear_end.controller.dto.UserPasswordDTO;
import com.itzc.rear_end.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author itzc
 * @since 2023-10-27
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    @Update("update sys_user set password=#{newPassword} where name=#{name} and password=#{password}")
    int updatePassword(UserPasswordDTO userPasswordDTO);

    @Select("select name ,uname,number,address,role from sys_user where name=#{name}")
    SysUser selectByName(String name);
}
