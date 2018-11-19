package com.cz.spider.repository.secondary;

import com.cz.spider.model.secondary.V9News;
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
public interface V9NewsRepository extends JpaRepository<V9News, Integer> {

}
