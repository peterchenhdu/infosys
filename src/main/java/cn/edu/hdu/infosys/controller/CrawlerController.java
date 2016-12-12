package cn.edu.hdu.infosys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.hdu.infosys.common.base.BaseController;
import cn.edu.hdu.infosys.service.datacollection.news.News163CrawlerService;
import cn.edu.hdu.infosys.service.news.INewsService;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

/**
 * 
 * 
 * 
 * @author Pi Chen
 * @version infosys V1.0.0, 2016年12月10日
 * @see
 * @since infosys V1.0.0
 */
@Controller
@RequestMapping(value = "/cc")
public class CrawlerController extends BaseController
{
    @Autowired
    private INewsService newService;
    @Autowired
    private News163CrawlerService news163CrawlerService;
//    @RequestMapping(value = "test", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
//    @ResponseBody
//    public String test() 
//    {
//        return gson.toJson(newService.findByUrl("http://news.163.com/16/0831/19/BVQOVESP0001124J.html"));
//    }
    
    @RequestMapping(value = "start", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public void startCrawler() throws Exception
    {
        news163CrawlerService.start();
    }
}
