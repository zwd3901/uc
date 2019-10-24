package com.sxkj.uc.dao;

import com.sxkj.uc.dao.base.BaseDao;
import com.sxkj.uc.entity.UserApp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zwd
 *
 */
@Repository
@Slf4j
public class UserAppDao extends BaseDao<UserApp> {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void batchInsertUserApp(List<UserApp> userAppList) {
        try {
            String[] sqls = new String[userAppList.size()];
            log.info("batch insert is : {}", sqls);
            jdbcTemplate.batchUpdate(sqls);
        } catch (Exception e) {
            log.error(e.getMessage(),e.getCause());
        }
    }
}
