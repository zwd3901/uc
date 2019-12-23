package com.sxkj.uc.service;

import com.sxkj.common.base.BaseService;
import com.sxkj.common.util.RandomString;
import com.sxkj.common.util.UUIDGenerator;
import com.sxkj.common.util.code.DataStatusEnum;
import com.sxkj.uc.dao.OnLineDao;
import com.sxkj.uc.dao.UserDao;
import com.sxkj.uc.entity.OnLine;
import com.sxkj.uc.entity.User;
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
public class AccessTokenService extends BaseService<OnLine> {
    /**
     * 过期时长：12小时的毫秒数
     */
    private final static long EXPIRE = 43200000;

    @Autowired
    private OnLineDao onLineDao;
    @Autowired
    private UserDao userDao;

    /**
     * 删除token记录
     *
     * @param userId
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteByUserId(String userId) {
        OnLine onLine = new OnLine();
        onLine.setUserId(userId);
        List<Map<String, Object>> list = onLineDao.findList(onLine);
        if (list != null && list.size() > 0) {
            for (Map<String, Object> map : list) {
                OnLine tmp = new OnLine();
                tmp.setId(map.get("id").toString());
                onLineDao.deleteByPrimaryKey(tmp);
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

        OnLine onLine = new OnLine();
        onLine.setUserId(userId);
        onLine.setToken(token);
        onLine.setExpireTime(System.currentTimeMillis() + EXPIRE);
        // 3、保存token
        onLineDao.insert(onLine);
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
        OnLine onLine = findByToken(token);
        int duration = expireDuration(onLine);

        return duration <= 0 ? true : false;
    }

    /**
     * 更新token过期时间
     *
     * @param onLine
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public String updateToken(OnLine onLine) {
        log.info("更新token。。。。。。。。。。。。。。。。。。。。。");
        onLine.setExpireTime(System.currentTimeMillis() + EXPIRE);
        onLine = onLineDao.updateByPrimaryKey(onLine);
        return onLine.getToken();
    }

    /**
     * token过期时长
     *
     * @param onLine
     * @return 当前时间与过期时间的时间差（秒）,大于0：token未过期，小等于0：token已过期
     */
    public int expireDuration(OnLine onLine) {
        return (int) ((onLine.getExpireTime() - System.currentTimeMillis()) / 1000);
    }

    /**
     * 根据token查找用户
     *
     * @param token
     * @return
     */
    public User findUserByToken(String token) {
        OnLine onLine = new OnLine();
        onLine.setToken(token);
        onLine.setStatus(DataStatusEnum.USABLE.getCode());
        List<Map<String, Object>> list = onLineDao.findList(onLine);
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
    public OnLine findByUserId(String userId) {
        OnLine onLine = new OnLine();
        onLine.setUserId(userId);
        onLine.setStatus(DataStatusEnum.USABLE.getCode());
        List<Map<String, Object>> list = onLineDao.findList(onLine);
        if (list == null || list.size() == 0) {
            return null;
        }
        onLine = new OnLine();
        onLine.setId(list.get(0).get("id").toString());

        return onLineDao.findByPrimaryKey(onLine);
    }

    /**
     * 根据token获取sysToken
     *
     * @param token
     * @return
     */
    public OnLine findByToken(String token) {
        OnLine onLine = new OnLine();
        onLine.setToken(token);
        List<Map<String, Object>> list = onLineDao.findList(onLine);
        if (list == null || list.size() == 0) {
            return null;
        }
        onLine = new OnLine();
        onLine.setId(list.get(0).get("id").toString());
        return onLineDao.findByPrimaryKey(onLine);
    }

    public boolean tokenCheck(OnLine onLine, String token) throws Exception {
        if (onLine == null || token == null || "".equals(token)) {
            log.info("token检查，没有token");
            throw new IncorrectCredentialsException();
        }
        // 判断token是否过期
        long now = System.currentTimeMillis();
        long expireTime = onLine.getExpireTime();
        if (expireTime - now <= 0) {
            log.info("token已过期");
            throw new ExpiredCredentialsException();
        }
        return true;
    }
}
