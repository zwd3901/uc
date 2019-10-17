package com.sxkj.uc.api;

import com.sxkj.uc.entity.User;
import com.sxkj.uc.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zwd
 */
@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @PutMapping("/edit")
    public Map<String,Object> editUser(@RequestBody User user){
        Map<String,Object> map = new HashMap<>(2);
        try {
            String id = userService.createUser(user);
            map.put("id",id);
            map.put("msg","success");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg",e.getMessage());
            log.error(e.getMessage(),e.getCause());
        }
        return map;
    }
}
