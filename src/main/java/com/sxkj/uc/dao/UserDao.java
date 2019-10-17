package com.sxkj.uc.dao;

import com.sxkj.uc.entity.User;
import com.sxkj.uc.util.MD5;
import com.sxkj.uc.util.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class UserDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * add user
     * @param user
     * @throws Exception
     */
    public String create(User user) throws Exception {
        String id = UUIDGenerator.generator();
        String sql = "insert into t_user values('"
                + id + "','"
                + user.getLoginName() + "','"
                + MD5.getMD5(user.getLoginName() + user.getLoginPassword()) + "')";
        jdbcTemplate.execute(sql);
        return id;
    }

    /**
     * query and get a user
     * @param id
     * @return
     */
    public Map<String, Object> findUser(Integer id) throws Exception{
        String sql = "select id,login_name,login_password from t_user where id="+id;
        return jdbcTemplate.queryForMap(sql);
    }

    public Map<String, Object> findUserByLoginName(String loginName) throws Exception{
        String sql = "select id,login_name,login_password from t_user where login_name='"+loginName+"'";
        return jdbcTemplate.queryForMap(sql);
    }
}
