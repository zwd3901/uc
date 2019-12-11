package com.sxkj.uc.entity;



import com.sxkj.uc.entity.base.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zwd
 * 系统接管的用户
 * // todo 属性待完善
 */
@Table(name="t_user")
@Data
public class User extends BaseEntity {

    @Column(name="login_name", unique = true)
    private String username;

    @Column(name="login_password")
    private String password;

    @Column(name = "real_name", nullable = false)
    private String realName;

    @Column(name = "telephone", nullable = false)
    private String telephone = "";

    @Column(name = "work_place", nullable = false)
    private String workPlace = "";

    /** 重定向地址 */
    private String redirect = "";
    /** 可访问应用 */
    private List<App> appList = new ArrayList<>(16);

}
