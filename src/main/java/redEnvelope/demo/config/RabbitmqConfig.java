package redEnvelope.demo.config;
import org.springframework.context.annotation.Configuration;

/**
 * @author : Meredith
 * @date : 2019-09-12 22:05
 * @description :
 */
@Configuration
public class RabbitmqConfig {

    // //TODO：基本消息模型构建
    // @Bean
    // public DirectExchange basicExchange(){
    //     return new DirectExchange(env.getProperty("basic.info.mq.exchange.name"), true,false);
    // }
    //
    // @Bean(name = "basicQueue")
    // public Queue basicQueue(){
    //     return new Queue(env.getProperty("basic.info.mq.queue.name"), true);
    // }
    //
    // @Bean
    // public Binding basicBinding(){
    //     return BindingBuilder.bind(basicQueue()).to(basicExchange()).with(env.getProperty("basic.info.mq.routing.key.name"));
    // }


}
