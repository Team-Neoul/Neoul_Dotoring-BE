package com.theZ.dotoring.app.notification.controller;

import com.theZ.dotoring.app.memberAccount.model.MemberAccount;
import com.theZ.dotoring.app.notification.dto.*;
import com.theZ.dotoring.app.notification.service.NotificationService;
import com.theZ.dotoring.common.ApiResponse;
import com.theZ.dotoring.common.ApiResponseGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class NotificationController {

    private final NotificationService notificationService;

    @RequestMapping(value = {"/notification", "/notificatation/{notificationId}"}, method = {RequestMethod.POST, RequestMethod.PUT})
    public ApiResponse<?> writeNotification(@AuthenticationPrincipal MemberAccount memberAccount, @PathVariable(required = false) Long notificationId, @RequestBody NotificationReqDTO notificationReqDTO){

        if (notificationId != null){
            return ApiResponseGenerator.success(notificationService.updateNotification(memberAccount, notificationId, notificationReqDTO), HttpStatus.OK);
        }

        return ApiResponseGenerator.success(notificationService.saveNotification(memberAccount, notificationReqDTO), HttpStatus.OK);
    }

    @GetMapping(value = "/notification")
    public ApiResponse<ApiResponse.CustomBody<NotificationResDTO>> getNotificationByFilter(@RequestParam String title, @RequestParam String goal, @RequestParam boolean isClose){

        NotificationResDTO notificationResDTO =  notificationService.getNotificationByFilter(title, goal, isClose);

        return ApiResponseGenerator.success(notificationResDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/notification/{notificationId}")
    public ApiResponse<ApiResponse.CustomBody<NotificationDetailDTO>> getNotification(@AuthenticationPrincipal MemberAccount memberAccount, @PathVariable Long notificationId){

        NotificationDetailDTO notificationResDTO =  notificationService.getNotification(memberAccount, notificationId);

        return ApiResponseGenerator.success(notificationResDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/notification/end/{notificationId}")
    public ApiResponse<ApiResponse.CustomBody<NotificationStatusDTO>> makeStatusToEnd(@AuthenticationPrincipal MemberAccount memberAccount, @PathVariable Long notificationId){

        NotificationStatusDTO notificationResDTO =  notificationService.makeStatusToEnd(memberAccount, notificationId);

        return ApiResponseGenerator.success(notificationResDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/notification/application/{notificationId}")
    public ApiResponse<ApiResponse.CustomBody<Void>> joinNotification(@AuthenticationPrincipal MemberAccount memberAccount, @PathVariable Long notificationId, @RequestBody(required = false) NotificationParticipateReqDTO notificationParticipateReqDTO){

        notificationService.joinNotification(memberAccount, notificationId, notificationParticipateReqDTO);

        return ApiResponseGenerator.success(null, HttpStatus.OK);
    }

}

