package com.theZ.dotoring.config;

import com.theZ.dotoring.app.chat.handler.ChatPreHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker // Stomp 프로토콜을 사용하도록 정의
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final ChatPreHandler chatPreHandler;

    private final StompExceptionHandler stompExceptionHandler;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) { // STOMP에서 사용하는 메시지 브로커를 설정하는 메소드이다.
        registry.setApplicationDestinationPrefixes("/app"); // 메시지 보낼 때 관련 경로 설정이다.
        registry.enableSimpleBroker("/sub"); // 메세지를 받을 때, 경로를 설정해주는 함수이다.
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.setErrorHandler(stompExceptionHandler)
                .addEndpoint("/ws")
                .setAllowedOriginPatterns("*");
                //.withSockJS(); //현재 Apic 사용시 충돌난다.
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(chatPreHandler);
    }
}
