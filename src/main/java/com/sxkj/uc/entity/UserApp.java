package com.sxkj.uc.entity;

import com.sxkj.common.base.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @author zwd
 * 用户可访问的应用关联
 * // todo 属性待完善
 */
@Table(name = "t_user_app")
@Data
public class UserApp extends BaseEntity {

    @Column(name = "user_id", nullable = false)
    private String userId;
    @Column(name = "app_id", nullable = false)
    private String appId;
    @Column(name = "login_name", nullable = false)
    private String loginName;
    @Column(name = "login_password", nullable = false)
    private String loginPassword;

}
