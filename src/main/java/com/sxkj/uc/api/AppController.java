package com.sxkj.uc.api;

import com.google.gson.Gson;
import com.sxkj.uc.api.base.BaseController;
import com.sxkj.uc.entity.App;
import com.sxkj.uc.service.AppService;
import com.sxkj.uc.util.CustomResult;
import com.sxkj.uc.util.CustomResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/app")
@Slf4j
public class AppController implements BaseController<App> {
    @Autowired
    private AppService appService;

    @Override
    @PostMapping("/add")
    public CustomResult create(@RequestBody App app) {
        app = appService.create(app);
        log.info("add data: {}",new Gson().toJson(app));
        return CustomResultUtil.success(app);
    }
}
