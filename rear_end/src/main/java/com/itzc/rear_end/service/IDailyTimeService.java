package com.itzc.rear_end.service;

import com.itzc.rear_end.entity.DailyTime;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author itzc
 * @since 2023-11-11
 */
public interface IDailyTimeService extends IService<DailyTime> {

    List<DailyTime> listDesc();
}
