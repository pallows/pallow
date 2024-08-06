package com.pallow.pallow.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableRedisRepositories
@RequiredArgsConstructor
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        //RedisTemplate 이 사용할 RedisConnectionFactory 를 설정
        //레디스는 기본적으로 바이트 배열로 저장

        // 키와 값을 문자열로 직렬화하도록 설정
        template.setKeySerializer(new StringRedisSerializer());
        // 키를 문자열로 직렬화하도록 설정합니다.
        template.setValueSerializer(new StringRedisSerializer());
        // 값을 문자열로 직렬화하도록 설정합니다.

        // 필요한 경우 해시 키와 값을 문자열로 직렬화하도록 설정
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new StringRedisSerializer());

        template.afterPropertiesSet();
        // 설정이 완료된 후 내부적으로 필요한 초기화 작업을 수행
        return template;
    }
}