package com.cz.spider.model.primary;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 *  * description: description
 *  * author: jiangtao
 *  * date: 2018-11-08 14:37
 *  * modify: modify
 *  
 */
@Entity
@Table(name = "article")
@Data
public class ArticleModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer  id ;
    @Column(name = "project_id")
    private Integer  projectId;
    @Column(name = "column_id")
    private Integer  columnId;
    @Column(name = "project_name")
    private String  projectName;
    @Column(name = "article_name")
    private String  articleName;
    private String  title;
    private String  content;
    @Column(name = "article_create_time")
    private String  articleCreateTime;
    @Column(name = "create_time")
    private Timestamp createTime;
}
