package com.theZ.dotoring.app.memberAccount.model;


import com.theZ.dotoring.app.certificate.model.Certificate;
import com.theZ.dotoring.common.CommonEntity;
import com.theZ.dotoring.enums.MemberType;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 멘토 및 멘티들의 계정정보 엔티티
 *
 * @author Sonny
 * @version 1.0
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberAccount extends CommonEntity implements Serializable {


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
    private MemberType memberType;

    @Setter
    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;

    @OneToMany(mappedBy = "memberAccount")
    private List<Certificate> certificates = new ArrayList<>();

    @Builder
    public MemberAccount(String loginId, String password, String email, List<Certificate> certificates, MemberType memberType, MemberRole memberRole) {
        this.loginId = loginId;
        this.password = password;
        this.email = email;
        this.certificates = certificates;
        this.memberType = memberType;
        this.memberRole = memberRole;
    }

    public static MemberAccount createMemberAccount(String loginId, String password, String email, MemberType memberType ,List<Certificate> certificates, MemberRole memberRole){
        MemberAccount memberAccount = MemberAccount.builder()
                .loginId(loginId)
                .password(password)
                .email(email)
                .certificates(certificates)
                .memberType(memberType)
                .memberRole(memberRole)
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

    public void updateLoginId(String loginId){
        this.loginId = loginId;
    }
}
