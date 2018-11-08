package com.cz.spider.controller;

import com.cz.spider.model.ProjectModel;
import com.cz.spider.repository.ProjectRepository;
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
@RequestMapping(value = "/project")
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepository;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(ProjectModel projectModel) {
        try {
            projectRepository.saveAndFlush(projectModel);
            return "success";
        } catch (Exception e){
            e.printStackTrace();
            return "error";
        }

    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String get() {
        System.out.println("====");
        return "";
    }


}
