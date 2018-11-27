package com.cz.spider.service;

import com.cz.spider.crawler.MyCommandLine;
import com.cz.spider.crawler.MyCrawler;
import com.cz.spider.model.primary.ArticleModel;
import com.cz.spider.model.primary.ProjectModel;
import com.cz.spider.repository.primary.ArticleRepository;
import com.cz.spider.repository.primary.ProjectRepository;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
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

    private MyCommandLine run(String xmmc, Integer num) throws Exception {
        ProjectModel projectModel = projectRepository.findFirstByXmmc(xmmc);
        if (!StringUtils.isEmpty(projectModel.getMbzgz()) && !Objects.equals("*", projectModel.getMbzgz())) {
            MyCrawler.MATCHER =  Pattern.compile(projectModel.getMbzgz());
        }
        String contentSelector = projectModel.getNrjd();
        String titleSelector = projectModel.getBtjd();
        String timeSelector = projectModel.getSjjd();
        String lbjd = projectModel.getLbjd();
        MyCommandLine commandLine = new MyCommandLine(Arrays.asList(lbjd.split("&&")), contentSelector, titleSelector, timeSelector, projectModel);
        commandLine.run(num.toString());
        return commandLine;
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

    private boolean filter(ArticleModel model) {
        int count = articleRepository.countByColumnIdAndTitle(model.getColumnId(), model.getTitle().trim());
        return count == 0;
    }


    public void runAll(Integer num) throws Exception {
        List<ProjectModel> all = projectRepository.findAll();
        if (all.size() == 0) return;

        for (ProjectModel projectModel : all) {
            runByXmmc(projectModel.getXmmc(), num);
        }
    }

    public boolean runByXmmc(String xmmc, Integer num){
        CrawlController controller = null;
        try {
            System.out.println(xmmc + "开始....");
            MyCommandLine myCommandLine = run(xmmc, num);
            while (true) {
                controller = myCommandLine.getControllerMap().get(xmmc);
                if (controller.isFinished()) {
                    break;
                } else {
                    Thread.sleep(10 * 1000);
                }
            }
            System.out.println(xmmc + "结束....");
        } catch (Exception e) {
            e.printStackTrace();
            if (Objects.nonNull(controller)) {
                controller.shutdown();
                controller.waitUntilFinish();
            }
            return false;
        }

        return true;
    }


    public void save(List<Object> collect, ProjectModel projectModel) {
        try {
            List<ArticleModel> resultList = new ArrayList<>();
            for (Object object : collect) {

                if (!(object instanceof List)) continue;

                List list = (List) object;

                for (Object o : list) {

                    if (!(o instanceof Map)) continue;
                    Map map = (Map) o;

                    if (map.size() == 0) continue;
                    ArticleModel articleModel = new ArticleModel();
                    articleModel.setProjectName(projectModel.getXmmc());
                    if (Objects.nonNull(map.get("title")))
                        articleModel.setArticleName(map.get("title").toString());
                    else
                        articleModel.setArticleName("");
                    if (Objects.nonNull(map.get("time")))
                        articleModel.setArticleCreateTime(handleTime(map.get("time").toString()));
                    else
                        articleModel.setArticleCreateTime("");
                    articleModel.setColumnId(projectModel.getLmid());
                    if (Objects.nonNull(map.get("content")))
                        articleModel.setContent(map.get("content").toString());
                    else
                        articleModel.setContent("");
                    if (Objects.nonNull(map.get("title")))
                        articleModel.setTitle(map.get("title").toString());
                    else
                        articleModel.setTitle("");
                    articleModel.setProjectId(projectModel.getId());
                    resultList.add(articleModel);
                }


            }

            resultList = resultList.stream().filter(this::filter).collect(Collectors.toList());

            if (resultList.size() > 0) articleRepository.saveAll(resultList);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
