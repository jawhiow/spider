package com.cz.spider.service;

import com.cz.spider.model.primary.ArticleModel;
import com.cz.spider.model.primary.ProjectModel;
import com.cz.spider.model.secondary.V9News;
import com.cz.spider.model.secondary.V9NewsData;
import com.cz.spider.repository.primary.ProjectRepository;
import com.cz.spider.repository.secondary.V9NewsDataRepository;
import com.cz.spider.repository.secondary.V9NewsRepository;
import com.hankcs.hanlp.HanLP;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 *  * description: description
 *  * author: jiangtao
 *  * date: 2018-11-19 17:09
 *  * modify: modify
 *  
 */
@Service
public class V9Service {

    @Autowired
    private V9NewsRepository v9NewsRepository;

    @Autowired
    private V9NewsDataRepository v9NewsDataRepository;

    @Autowired
    private ProjectRepository projectRepository;


    public boolean saveV9ForOne(ArticleModel articleModel) {
        Integer id = article2V9News(articleModel);
        article2V9NewsData(articleModel, id);
        return true;
    }

    public boolean saveV9ForList(List<ArticleModel> list) {
        Set<Boolean> collect = list.stream().map(this::saveV9ForOne).collect(Collectors.toSet());
        return collect.size() == 1;
    }

    private Integer article2V9News(ArticleModel article) {
        V9News v9News = new V9News();
        v9News.setCatid(article.getColumnId());
        v9News.setTypeid(0);
        v9News.setTitle(article.getTitle());
        v9News.setStyle("");
        v9News.setThumb("");
        String contentText = article.getContent().replaceAll("[^\\u4e00-\\u9fa5|^\\u3002\\uff1b\\uff0c\\uff1a\\u201c\\u201d\\uff08\\uff09\\u3001\\uff1f\\u300a\\u300b]", "");
        Optional<String> reduce = HanLP.extractKeyword(contentText, 4).stream()
                .reduce((a, b) -> a + " " + b);
        v9News.setKeywords("");
        reduce.ifPresent(v9News::setKeywords);
        v9News.setDescription(article.getTitle());
        v9News.setPosids(0);
        v9News.setUrl("");
        v9News.setListorder(0);
        v9News.setStatus(99);
        v9News.setSysadd(1);
        v9News.setIslink(0);
        v9News.setUsername("weiwo");
        String time = System.currentTimeMillis() + "";
        time = time.substring(0, time.length() - 3);
        v9News.setInputtime(Integer.parseInt(time));
        v9News.setUpdatetime(Integer.parseInt(time));
//        v9News.setPimg("");
        v9News.setCopyfromurl("");
        v9News.setZuozhe("");

        V9News newV9News = v9NewsRepository.saveAndFlush(v9News);
        String url = "/index.php?m=content&c=index&a=show&catid="+ v9News.getCatid() +"&id=" + newV9News.getId();
        newV9News.setUrl(url);
        v9NewsRepository.saveAndFlush(newV9News);

        return newV9News.getId();

    }

    private void article2V9NewsData(ArticleModel article, Integer id) {
        V9NewsData v9NewsData = new V9NewsData();
        v9NewsData.setId(id);
        Optional<ProjectModel> projectModelOptional = projectRepository.findById(article.getProjectId());
        String url = "";
        if (projectModelOptional.isPresent()) {
            ProjectModel projectModel = projectModelOptional.get();
            url = projectModel.getLywz();
        }
        v9NewsData.setContent(handleHref(url, article.getContent()));
        v9NewsDataRepository.saveAndFlush(v9NewsData);
    }

    private static String handleHref(String url, String content) {
        Pattern pattern = Pattern.compile("<[Aa]\\s+(.*?\\s+)*?href\\s*=\\s*(['\"])(.+?)\\2(\\s+.*?\\s*)*?>.+?</[Aa]>");
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            String group = matcher.group(3);
            String newUrl = url + group;
            content = content.replaceAll(group, newUrl);
        }

        return content;
    }
}
