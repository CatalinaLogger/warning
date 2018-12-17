package com.six.system.common.utils;

import org.springframework.util.StringUtils;

public class LevelUtil {
    public final static String SEPARATOR = ".";
    public final static String ROOT = "0";

    public static String calculateLevel(String parentLevel, Integer parentId) {
        if (StringUtils.isEmpty(parentLevel)) {
            return ROOT;
        } else {
            return parentLevel+SEPARATOR+parentId.toString();
        }
    }
}
