package cn.edu.hdu.infosys.service.news.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.hdu.infosys.dao.INewsDao;
import cn.edu.hdu.infosys.dao.IUserDao;
import cn.edu.hdu.infosys.model.News;
import cn.edu.hdu.infosys.model.User;
import cn.edu.hdu.infosys.service.news.INewsService;



@Service("newsService")
public class NewsServiceImpl implements INewsService
{

    @Autowired
    private INewsDao newsDao;
    
    @Override
    public News saveNews(News news)
    {
        String tableName = this.getTableNameByTime(news.getPublishTime());
        //不存在，则创建表
        newsDao.createNewTable(tableName);

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("url", news.getUrl());
        param.put("title", news.getTitle());
        param.put("contentText", news.getContentText());
        param.put("contentHtml", news.getContentHtml());
        param.put("srcUrl", news.getSrcUrl());
        param.put("srcName", news.getSrcName());
        param.put("publishTime", news.getPublishTime());
        param.put("crawlerSrc", news.getCrawlerSrc());
        param.put("tableName", tableName);
        
        newsDao.saveNews(param);
        return news;
    }

    @Override
    public List<News> findByNews(News news)
    {
        newsDao.createNewTable(this.getTableNameByTime(news.getPublishTime()));
        
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("url", news.getUrl());
        
        param.put("tableName", this.getTableNameByTime(news.getPublishTime()));
        return newsDao.findByNews(param);
    }
    
    private String getTableNameByTime(String pubTime){
        String time = pubTime.replace("-","_");
        return "is_news_"+time.subSequence(0, 7);
    }

}
