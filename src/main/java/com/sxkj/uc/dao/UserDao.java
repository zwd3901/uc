package com.sxkj.uc.dao;

import com.sxkj.uc.entity.User;
import com.sxkj.uc.util.MD5;
import com.sxkj.uc.util.UUIDGenerator;
import com.sxkj.uc.util.sql.SqlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
    public String create(User user) {
        String id = UUIDGenerator.generator();
        user.setId(id);
        user.setLoginPassword(MD5.getMD5(user.getLoginName()+user.getLoginPassword()));
        try {
            String sql = new SqlUtil().insert(user);
            jdbcTemplate.update(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }


//        String sql = "insert into t_user values('"
//                + id + "','"
//                + user.getLoginName() + "','"
//                + MD5.getMD5(user.getLoginName() + user.getLoginPassword()) + "')";
//        jdbcTemplate.execute(sql);
        return id;
    }

    public User edit(User user) {
        String id = user.getId();
        if (id != null && !"".equals(id)) {

        }
        return null;
    }
    /**
     * query and get a user
     * @param id
     * @return
     */
    public Map<String, Object> findUser(Integer id) {
        String sql = "select id,login_name,login_password from t_user where id="+id;
        return jdbcTemplate.queryForMap(sql);
    }

    public Map<String, Object> findUserByLoginName(String loginName) {
        String sql = "select id,login_name,login_password from t_user where login_name='"+loginName+"'";
        return jdbcTemplate.queryForMap(sql);
    }
}
