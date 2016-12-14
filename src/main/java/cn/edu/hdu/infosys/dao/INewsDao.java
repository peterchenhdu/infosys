package cn.edu.hdu.infosys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.edu.hdu.infosys.model.News;

/**
 * @author Pi Chen
 * @version infosys V1.0.0, 2016年12月10日
 * @see
 * @since infosys V1.0.0
 */

public interface INewsDao
{
    public int saveNewsCont(Map<String, Object> param);

    public int saveNewsSum(Map<String, Object> param);

    public List<News> findByNews(Map<String, Object> param);

    public void createNewTableCont(@Param(value = "tableName") String tableName);

    public void createNewTableSum(@Param(value = "tableName") String tableName);

    public List<News> findByTime(@Param(value = "tableName") String tableName,
        @Param(value = "from") String from, @Param(value = "to") String to,
        @Param(value = "offset") long offset, @Param(value = "limit") long limit);

    public long getYearCount(Map<String, Object> param);
}
