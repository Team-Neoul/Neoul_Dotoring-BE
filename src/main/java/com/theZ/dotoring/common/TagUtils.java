package com.theZ.dotoring.common;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TagUtils {

    public static String attachTags(List<String> tags) {
        if (tags == null || tags.isEmpty()) {
            return "";
        }
        return String.join(" ", tags);
    }

    public static List<String> splitTags(String tags) {
        if (tags == null || tags.trim().isEmpty()) {
            return Collections.emptyList();
        }

        return Arrays.asList(tags.trim().split("\\s+"));
    }

}
