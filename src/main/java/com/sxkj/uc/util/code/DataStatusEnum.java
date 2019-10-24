package com.sxkj.uc.util.code;

public enum DataStatusEnum {
    USABLE(1),DISABLE(0);

    private int code;

    DataStatusEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
