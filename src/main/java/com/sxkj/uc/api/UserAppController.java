package com.sxkj.uc.api;

import com.sxkj.uc.api.base.BaseController;
import com.sxkj.uc.entity.UserApp;
import com.sxkj.uc.service.UserAppService;
import com.sxkj.uc.util.CustomResult;
import com.sxkj.uc.util.CustomResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zwd
 * 用户、应用关联关系表
 */
@RestController
@RequestMapping("/user/app")
@Slf4j
public class UserAppController implements BaseController<UserApp> {
    @Autowired
    private UserAppService userAppService;

    /**
     * 创建用户和应用的关联
     * @param userAppList
     * @return
     */
    @PostMapping("/batch/create")
    public CustomResult batchCreate(@RequestBody List<UserApp> userAppList) {
        return CustomResultUtil.success(userAppService.batchInsert(userAppList));
    }

    /**
     * 创建用户和应用的关联关系
     * @param userApp
     * @return
     */
    @Override
    @PostMapping("/create")
    public CustomResult create(@RequestBody UserApp userApp) {
        return CustomResultUtil.success(userAppService.insert(userApp));
    }

    @Override
    public CustomResult edit(UserApp userApp) {
        return null;
    }

    /**
     * 修改用户和应用的关联
     * @param userAppList
     * @return
     */
    @PutMapping("/edit")
    public CustomResult edit(@RequestBody List<UserApp> userAppList) {
        return CustomResultUtil.success(userAppService.batchEdit(userAppList));
    }

    @Override
    public CustomResult find(UserApp userApp) {
        return null;
    }

    @Override
    public CustomResult findList(UserApp userApp) {
        return null;
    }

    @Override
    public CustomResult remove(UserApp userApp) {
        return null;
    }
}
