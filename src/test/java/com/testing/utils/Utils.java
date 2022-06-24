package com.testing.utils;

import java.util.Random;

public class Utils {

    public static String getFileNameFromPath(String filePath){
        String[] splitPath = filePath.split("/");
        return splitPath[splitPath.length - 1];
    }

    public static String getRandomText(int length){
        int leftLimit = 97;
        int rightLimit = 122;
        int targetStringLength = length;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString();
    }
}
