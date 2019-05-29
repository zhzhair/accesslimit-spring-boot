package com.example.demo.util;

import java.util.Random;

public class StringTools {
    /**
     * 模拟手机号
     */
    public static String getMobileStr(int userId){
        String[] strings = {"13","15","16","18"};
        String beginString = strings[new Random().nextInt(4)];
        String endString = String.valueOf(userId);
        int length = 9 - endString.length();
        StringBuilder stringBuilder = new StringBuilder(beginString);
        for (int i = 0; i < length; i++) {
            stringBuilder.append("0");
        }
        return stringBuilder.append(endString).toString();
    }

    /**
     * 模拟手机号
     */
    public static String getMobileStr(){
        return getMobileStr(new Random().nextInt(10_0000_0000));
    }
}
