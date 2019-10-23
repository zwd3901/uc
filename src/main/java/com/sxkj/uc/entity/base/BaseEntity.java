package com.sxkj.uc.entity.base;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author zwd
 * 所有entity的基类，所有继承本类的entity类必须添加Table注解，并填写表名
 * 所有表中的需要代码管理的字段必须添加Column注解
 */
@Data
public class BaseEntity implements Serializable {
    @Id
    private String id;
    /** 备注 */
    @Column(name = "remark")
    private String remark = "";
    /** 数据状态 */
    @Column(name = "status")
    private int status = 1;
    /** 数据创建人 */
    @Column(name="create_id", updatable = false)
    private String createId = "";
    /** 数据最后修改人 */
    @Column(name = "last_update_id")
    private String updateId = "";

}
