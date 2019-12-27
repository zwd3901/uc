package com.sxkj.uc.api;

import com.sxkj.common.response.MyResponse;
import com.sxkj.common.response.MyResponseUtil;
import com.sxkj.common.util.AppContext;
import com.sxkj.uc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @ClassName SlaveController
 * @Description: TODO 外部应用获取用户登录名和密码
 * @Author zwd
 * @Date 2019/12/25 0025
 **/
@RestController
@RequestMapping("/api/slave")
public class SlaveController {
    @Autowired
    private UserService userService;

    /**
     * @return
     */
    @GetMapping("/user")
    public MyResponse findSlaveUser() {

        String token = AppContext.getToken();
        String secret = AppContext.getSecretKey();
        List<Map<String, Object>> list = userService.findSlaveUser(token, secret);
        if (list == null || list.size() == 0) {
            return MyResponseUtil.fail("无此用户");
        } else {
            return MyResponseUtil.success(list.get(0));
        }
    }
}
