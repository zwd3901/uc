package com.sxkj.uc.util.code;

public enum CustomResultCodeEnum {
    SUCCESS("0000", "操作成功"), FAIL("0001", "操作失败"),
    SIGN_UP_SUCCESS("0010", "注册成功"), SIGN_UP_FAIL("0011", "注册失败"),
    LOG_IN_SUCCESS("0020", "登录成功"), LOG_IN_FAIL("0021", "登录失败"), LOG_IN_NO("0022", "未登录"),
    LOG_OUT_SUCCESS("0030", "退出成功"), LOG_OUT_FAIL("0031", "退出失败"),
    NO_PERMIT("0040", "缺少权限"),
    TOKEN_EXPIRE("0041", "token过期"),
    NO_TOKEN("0042", "没有token"),
    EXCEPTION("9999", "运行异常");

    private String code;
    private String msg;

    CustomResultCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode(String msg) {
        for (CustomResultCodeEnum result : CustomResultCodeEnum.values()) {
            if (msg.equals(result.msg)) {
                return result.code;
            }
        }
        return null;
    }

    public String getMsg(String code) {
        for (CustomResultCodeEnum result : CustomResultCodeEnum.values()) {
            if (code.equals(result.code)) {
                return result.msg;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
