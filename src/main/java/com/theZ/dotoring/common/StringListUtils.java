package com.theZ.dotoring.common;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class StringListUtils {

    public static String attach(List<String> target) {
        if (target == null || target.isEmpty()) {
            return "";
        }
        return String.join(" ", target);
    }

    public static List<String> split(String target) {
        if (target == null || target.trim().isEmpty()) {
            return Collections.emptyList();
        }

        return Arrays.asList(target.trim().split("\\s+"));
    }

}
