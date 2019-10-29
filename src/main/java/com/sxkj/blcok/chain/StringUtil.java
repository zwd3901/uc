package com.sxkj.blcok.chain;

import com.google.gson.GsonBuilder;

import java.security.MessageDigest;

public class StringUtil {
    /**
     * sha256加密字符串
     * @param input
     * @return
     */
    public static String applySha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            for (int i = 0, len = hash.length; i < len; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) {
                    hexString.append("0");
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 返回json格式数据
     * @param o
     * @return
     */
    public static String getJson(Object o) {
        return new GsonBuilder().setPrettyPrinting().create().toJson(o);
    }

    /**
     * 返回难度目标字符串，与hash比较，difficulty为5将返回“00000”
     * @param difficulty
     * @return
     */
    public static String getDifficultyString(int difficulty) {
        return new String(new char[difficulty]).replace('\0','0');
    }
}
