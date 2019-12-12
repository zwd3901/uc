package com.sxkj.uc.entity;

import com.sxkj.uc.entity.base.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @author zwd
 *
 * 系统生成的token保存到数据表中，发生以下几种情况时，token将被删除
 * 1、用户主动退出系统
 * 2、token检查时发现token过期
 * 3、用户登录系统时发现该userId下有token存在
 */
@Data
@Table(name = "sys_token")
public class Token extends BaseEntity {
    /**
     * 用户id
     */
    @Column(name = "user_id", unique = true, nullable = false)
    private String userId;
    /**
     * token
     */
    @Column(name = "token", nullable = false)
    private String token;
    /**
     * 过期时间
     */
    @Column(name = "expire_time", nullable = false)
    private Long expireTime;

}
