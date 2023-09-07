package com.theZ.dotoring.app.major.model;

import com.theZ.dotoring.app.memberMajor.model.MemberMajor;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * 학과 엔티티
 *
 * @author Sonny
 * @version 1.0
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Major {

    @Id
    private String majorName;

    @OneToMany(mappedBy = "major")
    private List<MemberMajor> memberMajors = new ArrayList<>();

    public Major(String majorName) {
        this.majorName = majorName;
    }

    public static Major createMajor(String majorName){
        Major major = new Major(majorName);
        return major;
    }

    public static List<Major> createMajors(List<String> majorNames){
        List<Major> majors = new ArrayList<>();
        for(String majorName : majorNames){
            Major major = createMajor(majorName);
            majors.add(major);
        }
        return majors;
    }
}
