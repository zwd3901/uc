package com.sxkj.uc.util;

import java.util.UUID;

public class UUIDGenerator {

    public static final String generator() {
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
