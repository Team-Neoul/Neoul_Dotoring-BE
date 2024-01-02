package com.theZ.dotoring.app.notification.service;

import com.theZ.dotoring.app.memberAccount.model.MemberAccount;
import com.theZ.dotoring.app.menti.model.Menti;
import com.theZ.dotoring.app.menti.repository.MentiRepository;
import com.theZ.dotoring.app.mento.model.Mento;
import com.theZ.dotoring.app.mento.repository.MentoRepository;
import com.theZ.dotoring.app.notification.dto.NotificationReqDTO;
import com.theZ.dotoring.app.notification.dto.NotificationSaveResDTO;
import com.theZ.dotoring.app.notification.dto.NotificationUpdateResDTO;
import com.theZ.dotoring.app.notification.model.Notification;
import com.theZ.dotoring.app.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    private final MentoRepository mentoRepository;

    private final MentiRepository mentiRepository;

    @Transactional
    public NotificationSaveResDTO saveNotification(MemberAccount memberAccount, NotificationReqDTO reqDTO){

        Notification notification = notificationRepository.save(Notification.of(reqDTO, memberAccount));

        Map<String, String> map = getMemberNicknameAndMajor(memberAccount);

        return NotificationSaveResDTO.of(notification, map.get("memberNickname"), map.get("memberMajor"));
    }


    @Transactional
    public NotificationUpdateResDTO updateNotification(MemberAccount memberAccount, Long notificationId, NotificationReqDTO reqDTO){
        Notification notification = notificationRepository.findById(notificationId).orElseThrow(() -> new RuntimeException("notification이 존재하지 않습니다."));

        notification.updateNotification(reqDTO);

        Map<String, String> map = getMemberNicknameAndMajor(memberAccount);

        return NotificationUpdateResDTO.of(notification, map.get("memberNickname"), map.get("memberMajor"));
    }


    /***
     * Member의 멘토나 멘티 분기에 따라서 닉네임, 전공을 Map 자료구조로 반환하는 메서드
     *
     * @param memberAccount
     * @return
     */
    private Map<String, String> getMemberNicknameAndMajor(MemberAccount memberAccount){
        Map<String, String> map = new HashMap<>();

        if (Objects.equals(memberAccount.getMemberType().toString(), "MENTO")){
            Mento mento = mentoRepository.findById(memberAccount.getId()).get();

            map.put("memberNickname", mento.getNickname());
            map.put("memberMajor", mento.getMemberMajors().get(0).toString());
        }

        if(Objects.equals(memberAccount.getMemberType().toString(), "MENTI")){
            Menti menti = mentiRepository.findById(memberAccount.getId()).get();

            map.put("memberNickname", menti.getNickname());
            map.put("memberMajor", menti.getMemberMajors().get(0).toString());
        }

        return map;
    }
}
