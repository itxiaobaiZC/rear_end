package com.itzc.rear_end.service;

import com.itzc.rear_end.entity.Homepic;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author itzc
 * @since 2023-11-18
 */
public interface IHomepicService extends IService<Homepic> {

    List<String> findByEnable();
}
