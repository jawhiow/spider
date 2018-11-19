package com.cz.spider.repository.primary;

import com.cz.spider.model.primary.ProjectModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *  * description: description
 *  * author: jiangtao
 *  * date: 2018-11-08 14:31
 *  * modify: modify
 *  
 */
@Repository
public interface ProjectRepository extends JpaRepository<ProjectModel, Integer> {

    ProjectModel findFirstByXmmc(String xmmc);
}
