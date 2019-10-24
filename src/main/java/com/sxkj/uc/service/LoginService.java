package com.sxkj.uc.service;

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

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class LoginService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserAppDao userAppDao;


    public User findUserByLoginName(String loginName){
        Map<String, Object> map = userDao.findByLonginName(loginName);
        if (map != null && !map.isEmpty()) {
            User user = new User();
            try {
                BeanUtils.populate(user, map);
            } catch (Exception e) {
                log.error(e.getMessage(),e.getCause());
            }
        }else {
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

    public User signIn(String loginName,String loginPassword) {
        return null;
    }

}
