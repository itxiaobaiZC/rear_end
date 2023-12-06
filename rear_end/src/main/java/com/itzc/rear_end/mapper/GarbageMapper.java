package com.itzc.rear_end.mapper;

import com.itzc.rear_end.entity.Garbage;
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
 * @since 2023-11-09
 */
@Mapper
public interface GarbageMapper extends BaseMapper<Garbage> {

}
