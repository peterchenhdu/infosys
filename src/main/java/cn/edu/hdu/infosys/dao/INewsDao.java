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
    public int saveNews(Map<String, Object> param);
    
    public List<News> findByNews(Map<String, Object> param);
    
    public void createNewTable(@Param(value = "tableName") String tableName);
}
