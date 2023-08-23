package com.theZ.dotoring.app.profile.controller;

import com.theZ.dotoring.app.profile.service.ProfileService;
import com.theZ.dotoring.common.ApiResponse;
import com.theZ.dotoring.common.ApiResponseGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProfileController {

    private final ProfileService profileService;

    @PutMapping("/profile")
    public ApiResponse<ApiResponse.CustomBody<Void>> updateProfile(@RequestPart(required = false) MultipartFile profile, @RequestPart ProfileRequestDTO profileRequestDTO) throws IOException {

        if(profile == null){
            profileService.updateDefaultProfile(profileRequestDTO);
            return ApiResponseGenerator.success(HttpStatus.OK);
        }
        profileService.updateProfile(profile,profileRequestDTO);
        return ApiResponseGenerator.success(HttpStatus.CREATED);
    }

}
