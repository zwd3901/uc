package com.sxkj.uc.entity;



import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="t_user")
@Data
public class User implements Serializable {
    @Id
    @Column(name = "id", unique = true, nullable = false,length = 30)
    private String id;

    @Column(name="login_name",unique = true)
    private String loginName;

    @Column(name="login_password")
    private String loginPassword;


}
