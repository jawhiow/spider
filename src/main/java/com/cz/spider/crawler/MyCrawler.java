package com.cz.spider.crawler;

/**
 *  * description: description
 *  * author: jiangtao
 *  * date: 2018-11-08 11:12
 *  * modify: modify
 *  
 */

import com.cz.spider.model.primary.ProjectModel;
import com.cz.spider.service.SpiderService;
import com.cz.spider.utils.SpringUtil;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;
import java.util.regex.Pattern;

/**
 * 自定义爬虫类myCrawler需要继承WebCrawler,决定哪些url被爬取以及处理爬的页面信息
 */
public class MyCrawler extends WebCrawler {

    private String contentSelector;
    private String titleSelector;
    private String timeSelector;
    private ProjectModel projectModel;

    public List<Map<String, String>> resultList;
//    public Map<String, String> map;


    public MyCrawler(String contentSelector, String titleSelector, String timeSelector, ProjectModel projectModel) {
        this.contentSelector = contentSelector;
        this.titleSelector = titleSelector;
        this.timeSelector = timeSelector;
        this.projectModel = projectModel;
        this.resultList = new ArrayList<>();
//        map = new HashMap<>();
    }

    /**
     * 正则匹配指定的后缀文件
     */
    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|"
            + "|tiff?|mid|mp2|mp3|mp4" + "|wav|avi|mov|mpeg|ram|m4v|pdf"
            + "|rm|smil|wmv|swf|wma|zip|rar|gz))$");

    public static Pattern MATCHER = Pattern.compile("^.*(\\.(html))$");


    /**
     *   根据url进行网页的解析，对返回为TRUE的网页进行抓取
     *   第一个参数referringPage封装了当前爬取的页面信息
     *   第二个参数封装了当前爬取页面的url信息
     */
    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        //小写url
        // .toLowerCase()
        String href = url.getURL();
        // System.out.println("当前访问为：" + url.getURL());
        //正则匹配，过滤掉我们不需要的后缀文件
        return MATCHER.matcher(href).matches()//匹配过滤掉不需要的后缀文件
                && href.startsWith(url.getURL());//url必须是http://www.baidu.com开头
    }

    /**
     * 解析网页内容，page类包含了丰富的方法，可以利用这些方法得到网页的内容和属性
     * 当我们爬取到我们需要的页面，这个方法会被调用，我们可以随意处理页面
     * page 封装了所有页面信息
     *
     */
    @Override
    public void visit(Page page) {
        //获取url
        String url=page.getWebURL().getURL();
        System.out.println("url:"+url);
        //判断是否是html数据
        if(page.getParseData() instanceof HtmlParseData){
            //强制类型转换，获取html数据对象
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            //获得页面纯文本
            String text = htmlParseData.getText();
            //获得页面html
            String html = htmlParseData.getHtml();
            //获取页面输出链接
            Set<WebURL> links = htmlParseData.getOutgoingUrls();

            Document document = Jsoup.parse(html);
            Element contentElement = document.select(this.contentSelector).first();
            Element timeElement = document.select(this.timeSelector).first();
            Element titleElement = document.select(this.titleSelector).first();

            Map<String, String> map = new HashMap<>();

            if (Objects.nonNull(timeElement)) {
                System.out.println("=========时间内容=========");
                System.out.println(timeElement.html());
                map.put("time", timeElement.html());
            }

            if (Objects.nonNull(contentElement)) {
                System.out.println("=========文章内容=========");
                System.out.println(contentElement.html());
                map.put("content", contentElement.html());
            }

            if (Objects.nonNull(titleElement)) {
                System.out.println("=========标题内容=========");
                System.out.println(titleElement.html());
                map.put("title", titleElement.html());
            }

            resultList.add(map);


            System.out.println("纯文本长度: " + text.length());
            System.out.println("html长度: " + html.length());
            System.out.println("输出链接个数: " + links.size());
        }
    }

    @Override
    public Object getMyLocalData() {
        return resultList;
    }

    @Override
    public void onBeforeExit() {
        System.out.println("=============当前线程爬取完毕准备入库================");
        SpiderService spiderService = SpringUtil.getBean(SpiderService.class);
        spiderService.save(Collections.singletonList(resultList), projectModel);
    }
}

