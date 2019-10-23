package com.sxkj.uc.api;

import com.google.gson.Gson;
import com.sxkj.uc.api.base.BaseController;
import com.sxkj.uc.entity.App;
import com.sxkj.uc.service.AppService;
import com.sxkj.uc.util.CustomResult;
import com.sxkj.uc.util.CustomResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author zwd
 * 应用管理
 */
@RestController
@RequestMapping("/api/app")
@Slf4j
public class AppController implements BaseController<App> {
    @Autowired
    private AppService appService;

    /**
     * 创建应用
     * @param app
     * @return
     */
    @Override
    @PostMapping("/create")
    public CustomResult create(@RequestBody App app) {
        app = appService.insert(app);
        log.info("add data: {}",new Gson().toJson(app));
        return CustomResultUtil.success(app);
    }

    /**
     * 编辑应用信息
     * @param app
     * @return
     */
    @Override
    @PutMapping("/edit")
    public CustomResult edit(@RequestBody App app) {
        app = appService.updateByPrimaryKey(app);
        log.info("eidt data : {}", app);
        return CustomResultUtil.success(app);
    }

    /**
     * 查找单个应用
     * @param app
     * @return
     */
    @Override
    @GetMapping("/find")
    public CustomResult find(@RequestBody App app) {
        //System.err.println(3/0);
        return CustomResultUtil.success(appService.findByPrimaryKey(app));
    }

    @Override
    @GetMapping("/list")
    public CustomResult findList(@RequestBody App app) {
        List<Map<String,Object>> list = appService.findList(app);
        log.error(new Gson().toJson(list));
        return CustomResultUtil.success(appService.findList(app));
    }

    /**
     * 移除应用
     * @param app
     * @return
     */
    @Override
    @DeleteMapping("/remove")
    public CustomResult remove(@RequestBody App app) {
        appService.deleteByPrimaryKey(app);
        return CustomResultUtil.success();
    }
}
