package com.theZ.dotoring.app.mento.handler;

import com.theZ.dotoring.app.certificate.model.Certificate;
import com.theZ.dotoring.app.certificate.service.CertificateService;
import com.theZ.dotoring.app.desiredField.model.DesiredField;
import com.theZ.dotoring.app.desiredField.service.DesiredFieldService;
import com.theZ.dotoring.app.field.model.Field;
import com.theZ.dotoring.app.field.service.FieldService;
import com.theZ.dotoring.app.major.model.Major;
import com.theZ.dotoring.app.major.service.MajorService;
import com.theZ.dotoring.app.memberAccount.model.MemberAccount;
import com.theZ.dotoring.app.memberAccount.service.MemberAccountService;
import com.theZ.dotoring.app.memberMajor.model.MemberMajor;
import com.theZ.dotoring.app.memberMajor.service.MemberMajorService;
import com.theZ.dotoring.app.mento.dto.SaveMentoRqDTO;
import com.theZ.dotoring.app.mento.service.MentoService;
import com.theZ.dotoring.app.profile.model.Profile;
import com.theZ.dotoring.app.profile.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SaveMentoHandler {

    private final MentoService mentoService;
    private final MemberAccountService memberAccountService;
    private final CertificateService certificateService;
    private final ProfileService profileService;
    private final MajorService majorService;
    private final FieldService fieldService;
    private final DesiredFieldService desiredFieldService;
    private final MemberMajorService memberMajorService;


    @Transactional
    public void execute(SaveMentoRqDTO saveMentoRqDTO, List<MultipartFile> certificates) throws IOException {
        /**
         *  증명서 저장
         */
        List<Certificate> savedCertificates = certificateService.saveCertifications(certificates);

        /**
         * 회원 계정 저장
         */

        MemberAccount memberAccount = memberAccountService.saveMentoAccount(saveMentoRqDTO, savedCertificates);

        /**
         * 회원가입이후 프로필 변경전까지는 기본 프로필 이미지 사용
         */

        Profile defaultProfile = profileService.getDefaultProfile();

        /**
         *  fields와 majors가 유효한 지를 검사한다음에 유효하지 않으면 에러 터뜨리고, 유효하다면, 해당 majors와 fields들을 가져온다.
         */

        fieldService.validFields(saveMentoRqDTO.getFields());
        List<Field> fields = fieldService.findFields(saveMentoRqDTO.getFields());
        majorService.validMajors(saveMentoRqDTO.getMajors());
        List<Major> majors = majorService.findMajors(saveMentoRqDTO.getMajors());


        List<DesiredField> desiredFields = desiredFieldService.save(fields);
        List<MemberMajor> memberMajors = memberMajorService.save(majors);

        mentoService.saveMento(saveMentoRqDTO,memberAccount,defaultProfile,desiredFields,memberMajors);
    }

}
