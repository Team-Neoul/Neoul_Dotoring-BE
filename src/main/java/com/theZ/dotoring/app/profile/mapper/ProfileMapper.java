package com.theZ.dotoring.app.profile.mapper;

import com.theZ.dotoring.app.profile.model.Profile;
import com.theZ.dotoring.common.UploadFile;

public class ProfileMapper {

    public static Profile to(UploadFile uploadFile){

        if(uploadFile == null){
            return null;
        }

        Profile profile = Profile.builder()
                .originalProfileName(uploadFile.getOriginalFileName())
                .savedProfileName(uploadFile.getStoreFileName())
                .build();
        return profile;
    }

}
