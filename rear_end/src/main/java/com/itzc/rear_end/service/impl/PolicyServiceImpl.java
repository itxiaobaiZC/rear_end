package com.itzc.rear_end.service.impl;

import com.itzc.rear_end.entity.Policy;
import com.itzc.rear_end.mapper.PolicyMapper;
import com.itzc.rear_end.service.IPolicyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author itzc
 * @since 2023-11-12
 */
@Service
public class PolicyServiceImpl extends ServiceImpl<PolicyMapper, Policy> implements IPolicyService {

}
