package com.sxkj.uc.dao;

import com.sxkj.common.base.BaseDao;
import com.sxkj.common.util.UUIDGenerator;
import com.sxkj.uc.entity.UserApp;
import com.sxkj.uc.util.sql.SqlUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author zwd
 */
@Repository
@Slf4j
public class UserAppDao extends BaseDao<UserApp> {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 批量insert
     *
     * @param userAppList
     * @return
     */
    public boolean batchInsert(List<UserApp> userAppList) {
        try {
            if (userAppList != null) {
                if (userAppList.size() == 1) {
                    insert(userAppList.get(0));
                } else {
                    String[] sqls = new String[userAppList.size()];
                    SqlUtil sqlUtil = new SqlUtil();
                    UserApp userApp;
                    for (int i = 0, len = userAppList.size(); i < len; i++) {
                        userApp = userAppList.get(i);
                        if (userApp.getId() == null || "".equals(userApp.getId())) {
                            userApp.setId(UUIDGenerator.generator());
                        }
                        sqls[i] = sqlUtil.insert(userApp);
                    }
                    log.info("batch insert is :{}", sqls);
                    jdbcTemplate.batchUpdate(sqls);
                }
                return true;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e.getCause());
        }
        return false;
    }

    /**
     * 批量删除
     *
     * @param userApp
     * @return
     */
    public boolean batchDelete(UserApp userApp) {
        try {
            String sql = new SqlUtil().delete(userApp);
            log.info("batch delete is : {}", sql);
            jdbcTemplate.update(sql);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e.getCause());
        }
        return false;
    }

    /**
     * 根据用户id获取可以访问的app
     *
     * @param userId
     * @return
     */
    public List<Map<String, Object>> findAppByUserId(String userId) {
        String sql = "select a.id,a.name,a.cn_name,a.url from t_app as a,t_user_app as u where a.id=u.app_id and u.user_id=?";
        return jdbcTemplate.queryForList(sql, userId);
    }
}
