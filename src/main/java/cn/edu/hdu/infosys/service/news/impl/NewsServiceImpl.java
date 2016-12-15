package cn.edu.hdu.infosys.service.news.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.stereotype.Service;

import cn.edu.hdu.infosys.common.base.BaseService;
import cn.edu.hdu.infosys.dao.INewsDao;
import cn.edu.hdu.infosys.model.News;
import cn.edu.hdu.infosys.service.news.INewsService;
import cn.edu.hdu.infosys.util.TimeUtil;



@Service("newsService")
public class NewsServiceImpl extends BaseService implements INewsService
{

    @Autowired
    private INewsDao newsDao;

    @Override
    public News saveNews(News news)
    {
        String contTableName = this.getContTableNameByTime(news.getPublishTime());
        String sumTableName = this.getSumTableNameByTime(news.getPublishTime());
        //不存在，则创建表
        newsDao.createNewTableCont(contTableName);
        newsDao.createNewTableSum(sumTableName);

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("url", news.getUrl());
        param.put("title", news.getTitle());
        param.put("contentText", news.getContentText());
        param.put("contentHtml", news.getContentHtml());
        param.put("srcUrl", news.getSrcUrl());
        param.put("srcName", news.getSrcName());
        param.put("publishTime", news.getPublishTime());
        param.put("crawlerSrc", news.getCrawlerSrc());
        param.put("tableName", contTableName);
        newsDao.saveNewsCont(param);
        param.put("tableName", sumTableName);
        newsDao.saveNewsSum(param);
        return news;
    }

    @Override
    public List<News> findByNews(News news)
    {
        String contTableName = this.getContTableNameByTime(news.getPublishTime());
        String sumTableName = this.getSumTableNameByTime(news.getPublishTime());
        //不存在，则创建表
        newsDao.createNewTableCont(contTableName);
        newsDao.createNewTableSum(sumTableName);

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("url", news.getUrl());
        param.put("tableName", contTableName);
        return newsDao.findByNews(param);
    }

    private String getContTableNameByTime(String pubTime){
        String time = pubTime.replace("-","_");
        return "is_news_cont_"+time.subSequence(0, 7);
    }

    private String getSumTableNameByTime(String pubTime){
        return "is_news_sum_"+pubTime.subSequence(0, 4);
    }

    @Override
    public long getYearCount(int year)
    {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("tableName", "is_news_sum_"+year);
        long count = 0;
        try{
            count  = newsDao.getCount(param);
        }catch(BadSqlGrammarException e){
            logger.warn("Bad Sql Grammar Exception, this often happened when table is_news_sum_{} does not exist.", year);
            logger.error(e.toString(),e);
        }

        return count;
    }

    @Override
    public List<News> findByMonth(String month, long offset, long limit)
    {
        String tableName = this.getSumTableNameByTime(month);
        String from = month + "-01 00:00:00";
        String to = TimeUtil.getDate(from, 0, 1, 0, 0);
        List<News> rst = new ArrayList<News>();

        try{
            rst = newsDao.findByTime(tableName, from, to, offset, limit);
        }catch(BadSqlGrammarException e){
            logger.warn("Bad Sql Grammar Exception, this often happened when table is_news_sum_{} does not exist.", month.subSequence(0, 4));
            logger.error(e.toString(),e);
        }

        for(News news : rst){
            switch (news.getCrawlerSrc())
            {
            case "news.163.com":
                news.setCrawlerName("网易新闻");
                break;

            default:
                break;
            }
        }
        return rst;
    }


    @Override
    public long getCount(String month)
    {
        String tableName = this.getContTableNameByTime(month);
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("tableName", tableName);
        long count = 0;
        try{
            count = newsDao.getCount(param);
        }catch(BadSqlGrammarException e){
            logger.warn("Bad Sql Grammar Exception, this often happened when table is_news_cont_{} does not exist.", month.subSequence(0, 7));
            logger.error(e.toString(),e);
        }
        return count;
    }

}
