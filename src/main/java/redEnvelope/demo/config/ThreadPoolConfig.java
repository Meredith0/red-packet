package redEnvelope.demo.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author : Meredith
 * @date : 2019-09-08 22:48
 * @description : 线程池配置
 */
@Configuration
public class ThreadPoolConfig {

    @Bean
    public ThreadPoolTaskExecutor threadPoolTaskExecutor(){
        //采用默认的线程拒绝策略，处理出错时直接抛出RejectedExecutionException
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(40);
        threadPoolTaskExecutor.setMaxPoolSize(160);
        threadPoolTaskExecutor.setKeepAliveSeconds(300);
        threadPoolTaskExecutor.setQueueCapacity(20);
        return threadPoolTaskExecutor;
    }
}
