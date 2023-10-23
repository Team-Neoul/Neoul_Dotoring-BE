package com.theZ.dotoring.app.major.service;

import com.theZ.dotoring.app.major.repository.MajorRepository;
import com.theZ.dotoring.common.Major;
import com.theZ.dotoring.common.MessageCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Major에관한 비즈니스 로직이 담겨있습니다.
 *
 * @author Sonny
 * @version 1.0
 */
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MajorService {

    private final MajorRepository majorRepository;

    /**
     * 서버가 시작할 때, Enum Major에 정의되어 있는 학과들을 모두 Majro 엔티티로 만들어 DB에 이를 저장하는 메서드
     *
     */
    public void saveAll(){
        List<String> majorNames = Major.getMajors().stream().map(m -> m.toString()).collect(Collectors.toList());
        List<com.theZ.dotoring.app.major.model.Major> majors = com.theZ.dotoring.app.major.model.Major.createMajors(majorNames);
        majorRepository.saveAll(majors);
    }

    /**
     * 인자로 받은 uncertainMajors를 사용하여 이들이 유효한 Major인지 확인하는 메서드
     *
     * @param uncertainMajors
     * @Exception IllegalArgumentException - 유효하지 않은 학과일 때 발생하는 예외
     *
     */

    public void validMajors(List<String> uncertainMajors){

        /**
         * uncertainMajors에 중복된 값이 들어왔는 지 확인
         */
        int size = uncertainMajors.stream().distinct().collect(Collectors.toList()).size();
        if(uncertainMajors.size() != size){
            throw new IllegalArgumentException(MessageCode.DUPLICATED_VALUE.getValue());
        }

        /**
         *  uncertainMajors가 유효한 Major인지 확인
         */
        List<String> majors = Major.getMajors().stream().map(m -> m.toString()).collect(Collectors.toList());
        if(!majors.containsAll(uncertainMajors)){
            throw new IllegalArgumentException("유효하지 않은 학과입니다.");
        }

    }

    /**
     * 인자로 받은 majors를 사용하여 해당 Major 엔티티들을 반환하는 메서드
     *
     * @param majors
     *
     * @return majorList
     */

    @Transactional(readOnly = true)
    public List<com.theZ.dotoring.app.major.model.Major> findMajors(List<String> majors) {
        List<com.theZ.dotoring.app.major.model.Major> majorList = majorRepository.findAllById(majors);
        return majorList;
    }
}
