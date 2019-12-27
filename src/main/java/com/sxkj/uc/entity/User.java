package com.sxkj.uc.entity;

import com.sxkj.common.base.BaseEntity;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author zwd
 * 系统接管的用户
 * // todo 属性待完善 spring security 须实现UserDetails接口
 */
@Table(name = "t_user")
@Data
public class User extends BaseEntity implements UserDetails {

    @Column(name = "login_name", unique = true)
    private String username;

    @Column(name = "login_password")
    private String password;

    @Column(name = "real_name", nullable = false)
    private String realName;

    @Column(name = "telephone", nullable = false)
    private String telephone = "";

    @Column(name = "work_place", nullable = false)
    private String workPlace = "";

    /**
     * 重定向地址
     */
    private String redirect = "";
    /**
     * 可访问应用
     */
    private List<App> appList = new ArrayList<>(16);

    /**
     * roles
     *
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
