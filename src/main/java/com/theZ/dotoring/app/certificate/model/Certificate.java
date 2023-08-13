package com.theZ.dotoring.app.certificate.model;

import com.theZ.dotoring.common.CommonEntity;
import com.theZ.dotoring.app.memberAccount.model.MemberAccount;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Certificate extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "certificateId", nullable = false)
    private Long id;

    private String originalFileName;

    private String saveFileName;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private MemberAccount memberAccount;

    public void mappingMemberAccount(MemberAccount memberAccount) {
        this.memberAccount = memberAccount;
    }

    @Builder
    public Certificate(String originalFileName, String saveFileName) {
        this.originalFileName = originalFileName;
        this.saveFileName = saveFileName;
    }
}
