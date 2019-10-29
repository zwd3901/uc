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
     * 用户使用登录名和登录密码登录
     * @param loginName
     * @param loginPassword
     * @return
     */
    public User signIn(String loginName,String loginPassword) {
        // 1、从t_user表根据登录名查找用户
        User user = userDao.findByLonginName(loginName);
        List<Map<String, Object>> userAppList = new ArrayList<>(16) ;
        List<App> appList = new ArrayList<>(16);
        // 2、如果t_user表中没有，在t_user_app中查找
        if (user == null) {
            UserApp userApp = new UserApp();
            userApp.setLoginName(loginName);
            userApp.setLoginPassword(loginPassword);
            userAppList = userAppDao.findList(userApp);
            // 3、t_user_app表中有记录
            if (userAppList != null && !userAppList.isEmpty()) {
                user = new User();
                user.setId(userAppList.get(0).get("userId").toString());
                user = userDao.findByPrimaryKey(user);
            }
        }else{
            UserApp userApp = new UserApp();
            userApp.setUserId(user.getId());
            userAppList = userAppDao.findList(userApp);
        }
        // 4、系统中有关于登录名的记录，查询允许访问的应用并添加到user中
        if (user != null && userAppList!=null &&!userAppList.isEmpty()) {
            for(Map<String,Object> map : userAppList){
                App app = new App();
                app.setId(map.get("appId").toString());
                app = appDao.findByPrimaryKey(app);
                appList.add(app);
            }
            user.setAppList(appList);
        }
        return user;
    }

}
