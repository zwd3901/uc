package com.sxkj.uc.service;

import com.sxkj.uc.dao.AppDao;
import com.sxkj.uc.dao.UserAppDao;
import com.sxkj.uc.dao.UserDao;
import com.sxkj.uc.entity.App;
import com.sxkj.uc.entity.User;
import com.sxkj.uc.entity.UserApp;
import com.sxkj.uc.service.base.BaseService;
import com.sxkj.uc.util.MD5;
import com.sxkj.uc.util.UUIDGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class UserService extends BaseService<User> {
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserAppDao userAppDao;
    @Autowired
    private AppDao appDao;

    /**
     * 创建用户，处理登录密码
     * @param user
     * @return
     */
    @Override
    public User insert(User user) {
        user.setLoginPassword(MD5.getMD5(user.getLoginName()+user.getLoginPassword()));
        try {
            return userDao.insert(user);
        } catch (Exception e) {
            log.error(e.getMessage(),e.getCause());
        }
        return null;
    }

    /**
     * 编辑用户信息，保留原有的登录名和登录密码
     * @param user
     * @return
     */
    @Override
    public User updateByPrimaryKey(User user) {
        String userId = user.getId();
        User condition = new User();
        condition.setId(userId);
        User old = userDao.findByPrimaryKey(condition);
        user.setLoginName(old.getLoginName());
        user.setLoginPassword(old.getLoginPassword());
        return userDao.updateByPrimaryKey(user);
    }

    /**
     * 修改登录密码
     * @param user
     * @return
     */
    public User updatePassword(User user) {
        String userId = user.getId();
        User old = new User();
        old.setId(userId);
        old = userDao.findByPrimaryKey(old);
        old.setLoginPassword(MD5.getMD5(old.getLoginName()+user.getLoginPassword()));
        return userDao.updateByPrimaryKey(old);
    }

    /**
     * 获取用户可访问应用列表
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
}
