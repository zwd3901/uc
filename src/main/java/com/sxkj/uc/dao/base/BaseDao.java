package com.sxkj.uc.dao.base;

import com.sxkj.uc.entity.base.BaseEntity;
import com.sxkj.uc.util.CustomResultCodeEnum;
import com.sxkj.uc.util.UUIDGenerator;
import com.sxkj.uc.util.sql.SqlUtil;
import javafx.beans.property.adapter.ReadOnlyJavaBeanBooleanProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.awt.*;
import java.util.List;
import java.util.Map;

/**
 * @author zwd
 * @param <T>
 * 数据库操作基类
 */
@Repository
@Slf4j
public class BaseDao<T extends BaseEntity> {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * insert
     * @param t
     */
    public T insert(T t){
        try {
            if (t.getId() == null || "".equals(t.getId())) {
                t.setId(UUIDGenerator.generator());
            }
            String sql = new SqlUtil().insert(t);
            log.info("insert data : {}", sql);
            jdbcTemplate.update(sql);
            return t;
        } catch (Exception e) {
            log.error(e.getMessage(),e.getCause());
        }
        return null;
    }

    /**
     * update by primary key
     * @param t
     */
    public T updateByPrimaryKey(T t) {
        try {
            if (t.getId() == null || "".equals(t.getId())) {
                throw new RuntimeException("id is empty ");
            }
            String sql = new SqlUtil().updateByPrimaryKey(t);
            log.info("update  : {}", sql);
            jdbcTemplate.update(sql);
            return t;
        } catch (Exception e) {
            log.error(e.getMessage(),e.getCause());
        }
        return null;
    }

    /**
     * finde single by primary key
     * @param t
     * @return
     */
    public Map<String,Object> findByPrimaryKey(T t) {
        try {
            if (t.getId() == null || "".equals(t.getId())) {
                throw new RuntimeException("id is empty ");
            }
            String sql = new SqlUtil().findByPrimaryKey(t);
            log.info("select data : {}", sql);
            return jdbcTemplate.queryForMap(sql);
        } catch (Exception e) {
            log.error(e.getMessage(),e.getCause());
        }
        return null;
    }

    public List<Map<String, Object>> findList(T t) {
        try {
            String sql = new SqlUtil().findList(t);
            log.info("select data list : {}",sql);
            return jdbcTemplate.queryForList(sql);
        } catch (Exception e) {
            log.error(CustomResultCodeEnum.EXCEPTION.getCode(),e.getCause());
        }
        return null;
    }

    /**
     * 真实删除
     * // todo
     * @param t
     */
    public void deleteByPrimaryKey(T t) {
        try {
            if (t.getId() == null || "".equals(t.getId())) {
                throw new RuntimeException("id is empty ");
            }
            String sql = new SqlUtil().deleteByPrimaryKey(t);
            log.info("remove data : {}", sql);
            jdbcTemplate.update(sql);
        } catch (Exception e) {
            log.error(e.getMessage(),e.getCause());
        }
    }

    /**
     * 逻辑删除
     * @param t
     */
    public void removeByPrimaryKey(T t) {
        try {
            if (t.getId() == null || "".equals(t.getId())) {
                throw new RuntimeException("id is empty ");
            }
            String sql = new SqlUtil().logicRemoveByPrimaryKey(t,"status","0");
            log.info("remove data : {}", sql);
            jdbcTemplate.update(sql);
        } catch (Exception e) {
            log.error(e.getMessage(),e.getCause());
        }
    }
}
