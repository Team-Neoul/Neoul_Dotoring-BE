package com.theZ.dotoring.common;

import com.theZ.dotoring.app.field.service.FieldService;
import com.theZ.dotoring.app.major.service.MajorService;
import com.theZ.dotoring.app.profile.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class DataInit {

    private final InitService initService;

    //-- running method zone --//
    @PostConstruct
    public void init() {
        initService.initProfile();
        initService.initMajors();
        initService.initField();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        //-- DI --//
        private final ProfileService profileService;
        private final MajorService majorService;
        private final FieldService fieldService;

        //-- init data method zone --//
        public void initProfile() {
            profileService.saveDefaultProfile();
        }

        public void initMajors() {
            majorService.saveAll();
        }

        public void initField(){
            fieldService.saveAll();
        }

    }
}
