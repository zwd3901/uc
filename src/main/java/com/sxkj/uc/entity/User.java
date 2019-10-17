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
 */
@Entity
@Table(name="t_user")
@Data
public class User extends BaseEntity {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    private String id;

    @Column(name="login_name",unique = true)
    private String loginName;

    @Column(name="login_password")
    private String loginPassword;

    @Column(name = "age")
    private int age;

    @Column(name = "create_time", insertable = false, updatable = false)
    private Date createTime;

}
