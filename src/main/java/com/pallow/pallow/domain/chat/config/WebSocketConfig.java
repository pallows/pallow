package com.pallow.pallow.domain.chat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

//    private static final Logger logger = LoggerFactory.getLogger(WebSocketConfig.class);

//    private final JwtProvider jwtProvider;
//    private final UserDetailsService userDetailsService;

//    public WebSocketConfig(JwtProvider jwtProvider, UserDetailsService userDetailsService) {
//        this.jwtProvider = jwtProvider;
//        this.userDetailsService = userDetailsService;
//    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic");
        registry.setApplicationDestinationPrefixes("/app");
    }

//    @Override
//    public void configureClientInboundChannel(ChannelRegistration registration) {
//        registration.interceptors(new ChannelInterceptor() {
//            @Override
//            public Message<?> preSend(Message<?> message, MessageChannel channel) {
//                StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
//                if (StompCommand.CONNECT.equals(accessor.getCommand())) {
//                    String token = accessor.getFirstNativeHeader("Authorization");
//                    logger.info("Received token: " + token);
//                    if (token != null && token.startsWith("Bearer ")) {
//                        token = token.substring(7);
//                        try {
//                            String username = jwtProvider.getUserNameFromJwtToken(token);
//                            logger.info("Extracted username: " + username);
//                            if (username != null) {
//                                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//                                UsernamePasswordAuthenticationToken authentication =
//                                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//                                SecurityContextHolder.getContext().setAuthentication(authentication);
//                                accessor.setUser(authentication);
//                                logger.info("Authentication set for user: " + username);
//                            }
//                        } catch (Exception e) {
//                            logger.error("Error processing token", e);
//                        }
//                    }
//                }
//                return message;
//            }
//        });
//    }
}