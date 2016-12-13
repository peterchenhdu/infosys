package cn.edu.hdu.infosys.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.hdu.infosys.common.base.BaseController;
import cn.edu.hdu.infosys.service.datacollection.news.News163CrawlerService;
import cn.edu.hdu.infosys.service.news.INewsService;

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
    @RequestMapping(value = "test", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String test() 
    {
        //return gson.toJson(newService.findByUrl("http://news.163.com/16/0831/19/BVQOVESP0001124J.html"));
//        String text1 = "我爱购物";
//        String text2 = "我爱读书";
//        String text3 = "他是黑客";
//        TextSimilarity textSimilarity = new SimpleTextSimilarity();
//        double score1pk1 = textSimilarity.similarScore(text1, text1);
//        double score1pk2 = textSimilarity.similarScore(text1, text2);
//        double score1pk3 = textSimilarity.similarScore(text1, text3);
//        double score2pk2 = textSimilarity.similarScore(text2, text2);
//        double score2pk3 = textSimilarity.similarScore(text2, text3);
//        double score3pk3 = textSimilarity.similarScore(text3, text3);
//        System.out.println(text1+" 和 "+text1+" 的相似度分值："+score1pk1);
//        System.out.println(text1+" 和 "+text2+" 的相似度分值："+score1pk2);
//        System.out.println(text1+" 和 "+text3+" 的相似度分值："+score1pk3);
//        System.out.println(text2+" 和 "+text2+" 的相似度分值："+score2pk2);
//        System.out.println(text2+" 和 "+text3+" 的相似度分值："+score2pk3);
//        System.out.println(text3+" 和 "+text3+" 的相似度分值："+score3pk3);
        return "";
    }
    
    @RequestMapping(value = "start163Crawler", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public void startCrawler() throws Exception
    {
        news163CrawlerService.start();
    }
}
