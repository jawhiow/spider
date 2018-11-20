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
    private Integer readpoint;
    private String groupids_view;
    private Integer paginationtype;
    private Integer maxcharperpage;
    private String template;
    private Integer paytype;
    private String relation;
    private Integer voteid;
    private Integer allow_comment;
    private String copyfrom;
    private String duopic;

    public V9NewsData() {
        this.readpoint = 0;
        this.groupids_view = "";
        this.paginationtype = 0;
        this.maxcharperpage = 0;
        this.template = "";
        this.paytype = 0;
        this.relation = "";
        this.voteid = 0;
        this.allow_comment = 1;
        this.copyfrom = "";
        this.duopic = "";
    }
}
