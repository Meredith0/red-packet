package redEnvelope.demo.rabbitmq;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redEnvelope.demo.dao.RedisDao;

/**
 * @author : Meredith
 * @date : 2019-09-12 16:31
 * @description : 转发mq消息到线程池
 */
@Component
public class MqMsgDispatcher {

    public static ExecutorService msgHandleService = Executors.newFixedThreadPool(40);



    static {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            msgHandleService.shutdown();
        }));
    }

    public static void doDispatch (String message) {
        msgHandleService.execute(new MessageHandleTask(message));
    }

    private static class MessageHandleTask implements Runnable {
        @Autowired
        RedisDao redisDao;
        String message;

        public MessageHandleTask (String message) {
            this.message = message;
        }

        @Override
        public void run () {
            long start = System.currentTimeMillis();
            String tName = Thread.currentThread().getName();
            System.out.println(tName + " [x] Received '" + message + "'");
            try {
                Thread.sleep(1);
                //todo 线程池消费方法
               // redisDao.getRedEnvelope(uuid, userId);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            long end = System.currentTimeMillis();
            System.out.println(tName + " cost " + (end - start));
        }

    }

}
