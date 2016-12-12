package cn.edu.hdu.infosys.dao;

import java.util.List;
import java.util.Map;

import cn.edu.hdu.infosys.model.User;

/**
 * @author Pi Chen
 * @version infosys V1.0.0, 2016年8月12日
 * @see
 * @since infosys V1.0.0
 */

public interface IUserDao
{
    public List<User> query(Map<String, Object> param);

    public int saveUser(Map<String, Object> param);

    public void deleteUser(Map<String, Object> param);

    public int updateUser(User user);
}
