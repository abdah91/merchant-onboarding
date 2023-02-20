package com.merchant.utils;

import java.util.UUID;

public class CommonUtils {

    public static String getOTP(){
        return UUID.randomUUID().toString().replace("-", "").substring(0, 6);
    }

}
