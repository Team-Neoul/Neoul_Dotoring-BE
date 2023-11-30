package com.theZ.dotoring.common;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StringListUtilsTest {

    private final List<String> tags = List.of("#tag1", "#tag2", "#tag3");
    private final String tagsString = "#tag1 #tag2 #tag3";

    @Test
    void attachTags() {
        assertEquals(tagsString, StringListUtils.attach(tags));
    }

    @Test
    void splitTags() {
        assertEquals(tags, StringListUtils.split(tagsString));
    }
}