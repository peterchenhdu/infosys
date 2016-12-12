package cn.edu.hdu.infosys.service.datacollection.news;

import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.hdu.infosys.service.news.INewsService;
import cn.edu.hdu.infosys.service.news.impl.NewsServiceImpl;
import cn.edu.hdu.infosys.service.user.impl.UserServiceImpl;
import cn.edu.hdu.infosys.util.BeanUtil;
import cn.edu.hdu.infosys.util.StringUtils;
import cn.edu.hdu.infosys.model.News;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import edu.uci.ics.crawler4j.url.WebURL;

/**
 * 
 * 
 * 
 * @author Pi Chen
 * @version infosys V1.0.0, 2016年12月10日
 * @see
 * @since infosys V1.0.0
 */
@Service
public class News163CrawlerService extends WebCrawler
{

    private static long count=0;
    private String hrefStartWith="http://news.163.com/";
    private String crawldataFolder = "crawldata";
    private String maxThreadNum = "30";
    //请求建个ms
    private int politenessDelay = 1000;
    //最大爬取深度，-1为无限大
    private int maxDepth = 50;
    //爬取得最大页面数,-1为无限大
    private int maxPagesNum = -1;
    //种子数组
    private String[] seedArr = new String[]{
//        "http://news.163.com/07/0110/01/34EJ7LF20001124J.html"
        "http://news.163.com/",
        "http://news.163.com/rank/",
        "http://news.163.com/domestic/",
        "http://news.163.com/world/",
        "http://news.163.com/special/"
    };

    private INewsService newsService = BeanUtil.getBean("newsService", NewsServiceImpl.class); ;

    private static final Pattern IMAGE_EXTENSIONS = Pattern.compile(".*\\.(bmp|gif|jpg|png)$");

    /**
     * You should implement this function to specify whether the given url
     * should be crawled or not (based on your crawling logic).
     */
    @Override
    public boolean shouldVisit(Page referringPage, WebURL url)
    {
        String href = url.getURL().toLowerCase();
        // Ignore the url if it has an extension that matches our defined set of
        // image extensions.
        if (IMAGE_EXTENSIONS.matcher(href).matches())
        {
            return false;
        }

        // Only accept the url if it is in the "www.ics.uci.edu" domain and
        // protocol is "http".
        return href.startsWith(hrefStartWith);

    }

    /**
     * This function is called when a page is fetched and ready to be processed
     * by your program.
     */
    @Override
    public void visit(Page page)
    {

        String url = page.getWebURL().getURL();

        logger.info("URL: {}", url);

        //自定义过滤规则，根据网站编写
        if (url.length() < 30 || url.startsWith("http://news.163.com/") == false
            || Character.isDigit(url.charAt(20)) == false
            || Character.isDigit(url.charAt(21)) == false
            || Character.isDigit(url.charAt(23)) == false
            || Character.isDigit(url.charAt(24)) == false
            || Character.isDigit(url.charAt(25)) == false
            || Character.isDigit(url.charAt(26)) == false
            || Character.isDigit(url.charAt(28)) == false
            || Character.isDigit(url.charAt(29)) == false)
        {
            logger.info("[已过滤] URL: {}", url);
            return;
        }
        

        
        if (page.getParseData() instanceof HtmlParseData)
        {
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
    
            //使用jsoup解析html网页
            Document doc = Jsoup.parse(htmlParseData.getHtml());
            Element content = doc.getElementById("endText");

            if (content == null)
            {
                logger.info("invalid source:" + url);
                return;
            }
            content.select(".gg200x300").remove();

            String srcUrl = "", srcName = "", time = "", title = "";
            Element source = doc.getElementById("ne_article_source");
            if (source == null)
            { // 老的新闻布局，
                Element h1title = doc.getElementById("h1title");
                if (h1title == null)
                {
                    h1title = doc.getElementById("endTitle");
                }

                if(h1title==null){//example:http://news.163.com/07/0110/01/34EJ7LF20001124J.html
                    Element endMain = doc.getElementById("endMain");
                    Element h3Title = endMain.select(".arcTitle h3").get(0);
                    Element link0 = endMain.select(".arcTitle .text a[href]").get(0);
                    
                    srcUrl = link0.attr("href");
                    srcName = link0.text();
                    time = link0.parent().text().trim().substring(0, 19);
                    title = h3Title.text();
                    
//                    System.out.println(srcUrl);
//                    System.out.println(srcName);
//                    System.out.println(time);
//                    System.out.println(title);
                    
                }else{//example:http://news.163.com/09/1012/08/5LDM72M0000120GR.html
                    Element link0 = h1title.parent().select("a[href]").get(0);
                    srcUrl = link0.attr("href");
                    srcName = link0.text();
                    time = link0.parent().text().trim().substring(0, 19);
                    title = h1title.text();
                }


            }
            else
            // example:http://news.163.com/16/1208/21/C7PUKOT5000189FH.html
            {
                Element sourcep = source.parent();
                srcUrl = source.attr("href");
                srcName = source.text();
                time = sourcep.text().trim().substring(0, 19);
                title = doc.getElementById("epContentLeft").child(0).text();
            }

            News news = new News();

            news.setPublishTime(time);
            news.setSrcUrl(srcUrl);
            news.setSrcName(srcName);
            news.setTitle(title);

            news.setCrawlerSrc("news.163.com");
            news.setUrl(url);
            news.setContentHtml(content.html());
            news.setContentText(StringUtils.replaceBlank(content.text()));
            
            //判断是否已存在
            if (newsService.findByNews(news).size() > 0)
            {
                logger.info("[已存在] URL: {}", url);
                return;
            }
            
            newsService.saveNews(news);
            logger.info("[保存成功-{}] URL: {} ", ++count, url);
        }
    }

    public void start() throws Exception
    {

        /*
         * crawlStorageFolder is a folder where intermediate crawl data is
         * stored.
         */
        String crawlStorageFolder = crawldataFolder;

        /*
         * numberOfCrawlers shows the number of concurrent threads that should
         * be initiated for crawling.
         */
        int numberOfCrawlers = Integer.parseInt(maxThreadNum);

        CrawlConfig config = new CrawlConfig();

        config.setCrawlStorageFolder(crawlStorageFolder);

        /*
         * Be polite: Make sure that we don't send more than 1 request per
         * second (1000 milliseconds between requests).
         */
        config.setPolitenessDelay(politenessDelay);

        /*
         * You can set the maximum crawl depth here. The default value is -1 for
         * unlimited depth
         */
        config.setMaxDepthOfCrawling(maxDepth);

        /*
         * You can set the maximum number of pages to crawl. The default value
         * is -1 for unlimited number of pages
         */
        config.setMaxPagesToFetch(maxPagesNum);

        /**
         * Do you want crawler4j to crawl also binary data ? example: the
         * contents of pdf, or the metadata of images etc
         */
        config.setIncludeBinaryContentInCrawling(false);

        /*
         * Do you need to set a proxy? If so, you can use:
         * config.setProxyHost("proxyserver.example.com");
         * config.setProxyPort(8080);
         * 
         * If your proxy also needs authentication:
         * config.setProxyUsername(username); config.getProxyPassword(password);
         */

        /*
         * This config parameter can be used to set your crawl to be resumable
         * (meaning that you can resume the crawl from a previously
         * interrupted/crashed crawl). Note: if you enable resuming feature and
         * want to start a fresh crawl, you need to delete the contents of
         * rootFolder manually.
         */
        config.setResumableCrawling(false);

        /*
         * Instantiate the controller for this crawl.
         */
        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        // robotstxtConfig.setEnabled(false);
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);

        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);


      
        //添加种子Url，爬取的起始url
        for(String seed:seedArr){
            controller.addSeed(seed);
        }

        //开始爬取，阻塞操作，直到爬取结束. 
        controller.start(News163CrawlerService.class, numberOfCrawlers);
        logger.info("Finish Crawler...");
    }
}
