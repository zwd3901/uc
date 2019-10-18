package com.sxkj.uc.dao.base;

import com.sxkj.uc.entity.base.BaseEntity;
import com.sxkj.uc.util.MD5;
import com.sxkj.uc.util.UUIDGenerator;
import com.sxkj.uc.util.sql.SqlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BaseDao<T extends BaseEntity> {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void create(T t){
        try {
            String sql = new SqlUtil().insert(t);
            jdbcTemplate.update(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
