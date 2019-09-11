package redEnvelope.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisCluster;

/**
 * @author : Meredith
 * @date : 2019-08-18 23:35
 * @description : redis集群配置
 */

@Configuration
@ConditionalOnClass (JedisCluster.class)
public class RedisConfig {

    private RedisTemplate redisTemplate;

    @Autowired (required = false)
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        RedisSerializer stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(stringSerializer);
        this.redisTemplate = redisTemplate;
    }

    /**
     * 设置数据存入redis 的序列化方式
     * redisTemplate序列化默认使用的jdkSerializeable
     * 存储二进制字节码，导致key会出现乱码，所以自定义序列化类
     */
    // @Bean
    // public RedisTemplate<Object, Object> redisTemplate (RedisConnectionFactory redisConnectionFactory) {
    //     RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
    //     redisTemplate.setConnectionFactory(redisConnectionFactory);
    //     Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
    //     ObjectMapper objectMapper = new ObjectMapper();
    //     objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
    //     objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
    //     jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
    //
    //     redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
    //     redisTemplate.setKeySerializer(new StringRedisSerializer());
    //     redisTemplate.setHashKeySerializer(new StringRedisSerializer());
    //     redisTemplate.afterPropertiesSet();
    //
    //     return redisTemplate;
    // }

}
