package com.sxkj.uc.service;

import com.sxkj.uc.dao.AppDao;
import com.sxkj.uc.dao.UserAppDao;
import com.sxkj.uc.dao.UserDao;
import com.sxkj.uc.entity.App;
import com.sxkj.uc.entity.User;
import com.sxkj.uc.entity.UserApp;
import com.sxkj.uc.util.CustomResult;
import com.sxkj.uc.util.MD5;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class LoginService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private AppDao appDao;
    @Autowired
    private UserAppDao userAppDao;

    /**
     * 根据登录名查找用户
     * @param loginName
     * @return
     */
    public User findUserByLoginName(String loginName){
        User user = userDao.findByLonginName(loginName);
        if (user != null) {
            UserApp userApp = new UserApp();
            userApp.setLoginName(loginName);
            List<Map<String, Object>> list = userAppDao.findList(userApp);
            if (list != null && list.size() == 1) {
                try {
                    BeanUtils.populate(userApp, list.get(0));
                } catch (Exception e) {
                    log.error(e.getMessage(),e.getCause());
                }
            }
        }
        return null;
    }

    /**
     * 用户使用登录名和登录密码登录
     * @param loginName
     * @param loginPassword
     * @return
     */
    public User signIn(String loginName,String loginPassword) {
        User user = findUserByLoginName(loginName);
        List<Map<String, Object>> userAppList ;
        List<App> appList = new ArrayList<>(16);
        if (user == null) {
            UserApp userApp = new UserApp();
            userApp.setLoginName(loginName);
            userApp.setLoginPassword(loginPassword);
            userAppList = userAppDao.findList(userApp);
            if (userAppList != null && !userAppList.isEmpty()) {
                user = new User();
                user.setId(userAppList.get(0).get("userId").toString());
                user = userDao.findByPrimaryKey(user);

                for(Map<String,Object> map : userAppList){
                    App app = new App();
                    app.setId(map.get("appId").toString());
                    app = appDao.findByPrimaryKey(app);
                    appList.add(app);
                }
                user.setAppList(appList);
            }
        }
        return user;
    }

}
