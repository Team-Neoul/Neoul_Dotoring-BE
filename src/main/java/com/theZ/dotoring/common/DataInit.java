package com.theZ.dotoring.common;

import com.theZ.dotoring.app.certificate.model.Certificate;
import com.theZ.dotoring.app.certificate.repository.CertificateRepository;
import com.theZ.dotoring.app.desiredField.model.DesiredField;
import com.theZ.dotoring.app.desiredField.repository.DesiredFieldRepository;
import com.theZ.dotoring.app.field.repository.FieldRepository;
import com.theZ.dotoring.app.field.service.FieldService;
import com.theZ.dotoring.app.major.repository.MajorRepository;
import com.theZ.dotoring.app.major.service.MajorService;
import com.theZ.dotoring.app.memberAccount.model.MemberAccount;
import com.theZ.dotoring.app.memberAccount.repository.MemberAccountRepository;
import com.theZ.dotoring.app.memberMajor.model.MemberMajor;
import com.theZ.dotoring.app.memberMajor.repository.MemberMajorRepository;
import com.theZ.dotoring.app.menti.dto.SaveMentiRqDTO;
import com.theZ.dotoring.app.menti.model.Menti;
import com.theZ.dotoring.app.menti.repository.MentiRepository;
import com.theZ.dotoring.app.mento.dto.SaveMentoRqDTO;
import com.theZ.dotoring.app.mento.model.Mento;
import com.theZ.dotoring.app.mento.repository.MentoRepository;
import com.theZ.dotoring.app.profile.model.Profile;
import com.theZ.dotoring.app.profile.repository.ProfileRepository;
import com.theZ.dotoring.app.profile.service.ProfileService;
import com.theZ.dotoring.enums.MemberType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DataInit {

    private final InitService initService;

    //-- running method zone --//
    @PostConstruct
    public void init() {
        initService.initProfile();
        initService.initMajors();
        initService.initField();
        initService.initMentoAndMenti();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        //-- DI --//
        private final ProfileService profileService;
        private final MajorService majorService;
        private final FieldService fieldService;
        private final MentiRepository mentiRepository;
        private final MentoRepository mentoRepository;
        private final CertificateRepository certificateRepository;
        private final MemberAccountRepository memberAccountRepository;
        private final DesiredFieldRepository desiredFieldRepository;
        private final MemberMajorRepository memberMajorRepository;


        //-- init data method zone --//
        public void initProfile() {
            profileService.saveDefaultProfile();
        }

        public void initMajors() {
            majorService.saveAll();
        }

        public void initField(){
            fieldService.saveAll();
        }

        public void initMentoAndMenti(){
            /**
             *  멘티 저장
             */

            Certificate certificate = new Certificate("원본 증명서이름입니다.", "저장시 사용될 증명서이름입니다.");
            List<Certificate> certificates = List.of(certificate);
            List<Certificate> saveCertificates = certificateRepository.saveAll(certificates);


            MemberAccount memberAccount = new MemberAccount("dotoring12", "dotoring12@@", "dotoring12@naver.com", saveCertificates, MemberType.MENTI);
            memberAccountRepository.save(memberAccount);

            Profile defaultProfile = profileService.getDefaultProfile();

            List<String> fieldList = List.of("진로", "개발_언어", "공모전");
            List<com.theZ.dotoring.app.field.model.Field> fields = fieldList.stream().map(com.theZ.dotoring.app.field.model.Field::new).collect(Collectors.toList());
            List<DesiredField> desiredFields = DesiredField.createDesiredFields(fields);
            List<DesiredField> desiredFieldList = desiredFieldRepository.saveAll(desiredFields);

            List<String> majorsList = List.of("소프트웨어공학과", "수학교육과");
            List<com.theZ.dotoring.app.major.model.Major> majors = majorsList.stream().map(com.theZ.dotoring.app.major.model.Major::new).collect(Collectors.toList());
            List<MemberMajor> memberMajors = MemberMajor.createDesiredFields(majors);
            List<MemberMajor> memberMajorList = memberMajorRepository.saveAll(memberMajors);

            SaveMentiRqDTO saveMentiRqDTO = new SaveMentiRqDTO("전남대학교",3L,majorsList,fieldList,"도토링","안녕하세요 전남대학교 3학년 도토링입니다.","dotoring12","dotoring12@@", "dotoring12@naver.com");

            Menti menti = Menti.createMenti(saveMentiRqDTO.getNickname(), saveMentiRqDTO.getIntroduction(), saveMentiRqDTO.getSchool(), saveMentiRqDTO.getGrade(), memberAccount,defaultProfile,desiredFieldList,memberMajorList);
            mentiRepository.save(menti);

            /**
             *  멘토 저장 1 - 멘티와 분야가 3개 겹침  => 우선 순위 1
             */
            Certificate certificate5 = new Certificate("원본 증명서이름입니다.", "저장시 사용될 dotori증명서이름입니다.");
            List<Certificate> certificates5 = List.of(certificate5);
            List<Certificate> saveCertificates5 = certificateRepository.saveAll(certificates5);

            MemberAccount memberAccount5 = new MemberAccount("sonny12345", "sonny12345@@", "sonny12345@naver.com", saveCertificates5, MemberType.MENTO);
            memberAccountRepository.save(memberAccount5);

            List<String> fieldList5 = List.of("진로", "개발_언어", "공모전");
            List<com.theZ.dotoring.app.field.model.Field> fields5 = fieldList5.stream().map(com.theZ.dotoring.app.field.model.Field::new).collect(Collectors.toList());
            List<DesiredField> desiredFields5 = DesiredField.createDesiredFields(fields5);
            List<DesiredField> desiredFieldList5 = desiredFieldRepository.saveAll(desiredFields5);

            List<String> majorsList5 = List.of("소프트웨어공학과", "수학교육과");
            List<com.theZ.dotoring.app.major.model.Major> majors5 = majorsList5.stream().map(com.theZ.dotoring.app.major.model.Major::new).collect(Collectors.toList());
            List<MemberMajor> memberMajors5 = MemberMajor.createDesiredFields(majors5);
            List<MemberMajor> memberMajorList5 = memberMajorRepository.saveAll(memberMajors5);

            SaveMentoRqDTO mentoSignupRequestDTO = new SaveMentoRqDTO("전남대학교",3L,fieldList,majorsList,"황대선","안녕하세요 전남대학교 3학년 황대선입니다!","sonny12345", "sonny12345@@", "sonny12345@naver.com");

            Mento mento = Mento.createMento(mentoSignupRequestDTO.getNickname(), mentoSignupRequestDTO.getIntroduction(), mentoSignupRequestDTO.getSchool(), mentoSignupRequestDTO.getGrade(), memberAccount5, defaultProfile, desiredFieldList5, memberMajorList5);
            mentoRepository.save(mento);

            /**
             *  멘토 저장 2 - 멘티와 분야가 1개 겹침, 멘티와 동일 학과 2개 => 우선 순위 2
             */

            Certificate certificate1 = new Certificate("원본 증명서이름입니다.", "저장시 사용될 장현지 증명서이름입니다.");
            List<Certificate> certificates1 = List.of(certificate1);
            List<Certificate> saveCertificates1 = certificateRepository.saveAll(certificates1);


            MemberAccount memberAccount1 = new MemberAccount("hynji12345", "hynji12345@@", "hynji12345@naver.com", saveCertificates1, MemberType.MENTO);
            memberAccountRepository.save(memberAccount1);


            List<String> fieldList1 = List.of("외국어", "개발_언어");
            List<com.theZ.dotoring.app.field.model.Field> fields1 = fieldList1.stream().map(com.theZ.dotoring.app.field.model.Field::new).collect(Collectors.toList());
            List<DesiredField> desiredFields1 = DesiredField.createDesiredFields(fields1);
            List<DesiredField> desiredFieldList1 = desiredFieldRepository.saveAll(desiredFields1);

            List<String> majorsList1 = List.of("소프트웨어공학과", "수학교육과");
            List<com.theZ.dotoring.app.major.model.Major> majors1 = majorsList1.stream().map(com.theZ.dotoring.app.major.model.Major::new).collect(Collectors.toList());
            List<MemberMajor> memberMajors1 = MemberMajor.createDesiredFields(majors1);
            List<MemberMajor> memberMajorList1 = memberMajorRepository.saveAll(memberMajors1);

            SaveMentoRqDTO mentoSignupRequestDTO1 = new SaveMentoRqDTO("전남대학교",3L,fieldList1,majorsList1,"장현지","안녕하세요 전남대학교 3학년 장현지에요!!","hynji12345", "hynji12345@@", "hynji12345@naver.com");

            Mento mento1 = Mento.createMento(mentoSignupRequestDTO1.getNickname(), mentoSignupRequestDTO1.getIntroduction(), mentoSignupRequestDTO1.getSchool(), mentoSignupRequestDTO1.getGrade(), memberAccount1, defaultProfile, desiredFieldList1, memberMajorList1);
            mentoRepository.save(mento1);


            /**
             *  멘토 저장 3 - 멘티와 분야가 1개 겹침, 멘티와 동일 학과 1개 => 우선 순위 3
             */

            Certificate certificate2 = new Certificate("원본 증명서이름입니다.", "저장시 사용될 이승건 증명서이름입니다.");
            List<Certificate> certificates2 = List.of(certificate2);
            List<Certificate> saveCertificates2 = certificateRepository.saveAll(certificates2);


            MemberAccount memberAccount2 = new MemberAccount("sksks12345", "sksks12345@@", "sksks12345@naver.com", saveCertificates2, MemberType.MENTO);
            memberAccountRepository.save(memberAccount2);


            List<String> fieldList2 = List.of("외국어", "개발_언어");
            List<com.theZ.dotoring.app.field.model.Field> fields2 = fieldList2.stream().map(com.theZ.dotoring.app.field.model.Field::new).collect(Collectors.toList());
            List<DesiredField> desiredFields2 = DesiredField.createDesiredFields(fields2);
            List<DesiredField> desiredFieldList2 = desiredFieldRepository.saveAll(desiredFields2);

            List<String> majorsList2 = List.of("소프트웨어공학과");
            List<com.theZ.dotoring.app.major.model.Major> majors2 = majorsList2.stream().map(com.theZ.dotoring.app.major.model.Major::new).collect(Collectors.toList());
            List<MemberMajor> memberMajors2 = MemberMajor.createDesiredFields(majors2);
            List<MemberMajor> memberMajorList2 = memberMajorRepository.saveAll(memberMajors2);

            SaveMentoRqDTO mentoSignupRequestDTO2 = new SaveMentoRqDTO("전남대학교",4L,fieldList2,majorsList2,"이승건","안녕하세요 전남대학교 4학년 이승건입니다!!","sksks12345", "sksks12345@@", "sksks12345@naver.com");

            Mento mento2 = Mento.createMento(mentoSignupRequestDTO2.getNickname(), mentoSignupRequestDTO2.getIntroduction(), mentoSignupRequestDTO2.getSchool(), mentoSignupRequestDTO2.getGrade(), memberAccount2, defaultProfile, desiredFieldList2, memberMajorList2);
            mentoRepository.save(mento2);


            /**
             *  멘토 저장 4 - 멘티와 분야가 겹치는 것이 없다.
             */

            Certificate certificate3 = new Certificate("원본 증명서이름입니다.", "저장시 사용될 임수미 증명서이름입니다.");
            List<Certificate> certificates3 = List.of(certificate3);
            List<Certificate> saveCertificates3 = certificateRepository.saveAll(certificates3);


            MemberAccount memberAccount3 = new MemberAccount("smsms12345", "smsms12345@@", "smsms12345@naver.com", saveCertificates3, MemberType.MENTO);
            memberAccountRepository.save(memberAccount3);


            List<String> fieldList3 = List.of("외국어", "자격증");
            List<com.theZ.dotoring.app.field.model.Field> fields3 = fieldList3.stream().map(com.theZ.dotoring.app.field.model.Field::new).collect(Collectors.toList());
            List<DesiredField> desiredFields3 = DesiredField.createDesiredFields(fields3);
            List<DesiredField> desiredFieldList3 = desiredFieldRepository.saveAll(desiredFields3);

            List<String> majorsList3 = List.of("소프트웨어공학과");
            List<com.theZ.dotoring.app.major.model.Major> majors3 = majorsList3.stream().map(com.theZ.dotoring.app.major.model.Major::new).collect(Collectors.toList());
            List<MemberMajor> memberMajors3 = MemberMajor.createDesiredFields(majors3);
            List<MemberMajor> memberMajorList3 = memberMajorRepository.saveAll(memberMajors3);

            SaveMentoRqDTO mentoSignupRequestDTO3 = new SaveMentoRqDTO("전남대학교",4L,fieldList3,majorsList3,"이시현","안녕하세요 전남대학교 3학년 임수미입니다!!","smsms12345", "smsms12345@@", "smsms12345@naver.com");

            Mento mento3 = Mento.createMento(mentoSignupRequestDTO3.getNickname(), mentoSignupRequestDTO3.getIntroduction(), mentoSignupRequestDTO3.getSchool(), mentoSignupRequestDTO3.getGrade(), memberAccount3, defaultProfile, desiredFieldList3, memberMajorList3);
            mentoRepository.save(mento3);

        }


    }
}
