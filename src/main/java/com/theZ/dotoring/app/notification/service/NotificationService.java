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
import com.theZ.dotoring.common.MessageCode;
import com.theZ.dotoring.exception.NotFoundMemberException;
import com.theZ.dotoring.exception.notificationException.DuplicateParticipationException;
import com.theZ.dotoring.exception.notificationException.NotAuthorNotificationException;
import com.theZ.dotoring.exception.notificationException.NotFoundNotificationException;
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

    private static final String MEMBER_NICKNAME = "memberNickname";

    private static final String MEMBER_MAJOR = "memberMajor";

    @Transactional
    public NotificationSaveResDTO saveNotification(MemberAccount memberAccount, NotificationReqDTO reqDTO){

        Map<String, String> map = getMemberNicknameAndMajor(memberAccount);

        Notification notification = notificationRepository.save(Notification.of(reqDTO, map.get(MEMBER_NICKNAME)));

        return NotificationSaveResDTO.of(notification, map.get(MEMBER_NICKNAME), map.get(MEMBER_MAJOR));
    }

    @Transactional
    public NotificationUpdateResDTO updateNotification(MemberAccount memberAccount, Long notificationId, NotificationReqDTO reqDTO) throws NotFoundNotificationException, NotAuthorNotificationException {

        Map<String, String> map = getMemberNicknameAndMajor(memberAccount);

        Notification notification = notificationRepository.findById(notificationId).orElseThrow(
                () -> new NotFoundNotificationException(MessageCode.NOTIFICATION_NOT_FOUND));

        // 유저 본인이 아니라면 업데이트를 할 수 없게 예외 발생
        if (!Objects.equals(notification.getAuthor(), map.get(MEMBER_NICKNAME))){
            throw new NotAuthorNotificationException(MessageCode.NOT_AUTHOR_NOTIFICATION);
        }

        notification.updateNotification(reqDTO);

        return NotificationUpdateResDTO.of(notification, map.get(MEMBER_NICKNAME), map.get(MEMBER_MAJOR));
    }

    @Transactional(readOnly = true)
    public NotificationResDTO getNotificationByFilter(String title, String goal, boolean isClose){
        List<Notification> notifications = notificationRepositoryImpl.getNotificationByFilter(title, goal, isClose);

        return new NotificationResDTO(notifications);
    }

    @Transactional(readOnly = true)
    public NotificationDetailDTO getNotification(MemberAccount memberAccount, Long notificationId) throws NotFoundNotificationException {
        Notification notification = notificationRepository.findById(notificationId).orElseThrow(
                () -> new NotFoundNotificationException(MessageCode.NOTIFICATION_NOT_FOUND));

        Map<String, String> map = getMemberNicknameAndMajor(memberAccount);

        return NotificationDetailDTO.of(notification, map.get(MEMBER_MAJOR), map.get(MEMBER_NICKNAME));
    }

    @Transactional
    public NotificationStatusDTO makeStatusToEnd(MemberAccount memberAccount, Long notificationId) throws NotFoundNotificationException, NotAuthorNotificationException{

        Map<String, String> map = getMemberNicknameAndMajor(memberAccount);

        Notification notification = notificationRepository.findById(notificationId).orElseThrow(
                () -> new NotFoundNotificationException(MessageCode.NOTIFICATION_NOT_FOUND));

        // 유저 본인이 아니라면 업데이트를 할 수 없게 예외 발생
        if (!Objects.equals(notification.getAuthor(), map.get(MEMBER_NICKNAME))){
            throw new NotAuthorNotificationException(MessageCode.NOT_AUTHOR_NOTIFICATION);
        }

        notification.updateStatusToIsClose();

        return NotificationStatusDTO.from(notification);
    }

    @Transactional
    public void joinNotification(MemberAccount memberAccount, Long notificationId, NotificationParticipateReqDTO notificationParticipateReqDTO) throws NotFoundNotificationException, DuplicateParticipationException {

        Map<String, String> map = getMemberNicknameAndMajor(memberAccount);

        Notification notification = notificationRepository.findById(notificationId).orElseThrow(() -> new NotFoundNotificationException(MessageCode.NOTIFICATION_NOT_FOUND));

        // 이미 참여 신청한 유저인지 확인
        if (!notification.getParticipations().contains(map.get(MEMBER_NICKNAME))){
            notification.getParticipations().add(map.get(MEMBER_NICKNAME));
        } else {
            throw new DuplicateParticipationException(MessageCode.DUPLICATE_PARTICIPATION_NOTIFICATION);
        }

        // 글 작성자에게 해당 유저가 보내는 채팅 메시지 보내기 -> 합성 사용
        // 현재 유저가 멘토인지 아닌지 분기해서 보내주어야 함.
        if (memberAccount.getMemberType().toString().equals("MENTO")){
            boolean isMentoChatSender = false;
            messageService.sendJoinNotificationMessage(map.get(MEMBER_NICKNAME), notification.getAuthor(), isMentoChatSender, notificationParticipateReqDTO.getParticipationForm());
        }

        if (memberAccount.getMemberType().toString().equals("MENTI")){
            boolean isMentoChatSender = true;
            messageService.sendJoinNotificationMessage(notification.getAuthor(), map.get(MEMBER_NICKNAME), isMentoChatSender, notificationParticipateReqDTO.getParticipationForm());
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
            Mento mento = mentoRepository.findById(memberAccount.getId()).orElseThrow(
                    () -> new NotFoundMemberException(MessageCode.MEMBER_NOT_FOUND)
            );

            map.put(MEMBER_NICKNAME, mento.getNickname());
            map.put(MEMBER_MAJOR, mento.getMemberMajors().get(0).toString());
        }

        if(Objects.equals(memberAccount.getMemberType().toString(), "MENTI")){
            Menti menti = mentiRepository.findById(memberAccount.getId()).orElseThrow(
                    () -> new NotFoundMemberException(MessageCode.MEMBER_NOT_FOUND)
            );

            map.put(MEMBER_NICKNAME, menti.getNickname());
            map.put(MEMBER_MAJOR, menti.getMemberMajors().get(0).toString());
        }

        return map;
    }
}
