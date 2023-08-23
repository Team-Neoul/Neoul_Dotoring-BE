package com.theZ.dotoring.app.major.service;

import com.theZ.dotoring.app.major.repository.MajorRepository;
import com.theZ.dotoring.common.Major;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MajorService {

    private final MajorRepository majorRepository;

    /**
     *  기존 학과 모두 저장
     */
    public void saveAll(){
        List<String> majorNames = Major.getMajors().stream().map(m -> m.toString()).collect(Collectors.toList());
        List<com.theZ.dotoring.app.major.model.Major> majors = com.theZ.dotoring.app.major.model.Major.createMajors(majorNames);
        majorRepository.saveAll(majors);
    }

    public void validMajors(List<String> uncertainMajors){

        /**
         * uncertainMajors에 중복된 값이 들어왔는 지 확인
         */



        /**
         *  uncertainMajors가 유효한 Major인지 확인
         */
        List<String> majors = Major.getMajors().stream().map(m -> m.toString()).collect(Collectors.toList());
        if(!majors.containsAll(uncertainMajors)){
            throw new IllegalArgumentException("유효하지 않은 학과입니다.");
        }

    }

    @Transactional(readOnly = true)
    public List<com.theZ.dotoring.app.major.model.Major> findMajors(List<String> majors) {
        List<com.theZ.dotoring.app.major.model.Major> majorList = majorRepository.findAllById(majors);
        return majorList;
    }
}
