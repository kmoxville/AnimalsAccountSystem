package com.kmoxvilleEntertainmentGroup.Utils;

public class HelperUtils {
    public static void print(String str) {
        System.out.println(str);
    }

    public static void exit(ExitCodes code) {
        System.exit(code.getCode());
    }
}
