package com.sxkj.uc.entity;

import com.sxkj.uc.entity.base.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author zwd
 * 在线用户
 */
@Table(name = "t_on_line")
@Data
public class OnLine extends BaseEntity {
    @Id
    private String id;
    @Column(name = "user_id",nullable = false)
    private String userId;
    @Column(name = "user_real_name",nullable = false)
    private String userRealName;
    @Column(name = "token",nullable = false)
    private String token;
}
