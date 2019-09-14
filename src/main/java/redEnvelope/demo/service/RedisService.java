package redEnvelope.demo.service;
import java.util.concurrent.Future;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import redEnvelope.demo.dao.RedisDao;
import redEnvelope.demo.rabbitmq.Sender;

/**
 * @author : Meredith
 * @date : 2019-09-05 16:34
 * @description :
 */
@Service
@Slf4j
public class RedisService {

    @Autowired
    RedisDao redisDao;
    @Autowired
    Sender sender;
    /**
     * @param sum 拆分个数
     * @param money 总金额
     */
    @Async
    public Future<Boolean> init (String uuid, int sum, int money) {

        return new AsyncResult<Boolean>(redisDao.initRedisList(uuid, sum, money));
    }

    /**
     * @param userId 用户ID
     * @param uuid  红包id
     * 进入MQ
     */
    public void queueing (String userId, String uuid) {

        sender.send2RedList(userId, uuid);
    }

    public String check (String uuid, String userId) {
        
         return redisDao.check(uuid, userId);
    }

}
