package com.sxkj.uc.util;

import com.sxkj.uc.util.code.CustomResultCodeEnum;

public class CustomResultUtil {

    /**
     * 操作成功
     * @param data
     * @return
     */
    public static CustomResult success(Object data) {
        return new CustomResult(CustomResultCodeEnum.SUCCESS.getCode(),CustomResultCodeEnum.SUCCESS.getMsg(), data);
    }
    /**
     * 操作成功
     * @param
     * @return
     */
    public static CustomResult success(){
        return success(null);
    }
    /**
     * 操作失败
     * @param
     * @return
     */
    public static CustomResult fail() {
        return new CustomResult(CustomResultCodeEnum.FAIL.getCode(),CustomResultCodeEnum.FAIL.getMsg(),null);
    }

    /**
     * 操作失败
     * @param code 状态码
     * @param msg  说明
     * @return
     */
    public static CustomResult fail(String code, String msg) {
        return new CustomResult(code, msg,null);
    }

    public static CustomResult info(CustomResultCodeEnum resultCodeEnum,Object data) {
        return new CustomResult(resultCodeEnum.getCode(), resultCodeEnum.getMsg(), data);
    }

    public static CustomResult info(CustomResultCodeEnum resultCodeEnum) {
        return new CustomResult(resultCodeEnum.getCode(), resultCodeEnum.getMsg(), null);
    }

}
