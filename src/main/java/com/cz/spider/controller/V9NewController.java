package com.cz.spider.controller;

import com.cz.spider.model.primary.ProjectModel;
import com.cz.spider.model.secondary.V9News;
import com.cz.spider.repository.secondary.V9NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *  * description: description
 *  * author: jiangtao
 *  * date: 2018-11-19 16:50
 *  * modify: modify
 *  
 */
@RestController
@RequestMapping(value = "/v9")
public class V9NewController {

    @Autowired
    private V9NewsRepository v9NewsRepository;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<V9News> list() {
        try {
            return v9NewsRepository.findAll();
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }
}
