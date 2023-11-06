package com.theZ.dotoring.app.profile.model;

import com.theZ.dotoring.app.mento.model.Mento;
import com.theZ.dotoring.common.CommonEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 프로필 엔티티
 *
 * @author Sonny
 * @version 1.0
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Profile extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long profileId;

    private String savedProfileName;

    private String originalProfileName;

    private String profilePath;

    @Builder
    public Profile(String savedProfileName, String originalProfileName, String profilePath) {
        this.savedProfileName = savedProfileName;
        this.originalProfileName = originalProfileName;
        this.profilePath = profilePath;
    }

    public static Profile createProfile(String savedProfileName, String originalProfileName, String profilePath){
        Profile profile = Profile.builder()
                .originalProfileName(originalProfileName)
                .savedProfileName(savedProfileName)
                .profilePath(profilePath)
                .build();
        return profile;
    }

}
