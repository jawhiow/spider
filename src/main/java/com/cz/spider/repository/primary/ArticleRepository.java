package com.cz.spider.repository.primary;

import com.cz.spider.model.primary.ArticleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

/**
 *  * description: description
 *  * author: jiangtao
 *  * date: 2018-11-08 14:41
 *  * modify: modify
 *  
 */
@Repository
public interface ArticleRepository extends JpaRepository<ArticleModel, Integer> {

    @Transactional(isolation = Isolation.DEFAULT)
    ArticleModel findFirstByColumnIdAndTitle(Integer columnId, String title);

    @Transactional(isolation = Isolation.DEFAULT)
    int countByColumnIdAndTitle(Integer columnId, String title);
}
