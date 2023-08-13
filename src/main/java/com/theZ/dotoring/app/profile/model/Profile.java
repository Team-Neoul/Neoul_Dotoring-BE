package com.theZ.dotoring.app.profile.model;

import com.theZ.dotoring.common.CommonEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Profile extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long profileId;

    private String savedProfileName;

    private String originalProfileName;

    @Builder
    public Profile(String savedProfileName, String originalProfileName) {
        this.savedProfileName = savedProfileName;
        this.originalProfileName = originalProfileName;
    }

}
