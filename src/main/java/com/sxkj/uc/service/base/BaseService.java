package com.sxkj.uc.service.base;

import com.sxkj.uc.dao.base.BaseDao;
import com.sxkj.uc.entity.base.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author zwd
 * @param <T>
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class BaseService<T extends BaseEntity> {
    @Autowired
    private BaseDao<T> baseDao;
    /**
     * insert data
     * @param t
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public T insert(T t){
        return baseDao.insert(t);
    };

    /**
     * update data by primary key
     * @param t
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public T updateByPrimaryKey(T t){
        T tt = baseDao.updateByPrimaryKey(t);
        System.err.println(4/0);
        return tt;
    }

    /**
     * select data by primary key
     * @param t
     * @return
     */
    public Map<String, Object> findMapByPrimaryKey(T t){
        return baseDao.findMapByPrimaryKey(t);
    }

    public T findByPrimaryKey(T t){
        return baseDao.findByPrimaryKey(t);
    }

    public List<Map<String, Object>> findList(T t){
        return baseDao.findList(t);
    }

    /**
     * 物理删除
     * // todo login remove
     * @param t
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteByPrimaryKey(T t){
        baseDao.deleteByPrimaryKey(t);
    }

    /**
     * 逻辑删除
     * @param t
     */
    @Transactional(rollbackFor = Exception.class)
    public void removeByPromaryKey(T t) {
        baseDao.removeByPrimaryKey(t);
    }
}
