package com.itzc.rear_end.service.impl;

import com.itzc.rear_end.entity.Homepic;
import com.itzc.rear_end.mapper.HomepicMapper;
import com.itzc.rear_end.service.IHomepicService;
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
 * @since 2023-11-18
 */
@Service
public class HomepicServiceImpl extends ServiceImpl<HomepicMapper, Homepic> implements IHomepicService {

    @Autowired
    private HomepicMapper homepicMapper;

    @Override
    public List<String> findByEnable() {
        return homepicMapper.findByEnable();
    }
}
