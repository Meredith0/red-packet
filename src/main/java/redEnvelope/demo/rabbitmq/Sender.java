package redEnvelope.demo.rabbitmq;
import static redEnvelope.demo.common.Constants.persistence;
import static redEnvelope.demo.common.Constants.redList;

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


    public void send2Persistence (Object  res) {

        rabbitTemplate.convertAndSend(persistence, res);
    }

    public void send2RedList (Object res, String uuid) {

        rabbitTemplate.convertAndSend(redList, res);
    }

}
