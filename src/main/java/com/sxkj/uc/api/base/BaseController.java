package com.sxkj.uc.api.base;

import com.sxkj.uc.entity.base.BaseEntity;
import com.sxkj.uc.util.CustomResult;

/**
 * @author zwd
 */
public interface BaseController<T extends BaseEntity> {
    /**
     * 新增
     * @param t 待新增的对象实例
     * @return
     */
    CustomResult create( T t);

    /**
     * 单表修改数据
     * @param t
     * @return
     */
    CustomResult edit( T t);

    /**
     * 单表根据主键查找单条记录
     * @param t
     * @return
     */
    CustomResult find( T t);

    /**
     * 单表查询
     * @param t
     * @return
     */
    CustomResult findList( T t);

    /**
     * 根据主键移除数据
     * @param t
     * @return
     */
    CustomResult remove( T t);
}
