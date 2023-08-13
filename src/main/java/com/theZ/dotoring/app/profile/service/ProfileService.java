package com.theZ.dotoring.app.profile.service;

import com.theZ.dotoring.app.profile.model.Profile;
import com.theZ.dotoring.app.profile.repository.ProfileRepository;
import com.theZ.dotoring.common.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ProfileService {

    private final FileUtils fileUtils;
    private final ProfileRepository profileRepository;

    /**
     * 기본 프로필 이미지 가져오기
     */
    @Transactional(readOnly = true)
    public Profile getDefaultProfile(){
        Profile profile = profileRepository.findById(1L).orElseThrow(() -> new IllegalStateException("존재하지 않는 이미지입니다."));
        return profile;
    }

    /**
     *  기본 프로필 이미지 저장하기
     */
    public Profile saveDefaultProfile(){
        Profile profile = new Profile("default_profile20230812110822", "default_profile");
        Profile savedProfile = profileRepository.save(profile);
        return savedProfile;
    }


}
