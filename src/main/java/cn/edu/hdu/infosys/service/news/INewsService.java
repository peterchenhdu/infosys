package cn.edu.hdu.infosys.service.news;

import java.util.List;
import java.util.Map;

import cn.edu.hdu.infosys.model.News;
import cn.edu.hdu.infosys.model.User;

/**
 * 
 * 
 * 
 * @author    Pi Chen
 * @version   infosys V1.0.0, 2016年12月10日
 * @see       
 * @since     infosys V1.0.0
 */
public interface INewsService
{
    
    public News saveNews(News news);
    
    public List<News> findByNews(News news);

}
