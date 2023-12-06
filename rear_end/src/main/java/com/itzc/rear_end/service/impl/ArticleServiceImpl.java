package com.itzc.rear_end.service.impl;

import com.itzc.rear_end.entity.Article;
import com.itzc.rear_end.mapper.ArticleMapper;
import com.itzc.rear_end.service.IArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author itzc
 * @since 2023-11-07
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {

}
