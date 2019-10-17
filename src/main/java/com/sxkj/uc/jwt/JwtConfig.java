package com.sxkj.uc.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author zwd
 */
@Component
public class JwtConfig {

    @Value("${config.jwt.secret}")
    private String secret;
    @Value("${config.jwt.expire}")
    private long expire;
    @Value("${config.jwt.header}")
    private String header;

    /**
     * 根据用户id标识生成token
     * @param id
     * @return
     */
    public String createToken(String id) {
        // 当前时间
        long current = System.currentTimeMillis();
        // 过期时间
        long expireDate = current + expire * 1000;
        return Jwts.builder()
                .setSubject(id)
                .setIssuedAt(new Date(current))
                .setExpiration(new Date(expireDate))
                .signWith(SignatureAlgorithm.ES256, secret)
                .compact();

    }

    /**
     * 解析token，获取token中的信息
     * @param token
     * @return
     */
    public Claims getTokenClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 验证token是否过期
     * @param expirationTime
     * @return false:未失效,true:失效了
     */
    public boolean isExpired(Date expirationTime){
        return !expirationTime.before(new Date());
    }

    /**
     * 刷新token，重新生成过期时间
     * @param token
     * @return
     */
    public String refreshToken(String token) {
        Claims claims = getTokenClaims(token);
        // TODO 判断是否过期
        if (isExpired(claims.getExpiration())) {
            // TODO 失效了，需要重新登录进行认证
            throw new RuntimeException("token expire");
        }else {
            return createToken(claims.getId());
        }
    }
}
