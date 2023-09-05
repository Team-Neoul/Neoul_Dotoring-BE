package com.theZ.dotoring.app.memberMajor.service;

import com.theZ.dotoring.app.major.model.Major;
import com.theZ.dotoring.app.memberMajor.model.MemberMajor;
import com.theZ.dotoring.app.memberMajor.repository.MemberMajorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MemberMajorService {

    private final MemberMajorRepository memberMajorRepository;

    public List<MemberMajor> save(List<Major> majors){
        List<MemberMajor> desiredFields = MemberMajor.createDesiredFields(majors);
        List<MemberMajor> memberMajors = memberMajorRepository.saveAll(desiredFields);
        return memberMajors;
    }

}
