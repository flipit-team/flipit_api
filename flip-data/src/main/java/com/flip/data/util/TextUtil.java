package com.flip.data.util;


import org.apache.commons.lang3.StringUtils;

/**
 * @author Obi on 10/05/2019
 */
public class TextUtil {

    public static String capitalizeInitialOnly(String str) {
        if (StringUtils.isEmpty(str))
            return "";

        str = str.trim();
        if (str.length() == 1)
            return str.toUpperCase();

        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    public static String toInitalUpper(String str) {
        if (StringUtils.isEmpty(str))
            return "";

        str = str.trim();
        if (str.length() == 1)
            return str.toUpperCase();

        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public static String toInitalLower(String str) {
        if (StringUtils.isEmpty(str))
            return "";

        str = str.trim();
        if (str.length() == 1)
            return str.toLowerCase();

        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }
}
