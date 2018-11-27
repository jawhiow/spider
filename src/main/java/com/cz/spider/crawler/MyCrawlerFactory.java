package com.cz.spider.crawler;

import com.cz.spider.model.primary.ProjectModel;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import lombok.Data;

/**
 *  * description: description
 *  * author: jiangtao
 *  * date: 2018-11-08 16:13
 *  * modify: modify
 *  
 */
@Data
public class MyCrawlerFactory implements CrawlController.WebCrawlerFactory {

    private String contentSelector;
    private String titleSelector;
    private String timeSelector;
    private ProjectModel projectModel;

    private MyCrawler myCrawler;

    public MyCrawlerFactory(String contentSelector, String titleSelector, String timeSelector, ProjectModel projectModel) {
        this.contentSelector = contentSelector;
        this.titleSelector = titleSelector;
        this.timeSelector = timeSelector;
        this.projectModel = projectModel;
    }

    @Override
    public WebCrawler newInstance() throws Exception {
        myCrawler = new MyCrawler(contentSelector, titleSelector, timeSelector, projectModel);
        return myCrawler;
    }
}
