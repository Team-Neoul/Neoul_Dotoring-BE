package com.theZ.dotoring.common;

import java.util.Arrays;
import java.util.List;

public enum Field {

    진로, 개발_언어, 경진대회, 외국어, 대외활동, 자격증, 공모전, 학교_생활, 기타;

    public static List<Field> getFields(){
        return Arrays.stream(Field.values()).toList();
    }

}
