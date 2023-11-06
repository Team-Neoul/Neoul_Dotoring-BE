package com.theZ.dotoring.common;

import java.util.Arrays;
import java.util.List;

public enum Major {
    소프트웨어공학과, 컴퓨터정보통신공학과, 전자공학과, 인공지능학부, 빅데이터융합학과, 빅데이터금융공학융합전공, 지능형모빌리티융합학과, IOT인공지능융합전공, 로봇공학융합전공, 지능실감미디어융합전공, 융합바이오시스템기계공학과, 문헌정보학과, 교육학과, 멀티미디어전공, 전자상거래전공;

    public static Major getMajor(String major){
        Major findMajor = Arrays.stream(Major.values()).filter(m -> m.toString().equals(major)).findAny().orElseThrow(() -> new IllegalArgumentException("존재하지 않는 학과입니다."));
        return findMajor;
    }

    public static List<Major> getMajors(){
        return Arrays.stream(Major.values()).toList();
    }

}
