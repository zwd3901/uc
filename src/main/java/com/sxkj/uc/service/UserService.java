package com.sxkj.uc.service;

import com.sxkj.uc.dao.UserDao;
import com.sxkj.uc.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    @Transactional(readOnly = false,rollbackFor = Exception.class)
    public String createUser(User user){
        return userDao.create(user);
    }

    public Map<String, Object> findUser(Integer id) {
        return userDao.findUser(id);
    }

}
