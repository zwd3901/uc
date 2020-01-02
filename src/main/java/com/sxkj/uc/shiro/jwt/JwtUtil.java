package com.sxkj.uc.shiro.jwt;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * com.sxkj.util.jwt.JwtTokenUtil
 *
 * @author zwd
 * @Description JwtTokenUtil
 * @Date Create in 2018-07-12 0012 15:13
 * @Modified
 */
@Component
@Slf4j
public class JwtUtil implements Serializable {
    /**
     * 密钥
     */
    private static final String SECRET = "ThisIsSecret";
    /**
     * 令牌有效期,秒
     */
    public static final Long EXPIRATION = 1200L;
    /**
     *
     */
    public static final String PARAM_KEY = "token";
    /**
     * 字符串连接符
     */
    public static final String SEPARATION = "::";


    public static String getUserId() {
        try {
            return getValue().split("::")[0];
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    public static String getUserName() {
        try {
            return getValue().split("::")[1];
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    public static String getValue(String key) {
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            String token = request.getHeader(PARAM_KEY);
            if (token == null) {
                token = request.getParameter(PARAM_KEY);
            }
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(key).asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    public static String getValue() {
        return getValue(PARAM_KEY);
    }

    /**
     * 判断令牌是否过期
     *
     * @param token 令牌
     * @return 是否过期
     */
    public static Boolean isTokenExpired(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            Date expiration = jwt.getExpiresAt();
            Boolean flag = !expiration.before(new Date());
            if (flag) {
                // 刷新token
                refreshToken(token);
            } else {
                // todo token 过期
            }
            return flag;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 刷新令牌
     *
     * @param token 原令牌
     * @return 新令牌
     */
    public static String refreshToken(String token) {
        String refreshedToken;
        try {
            DecodedJWT jwt = JWT.decode(token);
            refreshedToken = generateToken(jwt.getClaim(PARAM_KEY).asString());
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    /**
     * 验证令牌
     *
     * @param token    令牌
     * @param userName 用户名
     * @return 是否有效
     */
    public static Boolean validateToken(String token, String userId, String userName) throws UnsupportedEncodingException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
        DecodedJWT jwt = verifier.verify(token);
        String tokenValue = jwt.getClaim(PARAM_KEY).asString();
        String name = tokenValue.split("::")[1];
        String id = tokenValue.split("::")[0];
        if (null == name || "".equals(name) || null == id || "".equals(id)) {
            return false;
        }
        return (name.equals(userName) && (id.equals(userId)) && isTokenExpired(token));
    }

    /**
     * 验证令牌是否过期
     *
     * @param token 令牌
     * @return 是否有效
     */
    public static Boolean validateToken(String token) throws UnsupportedEncodingException {
        DecodedJWT jwt = JWT.decode(token);
        Date expiration = jwt.getExpiresAt();
        Boolean flag = !expiration.before(new Date());
        return flag;
    }

    /**
     * 生成签名
     *
     * @param username 用户名
     * @return 加密的token
     */
    public static String generateToken(String userId, String username) {
        return generateToken(userId + "::" + username);
    }

    private static String generateToken(String token) {
        try {
            Date date = new Date(System.currentTimeMillis() + EXPIRATION * 1000);
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            return JWT.create()
                    .withClaim(PARAM_KEY, token)
                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (Exception e) {
            return null;
        }
    }

    public static void main(String[] args) throws Exception {
        String userName = "123";
        String userId = "12345";
        String token = generateToken(userId, userName);
        System.out.println(token);
        boolean bool = validateToken(token);
        System.err.println(bool);

    }

}
