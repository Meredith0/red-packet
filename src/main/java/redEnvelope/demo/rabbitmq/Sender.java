package redEnvelope.demo.rabbitmq;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author : Meredith
 * @date : 2019-09-12 14:40
 * @description :
 */

@Component
public class Sender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send (String msg) {
        this.rabbitTemplate.convertAndSend("red-queue",msg);

    }

}
