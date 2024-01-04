package com.theZ.dotoring.config;

import org.springframework.http.HttpStatus;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.socket.messaging.StompSubProtocolErrorHandler;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Component
public class StompExceptionHandler extends StompSubProtocolErrorHandler {

    private static final byte[] EMPTY_PAYLOAD = new byte[0];

    public StompExceptionHandler() {
        super();
    }

    /**
     * 클라이언트 메시지 처리 중에 발생한 오류를 처리
     *
     * @param clientMessage 클라이언트 메시지
     * @param ex 발생한 예외
     * @return 오류 메시지를 포함한 Message 객체
     */
    @Override
    public Message<byte[]> handleClientMessageProcessingError(Message<byte[]> clientMessage,
                                                              Throwable ex) {

        final Throwable exception = converterThrowException(ex);

        if (exception instanceof HttpClientErrorException) {
            return handleUnauthorizedException(clientMessage, exception);
        }

        return super.handleClientMessageProcessingError(clientMessage, ex);

    }

    // 메세지 전송간 캐치되는 에러를 핸들링
    private Throwable converterThrowException(final Throwable exception) {
        if (exception instanceof MessageDeliveryException) {
            return exception.getCause();
        }
        return exception;
    }

    // 권한 문제 핸들링
    private Message<byte[]> handleUnauthorizedException(Message<byte[]> clientMessage,
                                                        Throwable ex) {

        return prepareErrorMessage(clientMessage, ex.getMessage(), HttpStatus.UNAUTHORIZED.name());

    }

    /**
     * 오류 메시지를 포함한 Message 객체를 생성
     *
     * @param message 오류 메시지
     * @return 오류 메시지를 포함한 Message 객체
     */
    private Message<byte[]> prepareErrorMessage(final Message<byte[]> clientMessage,
                                                final String message, final String errorCode) {

        final StompHeaderAccessor accessor = StompHeaderAccessor.create(StompCommand.ERROR);
        accessor.setMessage(errorCode);
        accessor.setLeaveMutable(true);

        setReceiptIdForClient(clientMessage, accessor);

        return MessageBuilder.createMessage(
                message != null ? message.getBytes(StandardCharsets.UTF_8) : EMPTY_PAYLOAD,
                accessor.getMessageHeaders()
        );
    }

    // accessor 헤더에 receiptId 저장하기
    private void setReceiptIdForClient(final Message<byte[]> clientMessage,
                                       final StompHeaderAccessor accessor) {

        if (Objects.isNull(clientMessage)) {
            return;
        }

        final StompHeaderAccessor clientHeaderAccessor = MessageHeaderAccessor.getAccessor(
                clientMessage, StompHeaderAccessor.class);

        final String receiptId =
                Objects.isNull(clientHeaderAccessor) ? null : clientHeaderAccessor.getReceipt();

        if (receiptId != null) {
            accessor.setReceiptId(receiptId);
        }
    }

    //
    @Override
    protected Message<byte[]> handleInternal(StompHeaderAccessor errorHeaderAccessor,
                                             byte[] errorPayload, Throwable cause, StompHeaderAccessor clientHeaderAccessor) {

        return MessageBuilder.createMessage(errorPayload, errorHeaderAccessor.getMessageHeaders());
    }
}
