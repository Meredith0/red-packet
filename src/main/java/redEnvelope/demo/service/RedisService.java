package redEnvelope.demo.service;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redEnvelope.demo.dao.RedisDao;

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

    public static final int GOOD_SIZE = 1000;

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
     *     拆分红包并放进redis队列
     */
    public Boolean init (String uuid, int sum, int money) {

        return redisDao.initRedisList(uuid, sum, money);
    }

    //开始抢红包
    public void start (String packetName) {

        String luaPath = "lua/getOne.lua";

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

    // public void start(String packetName) {
    //     log.info("活动开始");
    //     log.info("缓存中红包个数为：" + redisDao.getPacketsList().size(packetName));
    //     isFinish = false;
    //     while (!isFinish) {
    //         if (!requestQueue.isEmpty()) {
    //             String tel = requestQueue.poll();
    //             threadPoolTaskExecutor.execute(() -> {
    //                 ++requestCount;
    //                 if (!redisDao.isMemberOfSuccessList(tel)) {
    //                     Object packetId = redisDao.getPacketsList().leftPop(packetName);
    //                     if (StringUtils.isEmpty(packetId)) {
    //                         //                                这里没有直接设置isFinish为true，是因为可能同时存在其他线程虽然从redis得到了packetId，
    //                         //                              但由于异常的原因，导致packetId归还到redis里去了，这样其他用户可以再次请求
    //                         logger.info(tel + "没有抢到红包");
    //                         redisDao.addToFailedList(tel);
    //                     } else {
    //                         try {
    //                             int result = packetDao.bindRedPacket(packetId.toString(), tel);
    //                             if (result >= 0) {
    //                                 logger.info(tel + "抢到红包！");
    //                                 redisDao.addToSuccessList(tel);
    //                             } else {
    //                                 logger.info(tel + "已经抢到过红包");
    //                                 throw new Exception();
    //                             }
    //                         } catch (Exception e) {
    //                             //                                 如果重复插入手机号会出现主键重复异常，这时候恢复库存；
    //                             //                                 由于前端的控制，理论上不会重复提交手机号，但为了防止意外或者恶意请求的发升，因此try catch
    //                             //                                对于拦截重复手机号，后端做了3层拦截
    //                             //                                第一层：redisDao.isMemberOfSuccessList；
    //                             //                                第二层：存储过程中根据手机号查询i_order表是否已经存在数据
    //                             //
    //                             第三层：存储过程中，由于两个线程同时请求，有可能都会从i_order表中查不到数据，最终都会执行插入操作
    //                             //                                         但由于tel是不允许重复的，那么就依靠重复异常抛出错误了
    //                             logger.error(tel + "抢红包出现了异常，现在恢复库存");
    //                             ++repeatCount;
    //                             redisDao.getPacketsList().rightPush(packetName, packetId);
    //                         }
    //                     }
    //                 } else {
    //                     ++repeatCount;
    //                     logger.warn(tel + "已经抢成功过一次！");
    //                 }
    //             });
    //         }
    //     }
    // }
}
