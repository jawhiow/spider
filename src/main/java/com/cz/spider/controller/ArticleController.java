package com.cz.spider.controller;

import com.cz.spider.model.primary.ArticleModel;
import com.cz.spider.repository.primary.ArticleRepository;
import com.cz.spider.service.V9Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *  * description: description
 *  * author: jiangtao
 *  * date: 2018-11-08 14:51
 *  * modify: modify
 *  
 */
@RestController
@RequestMapping(value = "/article")
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private V9Service v9Service;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String get() {
        System.out.println("====");
        return "";
    }

    @RequestMapping(value = "/save", method = RequestMethod.GET)
    public String save() {
        try {
            List<ArticleModel> all = articleRepository.findAll();
            if (v9Service.saveV9ForList(all)) return "success";
            else return "error";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }


}
