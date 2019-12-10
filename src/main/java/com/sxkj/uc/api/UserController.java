package com.sxkj.uc.api;

import com.sxkj.uc.api.base.BaseController;
import com.sxkj.uc.entity.User;
import com.sxkj.uc.service.UserService;
import com.sxkj.uc.util.CustomResult;
import com.sxkj.uc.util.CustomResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zwd
 * 创建、编辑、查找、删除、修改密码
 */
@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController implements BaseController<User> {
    @Autowired
    private UserService userService;

    /**
     * 创建用户
     * @param user
     * @return
     */
    @Override
    @PostMapping("/create")
    public CustomResult create(@RequestBody User user) {
        return CustomResultUtil.success(userService.insert(user));
    }

    /**
     * 编辑用户信息，不包括登录名和登录密码
     * @param user
     * @return
     */
    @Override
    @PutMapping("/edit")
    public CustomResult edit(@RequestBody User user) {
        return CustomResultUtil.success(userService.updateByPrimaryKey(user));
    }

    /**
     * 获取单个用户信息
     * @param user
     * @return
     */
    @Override
    @GetMapping("/find")
    public CustomResult find(@RequestBody User user) {
        return CustomResultUtil.success(userService.findByPrimaryKey(user));
    }

    /**
     * 根据条件获取用户信息
     * @param user
     * @return
     */
    @Override
    @GetMapping("/find/list")
    public CustomResult findList(@RequestBody User user) {
        return CustomResultUtil.success(userService.findList(user));
    }

    /**
     * 移除某个用户
     * @param user
     * @return
     */
    @Override
    public CustomResult remove(@RequestBody User user) {
        return CustomResultUtil.success();
    }

    /**
     * 修改密码
     * @param user
     * @return
     */
    public CustomResult editPassword(@RequestBody User user) {
        return CustomResultUtil.success(userService.updatePassword(user));
    }
}
