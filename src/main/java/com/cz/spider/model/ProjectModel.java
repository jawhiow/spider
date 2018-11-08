package com.cz.spider.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 *  * description: description
 *  * author: jiangtao
 *  * date: 2018-11-08 14:24
 *  * modify: modify
 *  
 */
@Entity
@Table(name = "project")
@Data
public class ProjectModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String xmmc;
    private Integer lmid;
    private Timestamp jzsj;
    private String mbzgz ;
    private String lbjd;
    private String btjd;
    private String nrjd;
    private String sjjd;
    private String ly;
    private String lywz;
}
