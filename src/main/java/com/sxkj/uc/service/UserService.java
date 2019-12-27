package com.sxkj.uc.service;

import com.sxkj.common.base.BaseService;
import com.sxkj.common.util.MD5;
import com.sxkj.uc.dao.AppDao;
import com.sxkj.uc.dao.UserAppDao;
import com.sxkj.uc.dao.UserDao;
import com.sxkj.uc.entity.App;
import com.sxkj.uc.entity.User;
import com.sxkj.uc.entity.UserApp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zwd
 */
@Service
@Slf4j
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class UserService extends BaseService<User> implements UserDetailsService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserAppDao userAppDao;
    @Autowired
    private AppDao appDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 创建用户，处理登录密码
     *
     * @param user
     * @return
     */
    @Override
    public User insert(User user) {
        user.setPassword(MD5.encode2hex(user.getUsername() + user.getPassword()));
        try {
            return userDao.insert(user);
        } catch (Exception e) {
            log.error(e.getMessage(), e.getCause());
        }
        return null;
    }

    /**
     * 编辑用户信息，保留原有的登录名和登录密码
     *
     * @param user
     * @return
     */
    @Override
    public User updateByPrimaryKey(User user) {
        String userId = user.getId();
        User condition = new User();
        condition.setId(userId);
        User old = userDao.findByPrimaryKey(condition);
        user.setUsername(old.getUsername());
        user.setPassword(old.getPassword());
        return userDao.updateByPrimaryKey(user);
    }

    /**
     * 修改登录密码
     *
     * @param user
     * @return
     */
    public User updatePassword(User user) {
        String userId = user.getId();
        User old = new User();
        old.setId(userId);
        old = userDao.findByPrimaryKey(old);
        old.setPassword(MD5.encode2hex(old.getUsername() + user.getPassword()));
        return userDao.updateByPrimaryKey(old);
    }

    /**
     * 获取用户可访问应用列表
     *
     * @param userId
     * @return
     */
    public List<App> findAppList(String userId) {
        List<App> result = new ArrayList<>(16);
        UserApp userApp = new UserApp();
        userApp.setUserId(userId);
        List<Map<String, Object>> list = userAppDao.findList(userApp);
        if (list != null && list.size() > 0) {
            for (Map<String, Object> map : list) {
                if (map != null && map.get("appId") != null && !"".equals(map.get("appId").toString())) {
                    String appId = map.get("appId").toString();
                    App app = new App();
                    app.setId(appId);
                    app = appDao.findByPrimaryKey(app);
                    result.add(app);
                }
            }
        }
        return result;
    }

    public String hasPermit(String username) {
        String permit;
        switch (username) {
            case "sun42":
                permit = "user:all";
                break;
            case "zheng36":
                permit = "user:find";
                break;
            default:
                permit = "";
                break;
        }
        return permit;
    }

    public List<Map<String, Object>> findSlaveUser(String token, String secret) {
        if (token == null || "".equals(token) || secret == null || "".equals(secret)) {
            return null;
        }
        String sql = "select ua.login_name as name,ua.login_password as password from t_app as a,t_on_line as ol,t_user_app as ua " +
                "where a.id=ua.app_id and ol.id=ua.user_id and ol.token=? and a.secret=?";
        return jdbcTemplate.queryForList(sql, token, secret);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDao.findByLonginName(username);
    }
}
