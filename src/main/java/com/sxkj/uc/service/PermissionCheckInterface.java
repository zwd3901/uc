package com.sxkj.uc.service;

/**
 * @author zwd
 * 权限检查
 */
public interface PermissionCheckInterface {
    /**
     * 检查权限
     * @param permitValue 权限标识
     * @return 有：true，无：false
     */
    boolean hasPermit(String userId,String permitValue);
}
