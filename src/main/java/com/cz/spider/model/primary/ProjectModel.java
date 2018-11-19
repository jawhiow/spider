package com.cz.spider.model.primary;

import lombok.Data;

import javax.persistence.*;
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
    private String jzsj;
    private String mbzgz ;
    private String lbjd;
    private String btjd;
    private String nrjd;
    private String sjjd;
    private String ly;
    private String lywz;
}
