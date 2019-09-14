package redEnvelope.demo.rabbitmq;
import static redEnvelope.demo.common.Constants.persistence;
import static redEnvelope.demo.common.Constants.redList;

import com.alibaba.fastjson.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redEnvelope.demo.dao.PersistenceDao;
import redEnvelope.demo.dao.RedisDao;
import redEnvelope.demo.model.RedEnvelope;

/**
 * @author : Meredith
 * @date : 2019-09-12 14:54
 * @description :
 */
@Component
public class Receiver {

    @Autowired
    PersistenceDao dao;

    @Autowired
    RedisDao redisDao;

    @RabbitListener (queues = persistence)
    public void processPersistence (String msg) {
        JSONObject jsonObject = JSONObject.parseObject(msg);
        RedEnvelope redEnvelope = JSONObject.parseObject(String.valueOf(jsonObject), RedEnvelope.class);
        dao.insert(redEnvelope);
    }

    @RabbitListener (queues = redList)
    public void processGetRed (String msg) {
        String[] res = msg.split("#");
        redisDao.joinQueue(res[0], res[1]);


    }

}
