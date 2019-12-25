package com.sxkj.uc.entity;

import com.sxkj.common.base.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @author zwd
 * 系统接管的应用
 * // todo 属性待完善
 */
@Table(name = "t_app")
@Data
public class App extends BaseEntity {
    /**
     * 应用的简称，英文或字母
     */
    @Column(name = "name", unique = true, nullable = false)
    private String name;
    /**
     * 应用的中文名称
     */
    @Column(name = "cn_name", nullable = false)
    private String cnName;
    /**
     * 应用访问地址
     */
    @Column(name = "url", unique = true, nullable = false)
    private String url;
    /**
     * 应用特殊标识，请求master时必须携带，用于表明来自哪个应用
     */
    @Column(name = "secret", unique = true, nullable = false)
    private String secret;
}
