package com.sxkj.uc.service;

import com.sxkj.common.base.BaseService;
import com.sxkj.uc.entity.App;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional(readOnly = true, rollbackFor = Exception.class)
@Service
public class AppService extends BaseService<App> {

}
