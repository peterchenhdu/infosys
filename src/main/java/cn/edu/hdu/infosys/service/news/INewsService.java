package cn.edu.hdu.infosys.service.news;

import java.util.List;

import cn.edu.hdu.infosys.model.News;

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

    public List<News> findByMonth(String month, long offset, long limit);

    public long getYearCount(int year);

    public long getCount(String month);
}
