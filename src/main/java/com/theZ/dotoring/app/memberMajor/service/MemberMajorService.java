package com.theZ.dotoring.app.memberMajor.service;

import com.theZ.dotoring.app.major.model.Major;
import com.theZ.dotoring.app.memberMajor.model.MemberMajor;
import com.theZ.dotoring.app.memberMajor.repository.MemberMajorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * MemberMajor에관한 비즈니스 로직이 담겨있습니다.
 *
 * @author Sonny
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MemberMajorService {

    private final MemberMajorRepository memberMajorRepository;

    /**
     * 인자로 받은 majors 사용하여 MemberMajor 엔티티를 생성하여 이를 DB에 저장한 후 반환하는 메서드
     *
     * @param majors
     *
     * @return memberMajors 엔티티들을 반환
     */

    public List<MemberMajor> save(List<Major> majors){
        List<MemberMajor> desiredFields = MemberMajor.createDesiredFields(majors);
        List<MemberMajor> memberMajors = memberMajorRepository.saveAll(desiredFields);
        return memberMajors;
    }

}
