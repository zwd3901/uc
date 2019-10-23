package com.sxkj.uc.service;

import com.sxkj.uc.dao.AppDao;
import com.sxkj.uc.entity.App;
import com.sxkj.uc.service.base.BaseService;
import com.sxkj.uc.util.UUIDGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Slf4j
@Transactional(readOnly = true, rollbackFor = Exception.class)
@Service
public class AppService implements BaseService<App> {
    @Autowired
    private AppDao appDao;

    @Transactional(readOnly = false,rollbackFor = Exception.class)
    @Override
    public App insert(App app) {
        if (app.getId() == null || "".equals(app.getId())) {
            app.setId(UUIDGenerator.generator());
        }
        try {
            appDao.insert(app);
            return app;
        } catch (Exception e) {
            log.error(e.getMessage(),e.getCause());
            return null;
        }

    }
    @Transactional(readOnly = false,rollbackFor = Exception.class)
    @Override
    public App updateByPrimaryKey(App app) {
        try {
            appDao.updateByPrimaryKey(app);
            return app;
        } catch (Exception e) {
            log.error(e.getMessage(),e.getCause());
            return null;
        }

    }

    @Override
    public Map<String, Object> findByPrimaryKey(App app) {
        return appDao.findByPrimaryKey(app);
    }

    @Override
    public List<Map<String, Object>> findList(App app) {
        return appDao.findList(app);
    }

    @Transactional(readOnly = false,rollbackFor = Exception.class)
    @Override
    public App deleteByPrimaryKey(App app) {
        try {
            appDao.deleteByPrimaryKey(app);
            return app;
        } catch (Exception e) {
            log.error(e.getMessage(),e.getCause());
            return null;
        }

    }
}
