package com.sxkj.uc.api;

import com.google.gson.Gson;
import com.sxkj.uc.api.base.BaseController;
import com.sxkj.uc.entity.App;
import com.sxkj.uc.service.AppService;
import com.sxkj.uc.util.CustomResult;
import com.sxkj.uc.util.CustomResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author zwd
 * 应用管理
 */
@Api(description = "管理接入的应用，完善中")
@RestController
@RequestMapping("/api/app")
@Slf4j
public class AppController implements BaseController<App> {
    @Autowired
    private AppService appService;

    /**
     * 创建应用
     *
     * @param app
     * @return
     */
    @ApiOperation(value = "新增", notes = "新增接入应用")
    @ApiImplicitParam(name = "app", value = "对象实例", dataType = "json")
    @Override
    @PostMapping("/create")
    public CustomResult create(@RequestBody App app) {
        app = appService.insert(app);
        log.info("add data: {}", new Gson().toJson(app));
        return CustomResultUtil.success(app);
    }

    /**
     * 编辑应用信息
     *
     * @param app
     * @return
     */
    @ApiOperation(value = "编辑", notes = "编辑接入的应用信息")
    @Override
    @PutMapping("/edit")
    public CustomResult edit(@RequestBody App app) {
        app = appService.updateByPrimaryKey(app);
        log.info("eidt data : {}", app);
        return CustomResultUtil.success(app);
    }

    /**
     * 查找单个应用
     *
     * @param app
     * @return
     */
    @ApiOperation(value = "查找")
    @Override
    @GetMapping("/find")
    public CustomResult find(@RequestBody App app) {
        App a = appService.findByPrimaryKey(app);
        System.err.println(a);
        return CustomResultUtil.success(appService.findByPrimaryKey(app));
    }

    @Override
    @GetMapping("/list")
    public CustomResult findList(@RequestBody App app) {
        List<Map<String, Object>> list = appService.findList(app);
        log.error(new Gson().toJson(list));
        return CustomResultUtil.success(appService.findList(app));
    }

    /**
     * 移除应用
     *
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
