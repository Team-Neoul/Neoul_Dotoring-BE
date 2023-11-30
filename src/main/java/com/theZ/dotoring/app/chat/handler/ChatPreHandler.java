package com.theZ.dotoring.app.chat.handler;

import com.theZ.dotoring.app.auth.TokenStatus;
import com.theZ.dotoring.app.auth.model.Token;
import com.theZ.dotoring.app.auth.service.MemberDetailService;
import io.jsonwebtoken.MalformedJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

@Configuration
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class ChatPreHandler implements ChannelInterceptor {

    private final Token token;

    private final MemberDetailService memberDetailService;


    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor headerAccessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        String authorizationHeader = String.valueOf(headerAccessor.getNativeHeader("Authorization"));

        StompCommand command = headerAccessor.getCommand();

        if (command.equals(StompCommand.CONNECT)) {
            if (authorizationHeader == null) {
                throw new MalformedJwtException("jwt");
            }
            String accessToken = authorizationHeader.replaceAll("[\\[\\]]", "");

            // token 분리
            try {
                if (Token.isValidTokenInStomp(accessToken).equals(TokenStatus.VALID)) {
                    Authentication authentication = memberDetailService.getAuthentication(accessToken);
                    this.setAuthentication(authentication, message, headerAccessor);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

        if (command.equals(StompCommand.ERROR)) {
            throw new MessageDeliveryException("error");
        }

        return message;
    }

    private void setAuthentication(Authentication authentication, Message<?> message, StompHeaderAccessor headerAccessor) {
        SecurityContextHolder.getContext().setAuthentication(authentication);
        headerAccessor.setUser(authentication);
    }
}

