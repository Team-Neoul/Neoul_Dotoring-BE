package com.theZ.dotoring.app.profile.service;

import com.theZ.dotoring.app.memberAccount.service.MemberAccountService;
import com.theZ.dotoring.app.menti.model.Menti;
import com.theZ.dotoring.app.menti.repository.MentiRepository;
import com.theZ.dotoring.app.mento.model.Mento;
import com.theZ.dotoring.app.mento.repository.MentoRepository;
import com.theZ.dotoring.app.profile.controller.ProfileRequestDTO;
import com.theZ.dotoring.app.profile.model.Profile;
import com.theZ.dotoring.app.profile.repository.ProfileRepository;
import com.theZ.dotoring.common.FileUtils;
import com.theZ.dotoring.common.MessageCode;
import com.theZ.dotoring.common.UploadFile;
import com.theZ.dotoring.exception.DefaultProfileImageNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.NoSuchElementException;

/**
 * Profile에관한 비즈니스 로직이 담겨있습니다.
 *
 * @author Sonny
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ProfileService {

    private final FileUtils fileUtils;
    private final ProfileRepository profileRepository;
    private final MemberAccountService memberAccountService;
    private final MentoRepository mentoRepository;
    private final MentiRepository mentiRepository;

    /**
     * 기본 프로필을 반환하는 메서드
     *
     * @retrun profile
     */
    @Transactional(readOnly = true)
    public Profile getDefaultProfile(){
        Profile profile = profileRepository.findById(1L).orElseThrow(() -> new DefaultProfileImageNotFoundException(MessageCode.IMAGE_NOT_FOUND));
        return profile;
    }

    /**
     * 기본 프로필을 저장하는 메서드
     *
     * @retrun savedProfile
     */
    public Profile saveDefaultProfile(){
        Profile profile = new Profile("default_profile.png", "default_profile", "/images/default_profile.png");
        Profile savedProfile = profileRepository.save(profile);
        return savedProfile;
    }


    /**
     * 해당 회원의 프로필을 수정하는 메서드
     *
     * @param multipartFile
     * @param profileRequestDTO
     * @Exception - NoSuchElementException 존재하지 않는 회원입니다.
     *
     */

    public void updateProfile(MultipartFile multipartFile, ProfileRequestDTO profileRequestDTO) throws IOException {
        UploadFile uploadFile = fileUtils.storeFile(multipartFile);
        Profile profile = Profile.createProfile(uploadFile.getStoreFileName(), uploadFile.getOriginalFileName(), FileUtils.getFilePath(uploadFile.getStoreFileName()));
        Profile savedProfile = profileRepository.save(profile);
        if(memberAccountService.isMento(profileRequestDTO.getMemberId())){
            Mento mento = mentoRepository.findById(profileRequestDTO.getMemberId()).orElseThrow(() -> new NoSuchElementException("존재하지 않는 멘토입니다."));
            mento.mappingProfile(savedProfile);
        }
        Menti menti = mentiRepository.findById(profileRequestDTO.getMemberId()).orElseThrow(() -> new NoSuchElementException("존재하지 않는 멘티입니다."));
        menti.mappingProfile(savedProfile);
    }


    /**
     * 해당 회원의 프로필을 기본 프로필로 수정하는 메서드
     *
     * @param profileRequestDTO
     * @Exception - NoSuchElementException 존재하지 않는 회원입니다.
     *
     */

    public void updateDefaultProfile(ProfileRequestDTO profileRequestDTO) {
        Profile defaultProfile = getDefaultProfile();
        if(memberAccountService.isMento(profileRequestDTO.getMemberId())){
            Mento mento = mentoRepository.findById(profileRequestDTO.getMemberId()).orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원입니다."));
            mento.mappingProfile(defaultProfile);
        }
        Menti menti = mentiRepository.findById(profileRequestDTO.getMemberId()).orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원입니다."));
        menti.mappingProfile(defaultProfile);
    }

}
