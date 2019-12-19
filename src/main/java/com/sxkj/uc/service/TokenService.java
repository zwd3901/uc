package com.sxkj.uc.service;

import com.sxkj.uc.dao.TokenDao;
import com.sxkj.uc.dao.UserDao;
import com.sxkj.uc.entity.Token;
import com.sxkj.uc.entity.User;
import com.sxkj.uc.service.base.BaseService;
import com.sxkj.uc.util.RandomString;
import com.sxkj.uc.util.UUIDGenerator;
import com.sxkj.uc.util.code.DataStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author zwd
 */
@Service
@Slf4j
public class TokenService extends BaseService<Token> {
    /**
     * 过期时长：12小时的毫秒数
     */
    private final static long EXPIRE = 43200000;

    @Autowired
    private TokenDao tokenDao;
    @Autowired
    private UserDao userDao;

    /**
     * 删除token记录
     *
     * @param userId
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteByUserId(String userId) {
        Token sysToken = new Token();
        sysToken.setUserId(userId);
        List<Map<String, Object>> list = tokenDao.findList(sysToken);
        if (list != null && list.size() > 0) {
            for (Map<String, Object> map : list) {
                Token tmp = new Token();
                tmp.setId(map.get("id").toString());
                tokenDao.deleteByPrimaryKey(tmp);
            }
        }

    }

    /**
     * 创建token
     *
     * @param userId
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public String createToken(String userId) {
        // 2、删除token
        deleteByUserId(userId);
        // 3、生成新的token
        String token = RandomString.getStr() + UUIDGenerator.generator() + System.nanoTime();

        Token sysToken = new Token();
        sysToken.setUserId(userId);
        sysToken.setToken(token);
        sysToken.setExpireTime(System.currentTimeMillis() + EXPIRE);
        // 3、保存token
        tokenDao.insert(sysToken);
        // 4、返回字符串
        return token;
    }

    /**
     * 是否过期
     *
     * @param token
     * @return true：过期，false：未过期
     */
    public boolean isExpire(String token) {
        Token sysToken = findByToken(token);
        int duration = expireDuration(sysToken);

        return duration <= 0 ? true : false;
    }

    /**
     * 更新token过期时间
     *
     * @param sysToken
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public String updateToken(Token sysToken) {
        log.info("更新token。。。。。。。。。。。。。。。。。。。。。");
        sysToken.setExpireTime(System.currentTimeMillis() + EXPIRE);
        sysToken = tokenDao.updateByPrimaryKey(sysToken);
        return sysToken.getToken();
    }

    /**
     * token过期时长
     *
     * @param sysToken
     * @return 当前时间与过期时间的时间差（秒）,大于0：token未过期，小等于0：token已过期
     */
    public int expireDuration(Token sysToken) {
        return (int) ((sysToken.getExpireTime() - System.currentTimeMillis()) / 1000);
    }

    /**
     * 根据token查找用户
     *
     * @param token
     * @return
     */
    public User findUserByToken(String token) {
        Token sysToken = new Token();
        sysToken.setToken(token);
        sysToken.setStatus(DataStatusEnum.USABLE.getCode());
        List<Map<String, Object>> list = tokenDao.findList(sysToken);
        if (list != null && list.size() > 0) {
            User user = new User();
            user.setId(list.get(0).get("userId").toString());
            user = userDao.findByPrimaryKey(user);
            return user;
        }
        return null;
    }


    /**
     * 根据userId获取sysToken
     *
     * @param userId
     * @return
     */
    public Token findByUserId(String userId) {
        Token sysToken = new Token();
        sysToken.setUserId(userId);
        sysToken.setStatus(DataStatusEnum.USABLE.getCode());
        List<Map<String, Object>> list = tokenDao.findList(sysToken);
        if (list == null || list.size() == 0) {
            return null;
        }
        sysToken = new Token();
        sysToken.setId(list.get(0).get("id").toString());

        return tokenDao.findByPrimaryKey(sysToken);
    }

    /**
     * 根据token获取sysToken
     *
     * @param token
     * @return
     */
    public Token findByToken(String token) {
        Token sysToken = new Token();
        sysToken.setToken(token);
        List<Map<String, Object>> list = tokenDao.findList(sysToken);
        if (list == null || list.size() == 0) {
            return null;
        }
        sysToken = new Token();
        sysToken.setId(list.get(0).get("id").toString());
        return tokenDao.findByPrimaryKey(sysToken);
    }

    public boolean tokenCheck(Token sysToken, String token) throws Exception {
        if (sysToken == null || token == null || "".equals(token)) {
            log.info("token检查，没有token");
            throw new IncorrectCredentialsException();
        }
        // 判断token是否过期
        long now = System.currentTimeMillis();
        long expireTime = sysToken.getExpireTime();
        if (expireTime - now <= 0) {
            log.info("token已过期");
            throw new ExpiredCredentialsException();
        }
        return true;
    }
}
