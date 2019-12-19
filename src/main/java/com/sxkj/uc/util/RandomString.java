package com.sxkj.uc.util;

import java.util.Random;

/**
 * @ClassName RandomString
 * @Description: TODO
 * @Author zwd
 * @Date 2019/12/13 0013
 **/
public final class RandomString {
    private static final String STR = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int STR_LEN = 10;

    public static String getStr(int max, int min) {

        if (max <= 0) {
            max = STR_LEN;
        }
        if (min <= 0) {
            min = 1;
        }
        if (max < min) {
            int t = max;
            max = min;
            min = t;
        }
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        int length;
        if (max == min) {
            length = random.nextInt(max);
        } else {
            length = random.nextInt(max - min + 1) + min;
        }
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            sb.append(STR.charAt(number));
        }
        return sb.toString();
    }

    public static String getStr(int max) {
        if (max <= 0) {
            max = STR_LEN;
        }
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        int length = random.nextInt(max) + 1;
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            sb.append(STR.charAt(number));
        }
        return sb.toString();
    }

    public static String getStr() {
        return getStr(100, 50);
    }

    public static void main(String[] args) {
        String s = getStr(5, 2);
        s = getStr(2, 5);
        s = getStr(5, -2);
        s = getStr(-2, -4);
        s = getStr(-3, 2);
        s = getStr(5);
        s = getStr(-5);

    }
}
