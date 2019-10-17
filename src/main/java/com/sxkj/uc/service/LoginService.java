package com.sxkj.uc.service;

import com.sxkj.uc.dao.UserDao;
import com.sxkj.uc.entity.User;
import com.sxkj.uc.util.CustomResult;
import com.sxkj.uc.util.MD5;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Slf4j
@Service
public class LoginService {
    @Autowired
    private UserDao userDao;

    public User findUserByLoginName(String loginName){
        Map<String, Object> map = userDao.findUserByLoginName(loginName);
        if(map != null && !map.isEmpty()){
            User user = new User();
            user.setLoginName(map.get("login_name").toString());
            user.setLoginPassword(map.get("login_password").toString());
            user.setId(map.get("id").toString());
            return user;
        }
        return null;
    }

    public User signIn(String loginName,String loginPassword) {
        User user = findUserByLoginName(loginName);
        if (user == null) {
            return null;
        }else{
            String password = MD5.getMD5(loginPassword);
            if(password.equals(user.getLoginPassword())){
                return user;
            }else{
                return null;
            }
        }
    }

}
