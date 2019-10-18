package com.sxkj.uc.service;

import com.sxkj.uc.dao.AppDao;
import com.sxkj.uc.entity.App;
import com.sxkj.uc.service.base.BaseService;
import com.sxkj.uc.util.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppService implements BaseService<App> {
    @Autowired
    private AppDao appDao;
    @Override
    public App create(App app) {
        if (app.getId() == null || "".equals(app.getId())) {
            String id = UUIDGenerator.generator();
            app.setId(id);
        }
        appDao.create(app);
        return app;
    }
}
