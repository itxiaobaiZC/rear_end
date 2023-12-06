package com.itzc.rear_end.service;

import com.itzc.rear_end.entity.Policycomment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author itzc
 * @since 2023-11-22
 */
public interface IPolicycommentService extends IService<Policycomment> {

    List<Policycomment> findCommentDetail(Integer articleId);
}
