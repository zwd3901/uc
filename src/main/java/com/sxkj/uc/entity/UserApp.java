package com.sxkj.uc.entity;

import com.sxkj.uc.entity.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author zwd
 * 用户可访问的应用关联
 * // todo 属性待完善
 */
@Table(name = "t_user_app")
public class UserApp extends BaseEntity {
    @Id
    private String id;
    @Column(name = "user_id",nullable = false)
    private String userId;
    @Column(name = "app_id", nullable = false)
    private String appId;
    @Column(name = "login_name",nullable = false)
    private String loginName;
    @Column(name = "login_password",nullable = false)
    private String loginPassword;

}
