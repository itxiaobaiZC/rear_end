package com.itzc.rear_end.mapper;

import com.itzc.rear_end.entity.DailyTime;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author itzc
 * @since 2023-11-11
 */
@Mapper
public interface DailyTimeMapper extends BaseMapper<DailyTime> {

    @Select("SELECT * FROM daily_time ORDER BY logtime DESC ")
    List<DailyTime> listDesc();
}
