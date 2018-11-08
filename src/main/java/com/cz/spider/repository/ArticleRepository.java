package com.cz.spider.repository;

import com.cz.spider.model.ArticleModel;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *  * description: description
 *  * author: jiangtao
 *  * date: 2018-11-08 14:41
 *  * modify: modify
 *  
 */
public interface ArticleRepository extends JpaRepository<Integer, ArticleModel> {
}
