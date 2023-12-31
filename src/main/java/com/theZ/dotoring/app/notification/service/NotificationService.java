package com.theZ.dotoring.app.notification.service;

import com.theZ.dotoring.app.chat.service.MessageService;
import com.theZ.dotoring.app.memberAccount.model.MemberAccount;
import com.theZ.dotoring.app.menti.model.Menti;
import com.theZ.dotoring.app.menti.repository.MentiRepository;
import com.theZ.dotoring.app.mento.model.Mento;
import com.theZ.dotoring.app.mento.repository.MentoRepository;
import com.theZ.dotoring.app.notification.dto.*;
import com.theZ.dotoring.app.notification.model.Notification;
import com.theZ.dotoring.app.notification.repository.NotificationRepository;
import com.theZ.dotoring.app.notification.repository.NotificationRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final MessageService messageService;

    private final NotificationRepository notificationRepository;

    private final NotificationRepositoryImpl notificationRepositoryImpl;

    private final MentoRepository mentoRepository;

    private final MentiRepository mentiRepository;

    @Transactional
    public NotificationSaveResDTO saveNotification(MemberAccount memberAccount, NotificationReqDTO reqDTO){

        Map<String, String> map = getMemberNicknameAndMajor(memberAccount);

        Notification notification = notificationRepository.save(Notification.of(reqDTO, map.get("memberNickname")));

        return NotificationSaveResDTO.of(notification, map.get("memberNickname"), map.get("memberMajor"));
    }

    @Transactional
    public NotificationUpdateResDTO updateNotification(MemberAccount memberAccount, Long notificationId, NotificationReqDTO reqDTO){

        Map<String, String> map = getMemberNicknameAndMajor(memberAccount);

        Notification notification = notificationRepository.findById(notificationId).orElseThrow(() -> new RuntimeException("notification이 존재하지 않습니다."));

        // 유저 본인이 아니라면 업데이트를 할 수 없게 예외 발생
        if (!Objects.equals(notification.getAuthor(), map.get("memberNickname"))){
            throw new RuntimeException("글 작성한 본인만 수정할 수 있습니다.");
        }

        notification.updateNotification(reqDTO);

        return NotificationUpdateResDTO.of(notification, map.get("memberNickname"), map.get("memberMajor"));
    }

    @Transactional(readOnly = true)
    public NotificationResDTO getNotificationByFilter(String title, String goal, boolean isClose){
        List<Notification> notifications = notificationRepositoryImpl.getNotificationByFilter(title, goal, isClose);

        return new NotificationResDTO(notifications);
    }

    @Transactional(readOnly = true)
    public NotificationDetailDTO getNotification(MemberAccount memberAccount, Long notificationId){
        Notification notification = notificationRepository.findById(notificationId).orElseThrow(() -> new RuntimeException("해당 지원 공고가 존재하지 않습니다."));

        Map<String, String> map = getMemberNicknameAndMajor(memberAccount);

        return NotificationDetailDTO.of(notification, map.get("memberMajor"), map.get("memberNickname"));
    }

    @Transactional
    public NotificationStatusDTO makeStatusToEnd(MemberAccount memberAccount, Long notificationId){

        Map<String, String> map = getMemberNicknameAndMajor(memberAccount);

        Notification notification = notificationRepository.findById(notificationId).orElseThrow(() -> new RuntimeException("해당 지원 공고가 존재하지 않습니다."));

        // 유저 본인이 아니라면 업데이트를 할 수 없게 예외 발생
        if (!Objects.equals(notification.getAuthor(), map.get("memberNickname"))){
            throw new RuntimeException("글 작성한 본인만 게시글을 마감할 수 있습니다..");
        }

        notification.updateStatusToIsClose();

        return NotificationStatusDTO.from(notification);
    }

    // todo: 지원자 수를 응답내는 것은 어떤가?
    @Transactional
    public void joinNotification(MemberAccount memberAccount, Long notificationId, NotificationParticipateReqDTO notificationParticipateReqDTO){

        Map<String, String> map = getMemberNicknameAndMajor(memberAccount);

        Notification notification = notificationRepository.findById(notificationId).orElseThrow(() -> new RuntimeException("해당 지원 공고가 존재하지 않습니다."));

        // 이미 참여 신청한 유저인지 확인
        if (!notification.getParticipations().contains(map.get("memberNickname"))){
            notification.getParticipations().add(map.get("memberNickname"));
        } else {
            throw new RuntimeException("이미 참여하기를 누른 유저입니다.");
        }

        // 글 작성자에게 해당 유저가 보내는 채팅 메시지 보내기 -> 합성 사용
        // 현재 유저가 멘토인지 아닌지 분기해서 보내주어야 함.
        if (memberAccount.getMemberType().toString().equals("MENTO")){
            boolean isMentoChatSender = false;
            messageService.sendJoinNotificationMessage(map.get("memberNickname"), notification.getAuthor(), isMentoChatSender, notificationParticipateReqDTO.getParticipationForm());
        }

        if (memberAccount.getMemberType().toString().equals("MENTI")){
            boolean isMentoChatSender = true;
            messageService.sendJoinNotificationMessage(notification.getAuthor(), map.get("memberNickname"), isMentoChatSender, notificationParticipateReqDTO.getParticipationForm());
        }

        // 더티 체킹으로 지원자 수 증대
        notification.addCurParticipation();

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
