package com.testing.utils;

public class Utils {

    public static String getFileNameFromPath(String filePath){
        String[] splitPath = filePath.split("/");
        return splitPath[splitPath.length - 1];
    }
}
