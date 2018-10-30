package com.fanxun.common.utils;

import java.util.UUID;

/**
 * @Author liu
 * @Date 2018-10-29 16:32
 */
public class UUIDUtil {
    public static String getUUID(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            System.out.println(UUIDUtil.getUUID());
        }
    }
}
