package com.sxkj.uc.api;

import com.sxkj.common.base.BaseController;
import com.sxkj.common.response.MyResponse;
import com.sxkj.common.response.MyResponseUtil;
import com.sxkj.uc.entity.UserApp;
import com.sxkj.uc.service.UserAppService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zwd
 * 用户、应用关联关系表
 */
@RestController
@RequestMapping("/api/user/app")
@Slf4j
public class UserAppController implements BaseController<UserApp> {
    @Autowired
    private UserAppService userAppService;

    /**
     * 创建用户和应用的关联
     *
     * @param userAppList
     * @return
     */
    @PostMapping("/batch/create")
    public MyResponse batchCreate(@RequestBody List<UserApp> userAppList) {
        return MyResponseUtil.success(userAppService.batchInsert(userAppList));
    }

    /**
     * 创建用户和应用的关联关系
     *
     * @param userApp
     * @return
     */
    @Override
    @PostMapping("/create")
    public MyResponse create(@RequestBody UserApp userApp) {
        return MyResponseUtil.success(userAppService.insert(userApp));
    }

    @Override
    public MyResponse edit(UserApp userApp) {
        return null;
    }

    /**
     * 修改用户和应用的关联
     *
     * @param userAppList
     * @return
     */
    @PutMapping("/edit")
    public MyResponse edit(@RequestBody List<UserApp> userAppList) {
        return MyResponseUtil.success(userAppService.batchEdit(userAppList));
    }

    @Override
    public MyResponse find(UserApp userApp) {
        return null;
    }

    @Override
    public MyResponse findList(UserApp userApp) {
        return null;
    }

    @Override
    public MyResponse remove(UserApp userApp) {
        return null;
    }
}
