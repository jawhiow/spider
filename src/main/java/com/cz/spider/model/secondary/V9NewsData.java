package com.cz.spider.model.secondary;

import lombok.Data;

import javax.persistence.*;

/**
 *  * description: description
 *  * author: jiangtao
 *  * date: 2018-11-08 14:37
 *  * modify: modify
 *  
 */
@Entity
@Table(name = "v9_news_data")
@Data
public class V9NewsData {
    @Id
    private Integer id;
    private String content;
    private Integer readpoint = 0;
    private String groupids_view = "";
    private Integer paginationtype = 0;
    private Integer maxcharperpage = 0;
    private String template = "";
    private Integer paytype = 0;
    private String relation = "";
    private Integer voteid = 0;
    private Integer allow_comment = 1;
    private String copyfrom = "";
    private String duopic = "";


}
