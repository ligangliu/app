package com.fanxun.common.utils;

import java.util.Random;

/**
 * @Author liu
 * @Date 2018-10-20 11:24
 */
public class VerifyCodeUtil {

    public static String getVerifyCode(){
        String verifyCode = String.valueOf(new Random().nextInt(899999) + 100000);
        return verifyCode;
    }

//    public static void main(String[] args) {
//        for (int i=0;i<10;i++){
//            String code = VerifyCodeUtil.getVerifyCode();
//            System.out.println(code);
//        }
//    }
}
