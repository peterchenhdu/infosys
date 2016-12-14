package cn.edu.hdu.infosys.controller.news;

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
 * @author    Pi Chen
 * @version   infosys V1.0.0, 2016年12月13日
 * @see
 * @since     infosys V1.0.0
 */
@RestController
@RequestMapping(value = "/news")
public class newsStatController
{

    @Autowired
    private INewsService newsService;
    /**
     *
     * 当前系统新闻每年总记录数
     *
     * @return
     */
    @RequestMapping(value = "/yearcount", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public ResponseEntity<Map<String, Object>> getYearCount(
        @RequestParam(value = "from", defaultValue = "2000") int from,
        @RequestParam(value = "to", defaultValue = "2016") int to)
    {
        Map<String, Object> data = new HashMap<String, Object>();

        for(int i = from; i<=to; i++){
            data.put(i+"", newsService.getYearCount(i));
        }


        return new ResponseEntity<Map<String, Object>>(data, HttpStatus.OK);
    }

}
