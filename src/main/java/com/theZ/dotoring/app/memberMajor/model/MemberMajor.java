package com.theZ.dotoring.app.memberMajor.model;

import com.theZ.dotoring.app.desiredField.model.DesiredField;
import com.theZ.dotoring.app.field.model.Field;
import com.theZ.dotoring.app.major.model.Major;
import com.theZ.dotoring.app.mento.model.Mento;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberMajor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberMajorId;

    @ManyToOne
    @JoinColumn(name = "major_name")
    private Major major;

    @ManyToOne
    @JoinColumn(name = "mento_id")
    private Mento mento;

    public void mappingMento(Mento mento){
        this.mento = mento;
    }

    public MemberMajor(Major major) {
        this.major = major;
    }

    private static MemberMajor createMemberMajor(Major major){
        MemberMajor memberMajor = new MemberMajor(major);
        return memberMajor;
    }

    public static List<MemberMajor> createDesiredFields(List<Major> majors){
        if(majors.isEmpty()){
            throw new IllegalArgumentException("분야가 담겨있지 않습니다.");
        }
        List<MemberMajor> memberMajors = new ArrayList<>();
        for(Major major : majors){
            MemberMajor memberMajor = createMemberMajor(major);
            memberMajors.add(memberMajor);
        }
        return memberMajors;
    }
}
