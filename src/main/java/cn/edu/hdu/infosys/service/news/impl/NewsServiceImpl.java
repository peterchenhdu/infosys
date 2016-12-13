package cn.edu.hdu.infosys.service.news.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.hdu.infosys.dao.INewsDao;
import cn.edu.hdu.infosys.model.News;
import cn.edu.hdu.infosys.service.news.INewsService;



@Service("newsService")
public class NewsServiceImpl implements INewsService
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
        return newsDao.getYearCount(param);
    }

}
