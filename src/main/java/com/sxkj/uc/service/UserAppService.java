package com.sxkj.uc.service;

import com.sxkj.uc.dao.UserAppDao;
import com.sxkj.uc.entity.UserApp;
import com.sxkj.uc.service.base.BaseService;
import com.sxkj.uc.util.UUIDGenerator;
import com.sxkj.uc.util.sql.SqlUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author zwd
 *
 */
@Service
@Slf4j
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class UserAppService extends BaseService<UserApp> {
    @Autowired
    private UserAppDao userAppDao;

    @Transactional(rollbackFor = Exception.class)
    public boolean batchInsert(List<UserApp> userAppList){
        return userAppDao.batchInsert(userAppList);
    }

    public boolean batchEdit(List<UserApp> userAppList){
        String userId = userAppList.get(0).getUserId();
        UserApp temp = new UserApp();
        temp.setUserId(userId);
        if(userAppDao.batchDelete(temp)){
            return userAppDao.batchInsert(userAppList);
        }
        return false;
    }

}
