package com.itzc.rear_end.mapper;

import com.itzc.rear_end.entity.Homepic;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author itzc
 * @since 2023-11-18
 */
@Mapper
public interface HomepicMapper extends BaseMapper<Homepic> {

    @Select("select pathurl from homepic where enable='1'")
    List<String> findByEnable();
}
