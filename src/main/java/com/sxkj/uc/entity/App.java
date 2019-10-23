package com.sxkj.uc.entity;

import com.sxkj.uc.entity.base.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author zwd
 * 系统接管的应用
 * // todo 属性待完善
 */
@Table(name = "t_app")
@Data
public class App extends BaseEntity {

    @Column(name = "name",unique = true)
    private String name;
    @Column(name = "url",unique = true)
    private String url;
    @Column(name = "cn_name")
    private String cnName;
}
