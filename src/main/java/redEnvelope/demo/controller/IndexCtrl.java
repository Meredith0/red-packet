package redEnvelope.demo.controller;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import redEnvelope.demo.model.RedLog;
import redEnvelope.demo.service.LogService;
import redEnvelope.demo.service.RedisService;

/**
 * @author : Meredith
 * @date : 2019-09-04 15:38
 * @description :
 */
@RestController
@Slf4j
public class IndexCtrl {

    @Autowired
    RedisService redisService;

    @Autowired
    LogService logService;

    /**
     * @param userId 用户Id
     * @param sum 红包个数
     * @param money 红包总金额
     * @return 红包的 uuid / failed
     */
    @RequestMapping (method = RequestMethod.GET, value = "/red-env")
    public String giveRedEnvelope (int userId, int sum, int money)
        throws InterruptedException, TimeoutException, ExecutionException {
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replace("-", "");
        //  uuid = "{" + uuid + "}";

        Future<Boolean> init = redisService.init(uuid, sum, money);
        Future<Boolean> log = logService.addLog(new RedLog(userId+"", uuid, sum, money));

        while (true) {
            if (init.isDone() && log.isDone()) {
                return uuid;
            }
            Thread.sleep(10);
        }
    }

    /**
     * @param uuid 红包的id
     * @return 抢红包, 此方法仅登记抢红包的用户, 放进请求队列, 之后返回一个id供前端轮询抢红包结果
     */
    @RequestMapping (method = RequestMethod.GET, value = "/get/{uuid}")
    public String getRedEnvelope ( @PathVariable String uuid) {
        //用户id为随机数
        int num = new Random().nextInt(9000000);
        String id = num + "";
        // if (redisService.getRedEnvelope(id,uuid)) {
        //     return id;
        // }
        // return "failed";

        return redisService.getRedEnvelope(id, uuid).toString();
    }

    //前端定时请求查看是否抢到红包
    // @GetMapping ("/check")
    // public String check (@RequestParam ("id") String id) {
    //     return redisService.check(id).getMessage();
    // }

}
