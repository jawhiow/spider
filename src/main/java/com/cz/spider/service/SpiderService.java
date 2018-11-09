package com.cz.spider.service;

import com.cz.spider.crawler.MyCommandLine;
import com.cz.spider.crawler.MyCrawler;
import com.cz.spider.model.ArticleModel;
import com.cz.spider.model.ProjectModel;
import com.cz.spider.repository.ArticleRepository;
import com.cz.spider.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 *  * description: description
 *  * author: jiangtao
 *  * date: 2018-11-08 15:58
 *  * modify: modify
 *  
 */
@Service
public class SpiderService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ArticleRepository articleRepository;

    public void run(String xmmc) throws Exception {
        ProjectModel projectModel = projectRepository.findFirstByXmmc(xmmc);
        String contentSelector = projectModel.getNrjd();
        String titleSelector = projectModel.getBtjd();
        String timeSelector = projectModel.getSjjd();
        String lbjd = projectModel.getLbjd();
        MyCommandLine commandLine = new MyCommandLine(Arrays.asList(lbjd.split("&&")), contentSelector, titleSelector, timeSelector);
        commandLine.run();
        List<ArticleModel> resultList = new ArrayList<>();
        List<Object> collect = commandLine.getMapList().stream().filter(t -> t instanceof List)
                .filter(t -> ((List) t).size() > 0)
                .collect(Collectors.toList());
        for (Object object : collect) {

            if (!(object instanceof List)) continue;

            List list = (List) object;

            for (Object o : list) {

                if (!(o instanceof Map)) continue;
                Map map = (Map) o;

                if (map.size() == 0) continue;
                ArticleModel articleModel = new ArticleModel();
                articleModel.setProjectName(projectModel.getXmmc());
                articleModel.setArticleName(map.get("title").toString());
                articleModel.setArticleCreateTime(handleTime(map.get("time").toString()));
                articleModel.setColumnId(projectModel.getLmid());
                articleModel.setContent(map.get("content").toString());
                articleModel.setTitle(map.get("title").toString());
                articleModel.setProjectId(projectModel.getId());
                resultList.add(articleModel);
            }


        }

        if (resultList.size() > 0) articleRepository.saveAll(resultList);
    }

    private String handleTime(String str) {
        str = str.replaceAll("\n", "");
        Pattern pattern = Pattern.compile("(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))");
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            return matcher.group(1);
        }

        return str;
    }



}
