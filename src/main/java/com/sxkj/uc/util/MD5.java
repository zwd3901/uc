package com.sxkj.uc.util;

import java.security.MessageDigest;

public class MD5 {
    /**
     * 获取md5加密后的字符串
     * @param s 待加密的字符串
     * @return
     */
    public static final String getMD5(String s) {
        // 16进制数组
        char[] hexDigits = {'s','x','k','j','2','0','0','8','w','a','n','g','f','a','i','j'};
        try {
            char[] str;
            // 将传入的字符串转换成数组
            byte[] strTemp = s.getBytes("utf-8");
            // 获得md5加密对象
            MessageDigest mDigest = MessageDigest.getInstance("MD5");
            // 传入需要加密的数组
            mDigest.update(strTemp);
            // 获取加密后的数组
            byte[] md = mDigest.digest();
            int j = md.length;
            str = new char[j * 2];
            int k = 0;
            // 将数组位移
            for (int i = 0; i < j; i++) {
                byte b = md[i];
                str[k++] = hexDigits[b >>> 4 & 0xf];
                str[k++] = hexDigits[b & 0xf];
            }
            // 将处理后的字符串返回
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        String s = "123";
        System.err.println(getMD5(s));
        System.err.println(getMD5("1234"));
        System.err.println(getMD5("12345"));
    }
}
