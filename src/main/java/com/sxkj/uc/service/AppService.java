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
public class AppService extends BaseService<App> {
    @Autowired
    private AppDao appDao;

}
