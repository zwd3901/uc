package com.sxkj.uc.api;

import com.sxkj.common.base.BaseController;
import com.sxkj.common.response.MyResponse;
import com.sxkj.common.response.MyResponseUtil;
import com.sxkj.uc.entity.User;
import com.sxkj.uc.service.UserService;
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
     *
     * @param user
     * @return
     */
    @Override
    @PostMapping("/create")
    public MyResponse create(@RequestBody User user) {
        return MyResponseUtil.success(userService.insert(user));
    }

    /**
     * 编辑用户信息，不包括登录名和登录密码
     *
     * @param user
     * @return
     */
    @Override
    @PutMapping("/edit")
    public MyResponse edit(@RequestBody User user) {
        return MyResponseUtil.success(userService.updateByPrimaryKey(user));
    }

    /**
     * 获取单个用户信息
     *
     * @param user
     * @return
     */
    @Override
    @GetMapping("/find")
    public MyResponse find(@RequestBody User user) {
        try {
            return MyResponseUtil.success(userService.findByPrimaryKey(user));
        } catch (Exception e) {
            e.printStackTrace();
            return MyResponseUtil.fail();
        }


    }

    // todo shiro test
    @GetMapping("/find2/{id}")
    public MyResponse find2(@PathVariable String id) {
        try {
            return MyResponseUtil.success(id);
        } catch (Exception e) {
            e.printStackTrace();
            return MyResponseUtil.fail();
        }


    }

    @GetMapping("/find3/{id}")
    public MyResponse find3(@PathVariable String id) {
        try {
            return MyResponseUtil.success(id);
        } catch (Exception e) {
            e.printStackTrace();
            return MyResponseUtil.fail();
        }


    }

    /**
     * 根据条件获取用户信息
     *
     * @param user
     * @return
     */
    @Override
    @GetMapping("/find/list")
    public MyResponse findList(@RequestBody User user) {
        return MyResponseUtil.success(userService.findList(user));
    }

    /**
     * 移除某个用户
     *
     * @param user
     * @return
     */
    @Override
    public MyResponse remove(@RequestBody User user) {
        return MyResponseUtil.success();
    }

    /**
     * 修改密码
     *
     * @param user
     * @return
     */
    public MyResponse editPassword(@RequestBody User user) {
        return MyResponseUtil.success(userService.updatePassword(user));
    }

    // todo shiro test
    @GetMapping("/find/all")
    public MyResponse findList() {
        return MyResponseUtil.success(userService.findList(new User()));
    }
}
