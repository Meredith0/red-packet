package redEnvelope.demo.config;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author : Meredith
 * @date : 2019-08-18 23:35
 * @description : jedis 配置
 */
@Configuration
@PropertySource ("classpath:application.yml")
@Slf4j
public class RedisConfig {

    @Value ("${spring.redis.host}")
    private String host;

    @Value ("${spring.redis.port}")
    private int port;

    @Value ("${spring.redis.timeout}")
    private int timeout;

    @Value ("${spring.redis.jedis.pool.max-idle}")
    private int maxIdle;

    @Value ("${spring.redis.jedis.pool.max-wait}")
    private long maxWaitMillis;
    @Autowired
    private RedisTemplate redisTemplate;

    // @Bean
    // public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
    //     RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
    //     redisTemplate.setConnectionFactory(redisConnectionFactory);
    //
    //     // 使用Jackson2JsonRedisSerialize 替换默认序列化
    //     Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
    //
    //     ObjectMapper objectMapper = new ObjectMapper();
    //     objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
    //     objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
    //
    //     jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
    //
    //     // 设置value的序列化规则和 key的序列化规则
    //     redisTemplate.setKeySerializer(new StringRedisSerializer());
    //     redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
    //     redisTemplate.setHashKeySerializer(new StringRedisSerializer());
    //     redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
    //     redisTemplate.afterPropertiesSet();
    //     return redisTemplate;
    // }

    @Bean
    public JedisPool redisPoolFactory () throws Exception {
        log.info("JedisPool注入成功！！");
        log.info("redis地址：" + host + ":" + port);
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);

        // 是否启用pool的jmx管理功能, 默认true
        jedisPoolConfig.setJmxEnabled(true);
        return new JedisPool(jedisPoolConfig, host, port, timeout);
    }

    @Autowired (required = false)
    public void setRedisTemplate (RedisTemplate redisTemplate) {
        RedisSerializer stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(stringSerializer);
        this.redisTemplate = redisTemplate;
    }

}
