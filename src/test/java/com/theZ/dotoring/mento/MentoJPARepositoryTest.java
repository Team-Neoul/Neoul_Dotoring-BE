package com.theZ.dotoring.mento;

import com.theZ.dotoring.app.certificate.model.Certificate;
import com.theZ.dotoring.app.certificate.repository.CertificateRepository;
import com.theZ.dotoring.app.desiredField.model.DesiredField;
import com.theZ.dotoring.app.desiredField.repository.DesiredFieldRepository;
import com.theZ.dotoring.app.field.model.Field;
import com.theZ.dotoring.app.field.repository.FieldRepository;
import com.theZ.dotoring.app.major.model.Major;
import com.theZ.dotoring.app.major.repository.MajorRepository;
import com.theZ.dotoring.app.memberAccount.model.MemberAccount;
import com.theZ.dotoring.app.memberAccount.repository.MemberAccountRepository;
import com.theZ.dotoring.app.memberMajor.model.MemberMajor;
import com.theZ.dotoring.app.memberMajor.repository.MemberMajorRepository;
import com.theZ.dotoring.app.mento.dto.MentoSignupRequestDTO;
import com.theZ.dotoring.app.mento.model.Mento;
import com.theZ.dotoring.app.mento.repository.MentoRepository;
import com.theZ.dotoring.app.profile.model.Profile;
import com.theZ.dotoring.app.profile.repository.ProfileRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;


@DataJpaTest
public class MentoJPARepositoryTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private MentoRepository mentoRepository;

    @Autowired
    private CertificateRepository certificateRepository;

    @Autowired
    private MemberAccountRepository memberAccountRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private DesiredFieldRepository desiredFieldRepository;

    @Autowired
    private MemberMajorRepository memberMajorRepository;

    @Autowired
    private MajorRepository majorRepository;

    @Autowired
    private FieldRepository fieldRepository;

    @BeforeEach
    public void setUp(){
        List<String> majorNames = com.theZ.dotoring.common.Major.getMajors().stream().map(m -> m.toString()).collect(Collectors.toList());
        List<com.theZ.dotoring.app.major.model.Major> initmajors = com.theZ.dotoring.app.major.model.Major.createMajors(majorNames);
        majorRepository.saveAll(initmajors);

        List<String> fieldNames = com.theZ.dotoring.common.Field.getFields().stream().map(f -> f.toString()).collect(Collectors.toList());
        List<com.theZ.dotoring.app.field.model.Field> initFields = com.theZ.dotoring.app.field.model.Field.createFields(fieldNames);
        fieldRepository.saveAll(initFields);

        Certificate certificate = new Certificate("원본 증명서이름입니다.", "저장시 사용될 증명서이름입니다.");
        List<Certificate> certificates = List.of(certificate);
        List<Certificate> saveCertificates = certificateRepository.saveAll(certificates);


        MemberAccount memberAccount = new MemberAccount("sonny12345", "sonny12345@@", "sonny12345@naver.com", saveCertificates);
        memberAccountRepository.save(memberAccount);


        Profile profile = new Profile("default_profile20230812110822", "default_profile");
        Profile savedProfile = profileRepository.save(profile);

        List<String> fieldList = List.of("진로", "개발_언어");
        List<Field> fields = fieldList.stream().map(Field::new).collect(Collectors.toList());
        List<DesiredField> desiredFields = DesiredField.createDesiredFields(fields);
        List<DesiredField> desiredFieldList = desiredFieldRepository.saveAll(desiredFields);

        List<String> majorsList = List.of("소프트웨어공학과", "수학교육과");
        List<Major> majors = majorsList.stream().map(Major::new).collect(Collectors.toList());
        List<MemberMajor> memberMajors = MemberMajor.createDesiredFields(majors);
        List<MemberMajor> memberMajorList = memberMajorRepository.saveAll(memberMajors);

        MentoSignupRequestDTO mentoSignupRequestDTO = new MentoSignupRequestDTO("전남대학교",3L,majorsList,fieldList,"황대선","안녕하세요 전남대학교 3학년 황대선입니다.","sonny12345","sonny12345@@", "sonny12345@naver.com");

        Mento mento = Mento.createMento(mentoSignupRequestDTO.getNickname(), mentoSignupRequestDTO.getIntroduction(), mentoSignupRequestDTO.getSchool(), mentoSignupRequestDTO.getGrade(), memberAccount,savedProfile,desiredFieldList,memberMajorList);
        mentoRepository.save(mento);

        em.flush();
    }

    @Test
    public void findMentoWithProfileUsingFetchJoinByMentoId_test(){
        Mento mento = mentoRepository.findMentoWithProfileUsingFetchJoinByMentoId(1L).get();

        Assertions.assertThat("전남대학교").isEqualTo(mento.getSchool());
        Assertions.assertThat("황대선").isEqualTo(mento.getNickname());
        Assertions.assertThat("default_profile20230812110822").isEqualTo(mento.getProfile().getSavedProfileName());
        Assertions.assertThat("default_profile").isEqualTo(mento.getProfile().getOriginalProfileName());

    }


}
