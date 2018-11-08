package com.cz.spider.crawler;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import lombok.Data;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 *  * description: description
 *  * author: jiangtao
 *  * date: 2018-11-08 11:15
 *  * modify: modify
 *  
 */
@Data
public class MyCommandLine implements CommandLineRunner {

    private List<String> links = new ArrayList<>();

    private String contentSelector;
    private String titleSelector;
    private String timeSelector;

    public MyCommandLine(List<String> links, String contentSelector, String titleSelector, String timeSelector) {
        this.links = links;
        this.contentSelector = contentSelector;
        this.titleSelector = titleSelector;
        this.timeSelector = timeSelector;
    }

    @Override
    public void run(String... args) throws Exception {
        //定义爬虫存储的位置
        String crawStorageFoler="d:/crawler";
        //定义爬虫线程7个
        int numberOfCrawlers = 1;
        //定义爬虫配置
        CrawlConfig config = new CrawlConfig();
        //设置爬虫文件存储位置
        config.setCrawlStorageFolder(crawStorageFoler);
        //实例化页面获取器
        PageFetcher pageFetcher = new PageFetcher(config);
        //实例化爬虫机器人配置,比如可以设置user-agent
        RobotstxtConfig robotstxtconfig = new RobotstxtConfig();
        //实例化爬虫机器人对目标服务器的配置，每个网站都有一个robots.txt文件
        //规定了该网站哪些页面可以爬，哪些页面禁止爬，该类是对robots.txt规范的实现
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtconfig, pageFetcher);
        //实例化爬虫控制器
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);
        //配置爬取种子页面，就是规定从哪里开始爬，可以配置多个种子页面
        for (String link : links) {
            controller.addSeed(link);
        }
        // controller.addSeed("http://www.hfcz.gov.cn/544/hfczdtn/index.html");


        //启动爬虫，爬虫从此刻开始执行爬虫任务
        // controller.start(MyCrawler.class, numberOfCrawlers);
        MyCrawlerFactory factory = new MyCrawlerFactory(contentSelector, titleSelector, timeSelector);
        controller.startNonBlocking(factory, numberOfCrawlers);
    }
}