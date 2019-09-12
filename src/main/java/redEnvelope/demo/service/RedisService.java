package redEnvelope.demo.service;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
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

    public static final int GOOD_SIZE = 1000;
    @Autowired
    RedisDao redisDao;
    @Autowired
    Sender sender;
    private int WAIT_QUEUE_SIZE = GOOD_SIZE * 3;
    private AtomicInteger size = new AtomicInteger();
    private volatile boolean isFinish = false;
    private volatile int repeatCount = 0;
    private volatile int requestCount = 0;

    private BlockingQueue<String> requestQueue = new ArrayBlockingQueue(WAIT_QUEUE_SIZE);

    public boolean joinRequestQueue (String id) {
        if (size.getAndIncrement() <= WAIT_QUEUE_SIZE) {
            try {
                requestQueue.put(id);
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }
            return true;
        }
        return false;
    }

    /**
     * @param sum 拆分个数
     * @param money 总金额
     */
    @Async
    public Future<Boolean> init (String uuid, int sum, int money) {

        return new AsyncResult<Boolean>(redisDao.initRedisList(uuid, sum, money));
    }

    //开始抢红包
    public void start (String packetName) {

        String luaPath = "lua/getOne.lua";

    }

    public List getRedEnvelope (String userId, String uuid) {

        sender.send2RedList(userId, uuid);
      //  return redisDao.getRedEnvelope(uuid, userId);

    }


    // public Response check (String id) {
    //     Response response = new Response();
    //     String resultMsg = "";
    //     if (redisDao.isMemberOfSuccessList(id)) {
    //         Packet packet = packetDao.findByTel(id);
    //         resultMsg += "恭喜您抢到一份金额为" + packet.getValue() + "元的红包，现已存入您的账户";
    //         response.setStatus(200);
    //         response.setMessage(resultMsg);
    //     } else if (redisDao.isMemberOfFailedList(id)) {
    //         resultMsg += "很遗憾, 红包被抢光了";
    //         response.setStatus(200);
    //         response.setMessage(resultMsg);
    //     } else {
    //         response.setStatus(201);
    //         response.setMessage("继续等待");
    //     }
    //     return response;
    // }


}
