package com.msm.admin.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.msm.admin.modules.common.service.impl.RedisService;
import com.msm.admin.utils.JsonUtils;
import com.msm.admin.utils.SubjectUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.lang.reflect.Method;
import java.time.Duration;

/**
 * @author quavario@gmail.com
 * @date 2019/4/24 18:11
 */
@Configuration
@ConditionalOnClass(RedisTemplate.class)
@EnableCaching
public class RedisAutoConfiguration {
    /*class RedisConfig {

        RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
            RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
            redisTemplate.setConnectionFactory(factory);

            Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer  = new Jackson2JsonRedisSerializer<>(Object.class);
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
            objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
            jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

            StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
            redisTemplate.setKeySerializer(stringRedisSerializer);

            // hash的key也采用String的序列化方式
            redisTemplate.setHashKeySerializer(stringRedisSerializer);

            // value序列化方式采用jackson
            redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
            // hash的value序列化方式采用jackson
            redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
            redisTemplate.afterPropertiesSet();
            return redisTemplate;
        }
    }*/

    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory factory) {

        // 1.创建 redisTemplate 模版
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        // 2.关联 redisConnectionFactory
        redisTemplate.setConnectionFactory(factory);

        // 3.创建 序列化类
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer  = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();

        // 4.设置可见度
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);

        // 5.启动默认的类型
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);

        // 6.序列化类，对象映射设置
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);


        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

        redisTemplate.setKeySerializer(stringRedisSerializer);
        // value序列化方式采用jackson
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);


        // hash的key也采用String的序列化方式
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        // hash的value序列化方式采用jackson
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);


        redisTemplate.afterPropertiesSet();

        return redisTemplate;

    }

    @SuppressWarnings({"unchecked"})
    @Bean("cacheManager")
    @Primary
    public CacheManager cacheManager(RedisConnectionFactory factory) {
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);


        // 配置序列化
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(5))
                .computePrefixWith(name -> name + ":")
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer));

        RedisCacheManager cacheManager = RedisCacheManager.builder(factory)
                .cacheDefaults(config)
                .build();
        return cacheManager;
    }

    @SuppressWarnings({"unchecked"})
    @Bean("dictCacheManager")
    public CacheManager dictCacheManager(RedisConnectionFactory factory) {
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);


        // 配置序列化
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofDays(10))
                .computePrefixWith(name -> name + ":")
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer));

        RedisCacheManager cacheManager = RedisCacheManager.builder(factory)
                .cacheDefaults(config)
                .build();
        return cacheManager;
    }

    /**
     * 自定义key生成器
     */
    @Bean("cacheKeyGenerator")
    public KeyGenerator keyGenerator() {
        class CacheKeyGenerator implements KeyGenerator {

            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();

                String methodName = method.getName();

                sb.append(methodName);

                sb.append(":hashcode=");

                if (params != null && params.length > 0) {
                    for (Object arg : params) {
                        sb.append(Math.abs(JsonUtils.objectToJson(arg).hashCode() & Integer.MAX_VALUE));
                    }

                }
                return sb.toString();
            }
        }

        return new CacheKeyGenerator();
    }

//    @Bean("adminKeyGenerator")
//    public KeyGenerator adminKeyGenerator() {
//        String userId = SubjectUtils.getCurrentUser().getId();
//        class CacheKeyGenerator implements KeyGenerator {
//
//            @Override
//            public Object generate(Object target, Method method, Object... params) {
//                StringBuilder sb = new StringBuilder();
//
//                sb.append("user:").append(userId);
//                String methodName = method.getName();
//
//                sb.append(":").append(methodName);
//
//                sb.append(":hashcode=");
//
//                if (params != null && params.length > 0) {
//                    for (Object arg : params) {
//                        sb.append(Math.abs(JsonUtils.objectToJson(arg).hashCode() & Integer.MAX_VALUE));
//                    }
//
//                }
//                return sb.toString();
//            }
//        }
//
//        return new CacheKeyGenerator();
//    }

    @Bean
    public RedisService redisService() {
        return new RedisService();
    }


//    @Bean
//    public CacheAspect cacheAspect() {
//        return new CacheAspect();
//    }

//    @Bean
//    public SpelParser spelParser() {
//        return new SpelParser();
//    }
}
