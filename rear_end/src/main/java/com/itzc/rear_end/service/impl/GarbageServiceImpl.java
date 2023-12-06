package com.itzc.rear_end.service.impl;

import com.itzc.rear_end.entity.Garbage;
import com.itzc.rear_end.mapper.GarbageMapper;
import com.itzc.rear_end.service.IGarbageService;
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
 * @since 2023-11-09
 */
@Service
public class GarbageServiceImpl extends ServiceImpl<GarbageMapper, Garbage> implements IGarbageService {

    @Autowired
    private GarbageMapper garbageMapper;


}
