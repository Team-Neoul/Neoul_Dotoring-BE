package com.theZ.dotoring.app.memberAccount.model;


import com.theZ.dotoring.app.certificate.model.Certificate;
import com.theZ.dotoring.common.CommonEntity;
import com.theZ.dotoring.enums.MemberType;
import com.theZ.dotoring.enums.Status;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberAccount extends CommonEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "memberId", nullable = false)
    private Long id;

    @Column(name = "LOGIN_ID", unique = true)
    @Size(min = 8, max = 12)
    private String loginId;

    //    @Size(min = 7, max = 12)
    private String password;

    @Email
    private String email;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private MemberType memberType;

    @OneToMany(mappedBy = "memberAccount")
    private List<Certificate> certificates = new ArrayList<>();

    @Builder
    public MemberAccount(String loginId, String password, String email, List<Certificate> certificates, MemberType memberType) {
        this.loginId = loginId;
        this.password = password;
        this.email = email;
        this.status = Status.WAIT;
        this.certificates = certificates;
        this.memberType = memberType;
    }

    public static MemberAccount createMemberAccount(String loginId, String password, String email, MemberType memberType ,List<Certificate> certificates){
        MemberAccount memberAccount = MemberAccount.builder()
                .loginId(loginId)
                .password(password)
                .email(email)
                .certificates(certificates)
                .memberType(memberType)
                .build();
        memberAccount.mappingCertificate(certificates);
        return memberAccount;
    }

    public void mappingCertificate(List<Certificate> certificates){
        for(Certificate certificate : certificates){
            certificate.mappingMemberAccount(this);
        }
    }

    public void updatePassword(String password) {
        this.password = password;
    }
}
