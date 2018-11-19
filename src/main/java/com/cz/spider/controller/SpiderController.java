package com.cz.spider.controller;

import com.cz.spider.service.SpiderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *  * description: description
 *  * author: jiangtao
 *  * date: 2018-11-08 14:51
 *  * modify: modify
 *  
 */
@RestController
@RequestMapping(value = "/spider")
public class SpiderController {

    @Autowired
    private SpiderService spiderService;


    @RequestMapping(value = "/run", method = RequestMethod.GET)
    public String run(String xmmc, Integer num) {
        try {
            spiderService.run(xmmc, num);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }


}
