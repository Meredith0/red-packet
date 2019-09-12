package redEnvelope.demo.rabbitmq;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author : Meredith
 * @date : 2019-09-12 14:54
 * @description :
 */
@Component
public class Receiver {

    @RabbitListener (queues = {"red-queue"})
    public void process (String msg) {
        System.out.println("receive "+msg);
    }
}
