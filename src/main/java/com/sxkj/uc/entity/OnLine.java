package com.sxkj.uc.entity;

import com.sxkj.common.base.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @author zwd
 * 在线用户,同时记录用户的token及过期时间
 * token刷新策略：1、即时刷新，用户每次请求都刷新token的过期时间；2、即将过期刷新，系统每次检查token时计算是否即将过期，在某个临界值时刷新
 * token删除：1、执行退出操作；2、token过期；3、登录时表中用相同的user_id存在
 */
@Table(name = "t_on_line")
@Data
public class OnLine extends BaseEntity {
    @Column(name = "user_id", nullable = false)
    private String userId;
    @Column(name = "user_real_name", nullable = false)
    private String userRealName;
    @Column(name = "token", nullable = false)
    private String token;
    /**
     * 过期时间
     */
    @Column(name = "expire_time", nullable = false)
    private Long expireTime;
}
