package com.itzc.rear_end.mapper;

import com.itzc.rear_end.entity.Policycomment;
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
 * @since 2023-11-22
 */
@Mapper
public interface PolicycommentMapper extends BaseMapper<Policycomment> {
    @Select("select c.*,u.uname,u.avatar_url from t_policycomment c left join sys_user u on c.user_id = u.id " +
            "where c.article_id = #{articleId} order by id desc")
    List<Policycomment> findCommentDetail(Integer articleId);
}
