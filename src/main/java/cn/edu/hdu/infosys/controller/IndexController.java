package cn.edu.hdu.infosys.controller;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.edu.hdu.infosys.service.news.INewsService;

/**
 *
 *
 *
 * @author Pi Chen
 * @version infosys V1.0.0, 2016年12月13日
 * @see
 * @since infosys V1.0.0
 */
@RestController
@RequestMapping(value = "/index")
public class IndexController
{

    @Autowired
    private INewsService newsService;

    /**
     *
     * 总记录数
     *
     * @return
     */
    @RequestMapping(value = "/data", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public ResponseEntity<Map<String, Object>> getYearCount()
    {
        Map<String, Object> data = new HashMap<String, Object>();
        Calendar calendar = new GregorianCalendar();

        long totalCount = 0;
        for (int i = 2000; i <= calendar.get(Calendar.YEAR); i++)
        {
            totalCount += newsService.getYearCount(i);
        }
        data.put("totalCount", totalCount);
        return new ResponseEntity<Map<String, Object>>(data, HttpStatus.OK);
    }

}
