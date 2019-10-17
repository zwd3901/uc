package com.sxkj.uc.util;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zwd
 * @param <T>
 */
@Data
public class CustomResult<T> implements Serializable {
    private static final long serialVersionUID = -4505655308965878999L;

    /** 返回的数据 */
    private T data = null;
    /** 返回状态码 */
    private String code;
    /** 返回消息描述 */
    private String msg;

    public CustomResult(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

}
