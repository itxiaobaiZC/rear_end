package com.itzc.rear_end.service.impl;

import com.itzc.rear_end.entity.Policycomment;
import com.itzc.rear_end.mapper.PolicycommentMapper;
import com.itzc.rear_end.service.IPolicycommentService;
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
 * @since 2023-11-22
 */
@Service
public class PolicycommentServiceImpl extends ServiceImpl<PolicycommentMapper, Policycomment> implements IPolicycommentService {

    @Autowired
    private PolicycommentMapper policycommentMapper;
    @Override
    public List<Policycomment> findCommentDetail(Integer articleId) {
        return policycommentMapper.findCommentDetail(articleId);
    }
}
