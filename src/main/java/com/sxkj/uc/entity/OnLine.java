package com.sxkj.uc.entity;

import com.sxkj.uc.entity.base.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @author zwd
 * 在线用户
 */
@Table(name = "t_on_line")
@Data
public class OnLine extends BaseEntity {
    @Column(name = "user_id",nullable = false)
    private String userId;
    @Column(name = "user_real_name",nullable = false)
    private String userRealName;
    @Column(name = "token",nullable = false)
    private String token;
}
