package com.sxkj.uc.service;

import com.sxkj.uc.dao.SysTokenDao;
import com.sxkj.uc.dao.UserDao;
import com.sxkj.uc.entity.SysToken;
import com.sxkj.uc.entity.User;
import com.sxkj.uc.service.base.BaseService;
import com.sxkj.uc.util.UUIDGenerator;
import com.sxkj.uc.util.code.DataStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author zwd
 */
@Transactional(readOnly = true,rollbackFor = Exception.class)
@Service
@Slf4j
public class SysTokenService extends BaseService<SysToken> {
    /** 过期时长：12小时 */
    private final static int EXPIRE = 12;

    @Autowired
    private SysTokenDao sysTokenDao;
    @Autowired
    private UserDao userDao;

    /**
     * 删除token记录
     * @param userId
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteByUserId(String userId) {
        SysToken sysToken = new SysToken();
        sysToken.setUserId(userId);
        List<Map<String, Object>> list = sysTokenDao.findList(sysToken);
        if(list!=null&&list.size()>0){
            for (Map<String, Object> map : list) {
                SysToken tmp = new SysToken();
                tmp.setId(map.get("id").toString());
                sysTokenDao.deleteByPrimaryKey(tmp);
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
        String token = getRandomString() + UUIDGenerator.generator() + System.nanoTime();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expireTime = now.plusHours(EXPIRE);
        SysToken sysToken =  new SysToken();
        sysToken.setUserId(userId);
        sysToken.setToken(token);
        sysToken.setExpireTime(expireTime);
        // 3、保存token
        sysToken = sysTokenDao.insert(sysToken);
        // 4、返回字符串
        return token;
    }

    /**
     * 是否过期
     * @param token
     * @return  true：过期，false：未过期
     */
    public boolean isExpire(String token) {
        SysToken sysToken = findByToken(token);
        int duration = expireDuration(sysToken);

        return duration<=0?true:false;
    }
    /**
     * 更新token过期时间
     * @param sysToken
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public String updateToken(SysToken sysToken) {
        LocalDateTime now = LocalDateTime.now();
        sysToken.setExpireTime(now.plusHours(EXPIRE));
        sysToken = sysTokenDao.updateByPrimaryKey(sysToken);
        return sysToken.getToken();
    }

    /**
     * token过期时长
     * @param sysToken
     * @return  当前时间与过期时间的时间差（秒）,大于0：token未过期，小等于0：token已过期
     */
    public int expireDuration(SysToken sysToken){
        Duration duration = Duration.between(LocalDateTime.now(),sysToken.getExpireTime());
        return (int) (duration.toMillis()/1000);
    }

    /**
     * 根据token查找用户
     * @param token
     * @return
     */
    public User findUserByToken(String token) {
        SysToken sysToken = new SysToken();
        sysToken.setToken(token);
        sysToken.setStatus(DataStatusEnum.USABLE.getCode());
        List<Map<String, Object>> list = sysTokenDao.findList(sysToken);
        if (list != null && list.size() > 0) {
            User user = new User();
            user.setId(list.get(0).get("userId").toString());
            user = userDao.findByPrimaryKey(user);
            return user;
        }
        return null;
    }

    /**
     * 生成随机字符串
     * @return
     */
    private String getRandomString() {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        int length = random.nextInt(51) + 50;
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 根据userId获取sysToken
     * @param userId
     * @return
     */
    public SysToken findByUserId(String userId) {
        SysToken sysToken = new SysToken();
        sysToken.setUserId(userId);
        sysToken.setStatus(DataStatusEnum.USABLE.getCode());
        List<Map<String, Object>> list = sysTokenDao.findList(sysToken);
        if (list == null || list.size() == 0) {
            return null;
        }
        sysToken = new SysToken();
        sysToken.setId(list.get(0).get("id").toString());
        return sysTokenDao.findByPrimaryKey(sysToken);
    }

    /**
     * 根据token获取sysToken
     * @param token
     * @return
     */
    public SysToken findByToken(String token) {
        SysToken sysToken = new SysToken();
        sysToken.setToken(token);
        List<Map<String, Object>> list = sysTokenDao.findList(sysToken);
        if (list == null || list.size() == 0) {
            return null;
        }
        sysToken = new SysToken();
        sysToken.setId(list.get(0).get("id").toString());
        return sysTokenDao.findByPrimaryKey(sysToken);
    }
    public static void main(String[] args) {
        SysTokenService service = new SysTokenService();
//        for (int i = 0; i < 10; i++) {
//            String r = service.getRandomString();
//            System.err.println(r);
//        }
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expireTime = now.plusHours(EXPIRE);
        System.err.println(expireTime.isBefore(now)+"======="+expireTime.isAfter(now));
        Duration duration = Duration.between(now,expireTime);
        System.err.println(duration.toMillis());
        duration = Duration.between(expireTime,now);
        System.err.println(duration.toMillis());
    }


}
