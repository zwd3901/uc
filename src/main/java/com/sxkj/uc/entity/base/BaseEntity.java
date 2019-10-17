package com.sxkj.uc.entity.base;

import com.sun.xml.internal.ws.developer.Serialization;

import java.io.Serializable;

/**
 * @author zwd
 * 所有entity的基类，所有继承本类的entity类必须添加Table注解，并填写表名
 * 所有表中的需要代码管理的字段必须添加Column注解
 */
public class BaseEntity implements Serializable {
}
