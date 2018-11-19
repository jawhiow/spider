package com.cz.spider.repository.primary;

import com.cz.spider.model.primary.ArticleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *  * description: description
 *  * author: jiangtao
 *  * date: 2018-11-08 14:41
 *  * modify: modify
 *  
 */
@Repository
public interface ArticleRepository extends JpaRepository<ArticleModel, Integer> {
}
