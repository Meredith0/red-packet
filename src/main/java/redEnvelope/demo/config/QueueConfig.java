package redEnvelope.demo.config;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : Meredith
 * @date : 2019-09-12 14:43
 * @description :
 */
@Configuration
public class QueueConfig {

    @Bean
    public Queue createQueue () {
        return new Queue("red-queue");
    }
}
