package com.sxkj.uc.service.base;

import com.sxkj.uc.entity.base.BaseEntity;

import java.util.List;
import java.util.Map;

/**
 * @author zwd
 * @param <T>
 */
public interface BaseService<T extends BaseEntity> {
    /**
     * insert data
     * @param t
     * @return
     */
    T insert(T t);

    /**
     * update data by primary key
     * @param t
     * @return
     */
    T updateByPrimaryKey(T t);

    /**
     * select data by primary key
     * @param t
     * @return
     */
    Map<String, Object> findByPrimaryKey(T t);

    List<Map<String, Object>> findList(T t);

    /**
     * delete data by primary key
     * // todo login remove
     * @param t
     * @return
     */
    T deleteByPrimaryKey(T t);

}
