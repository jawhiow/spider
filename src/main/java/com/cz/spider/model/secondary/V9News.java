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
@Table(name = "v9_news")
@Data
public class V9News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer catid;
    private Integer typeid;
    private String title;
    private String style;
    private String thumb;
    private String keywords;
    private String description;
    private Integer posids;
    private String url;
    private Integer listorder;
    private Integer status;
    private Integer sysadd;
    private Integer islink;
    private String username;
    private Integer inputtime;
    private Integer updatetime;
//    private String pimg;
    private String zuozhe;
    private String copyfromurl;

}
