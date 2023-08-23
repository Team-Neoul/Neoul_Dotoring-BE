package com.theZ.dotoring.app.menti.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class FindMentiByIdRespDTO {

    private Long mentiId;
    private String profileImage;
    private String nickname;
    private String preferredMentoring;
    private List<String> fields;
    private List<String> majors;
    private String introduction;

    @Builder
    public FindMentiByIdRespDTO(Long mentiId, String profileImage, String nickname, String preferredMentoring, List<String> fields, List<String> majors, String introduction) {
        this.mentiId = mentiId;
        this.profileImage = profileImage;
        this.nickname = nickname;
        this.preferredMentoring = preferredMentoring;
        this.fields = fields;
        this.majors = majors;
        this.introduction = introduction;
    }

}
