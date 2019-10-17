package com.sxkj.uc.service;

import com.sxkj.uc.dao.UserDao;
import com.sxkj.uc.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;


    public String createUser(User user) throws Exception{
        return userDao.create(user);
    }

    public Map<String, Object> findUser(Integer id) throws Exception {
        return userDao.findUser(id);
    }

}
