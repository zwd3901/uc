package com.sxkj.uc.shiro;

import com.google.gson.Gson;
import com.sxkj.uc.util.RandomString;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName MyToken
 * @Description: 自定义token
 * @Author zwd
 * @Date 2019/12/13 0013
 **/
@Slf4j
public class MyToken {
    /**
     * 过期时长 毫秒
     */
    public static final long EXPIRT = 43200000;
    /**
     * 随机字符串最小长度
     */
    public static final int MIN_LENGTH = 50;
    /**
     * 随机字符串最大长度
     */
    public static final int MAX_LENGTH = 100;
    /**
     * token 数组长度
     */
    public static final int TOKEN_LENGTH = 3;
    /**
     * token 更新提前量 毫秒
     */
    public static final long LEAD = 3000;


    /**
     * 创建token
     *
     * @param userId
     * @return
     */
    public static String create(String userId) {
        String token = RandomString.getStr(MAX_LENGTH, MIN_LENGTH) + System.nanoTime();
        return userId + ":" + token + ":" + (System.currentTimeMillis() + EXPIRT);
    }

    /**
     * 更新token的过期时间
     *
     * @param token
     * @return
     */
    public String fresh(HttpServletResponse httpResponse, String token) {
        String[] ss = parse(token);
        long now = System.currentTimeMillis();
        long expireTime = Long.parseLong(ss[2]);
        if (expireTime < now) {
            httpResponse.setContentType("application/json;charset=utf-8");
            httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
            httpResponse.setCharacterEncoding("UTF-8");
            try {
                Map<String, Object> result = new HashMap<>();
                result.put("status", "400");
                result.put("msg", "登陆失败--onLoginFailure");
                String json = new Gson().toJson(result);
                httpResponse.getWriter().print(json);
            } catch (Exception e) {
                log.error(e.getMessage(), e.getCause());
                return null;
            }
        } else if (expireTime - now < LEAD) {
            return ss[0] + ":" + ss[1] + ":" + (System.currentTimeMillis() + EXPIRT);
        }
        return token;
    }

    /**
     * 解析token
     *
     * @param token
     * @return 0:user id,1:token string,2:expire time
     */
    public static String[] parse(String token) {
        if (token == null || "".equals(token)) {
            return null;
        }
        return token.split(":");
    }

    /**
     * 判断token是否过期
     *
     * @param token
     * @return true：过期，false：未过期
     */
    public static boolean isExpire(String token) {
        long now = System.currentTimeMillis();
        String[] ss = parse(token);
        if (ss != null && ss.length == TOKEN_LENGTH) {
            long expireTime = Long.parseLong(ss[2]);
            return expireTime <= now;
        }
        return true;
    }

}
