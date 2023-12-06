package com.itzc.rear_end.service.impl;

import com.itzc.rear_end.entity.DailyTime;
import com.itzc.rear_end.mapper.DailyTimeMapper;
import com.itzc.rear_end.service.IDailyTimeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author itzc
 * @since 2023-11-11
 */
@Service
public class DailyTimeServiceImpl extends ServiceImpl<DailyTimeMapper, DailyTime> implements IDailyTimeService {

    @Autowired
    private DailyTimeMapper dailyTimeMapper;


    @Override
    public List<DailyTime> listDesc() {
        return dailyTimeMapper.listDesc();
    }
}
