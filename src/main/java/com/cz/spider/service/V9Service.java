package com.cz.spider.service;

import com.cz.spider.model.primary.ArticleModel;
import com.cz.spider.model.secondary.V9News;
import com.cz.spider.model.secondary.V9NewsData;
import com.cz.spider.repository.secondary.V9NewsDataRepository;
import com.cz.spider.repository.secondary.V9NewsRepository;
import com.hankcs.hanlp.HanLP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
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


    public boolean saveV9ForOne(ArticleModel articleModel) {
        article2V9News(articleModel);
        article2V9NewsData(articleModel);
        return true;
    }

    public boolean saveV9ForList(List<ArticleModel> list) {
        Set<Boolean> collect = list.stream().map(this::saveV9ForOne).collect(Collectors.toSet());
        return collect.size() == 1;
    }

    private void article2V9News(ArticleModel article) {
        V9News v9News = new V9News();
        v9News.setCatid(article.getColumnId());
        v9News.setTypeid(0);
        v9News.setTitle(article.getTitle());
        v9News.setStyle("");
        v9News.setThumb("");
        Optional<String> reduce = HanLP.extractKeyword(article.getContent(), 5).stream()
                .reduce((a, b) -> a + " " + b);
        v9News.setKeywords("");
        reduce.ifPresent(v9News::setKeywords);
        v9News.setDescription(article.getContent().substring(0, 15));
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
        v9News.setPimg("");

        V9News newV9News = v9NewsRepository.saveAndFlush(v9News);
        String url = "/index.php?m=content&c=index&a=show&catid="+ v9News.getCatid() +"&id=" + newV9News.getId();
        newV9News.setUrl(url);
        v9NewsRepository.saveAndFlush(newV9News);

    }

    private void article2V9NewsData(ArticleModel article) {
        V9NewsData v9NewsData = new V9NewsData();
        v9NewsData.setId(article.getColumnId());
        v9NewsData.setContent(article.getContent());

        v9NewsDataRepository.saveAndFlush(v9NewsData);
    }
}
