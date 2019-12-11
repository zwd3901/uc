package com.sxkj.uc.dao;

import com.sxkj.uc.dao.base.BaseDao;
import com.sxkj.uc.entity.User;
import com.sxkj.uc.util.code.DataStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Slf4j
public class UserDao extends BaseDao<User> {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public User findByLonginName(String loginName) {
        User user = new User();
        user.setUsername(loginName);
        user.setStatus(DataStatusEnum.USABLE.getCode());
        List<Map<String, Object>> list = findList(user);

        if (list != null && !list.isEmpty() && list.size() == 1) {
            try {
                user = new User();
                BeanUtils.populate(user, list.get(0));
                return user;
            } catch (Exception e) {
                log.error(e.getMessage(),e.getCause());
            }
        }
        return null;
    }


}
