package com.sxkj.uc.entity;



import com.sxkj.uc.entity.base.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author zwd
 * 系统接管的用户
 * // todo 属性待完善
 */
@Entity
@Table(name="t_user")
@Data
public class User extends BaseEntity {
    @Id
    //@Column(name = "id", unique = true, nullable = false)
    private String id;

    @Column(name="login_name", unique = true)
    private String loginName;

    @Column(name="login_password")
    private String loginPassword;

    @Column(name = "real_name", nullable = false)
    private String realName;

    @Column(name = "telephone", nullable = false)
    private String telephone;

    @Column(name = "work_place", nullable = false)
    private String workPlace;

}
