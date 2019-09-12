package redEnvelope.demo.dao;
import com.alibaba.fastjson.JSON;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Repository;
import redEnvelope.demo.common.Constants;
import redEnvelope.demo.model.RedEnvelope;
import redEnvelope.demo.rabbitmq.Sender;

/**
 * @author : Meredith
 * @date : 2019-09-06 14:33
 * @description :
 */
@Repository
@Slf4j
public class RedisDao {

    private static final Random random = new Random();

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private PersistenceDao dao;

    @Autowired
    Sender sender;
    private DefaultRedisScript<List> getRedisScript;

    @PostConstruct
    public void init () {
        getRedisScript = new DefaultRedisScript<List>();
        getRedisScript.setResultType(List.class);
        getRedisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("lua/getOne.lua")));
    }

    public boolean initRedisList (String uuid, int num, int money) {

        log.info("初始化红包进redis未分配队列...");
        Integer restAmount = money;
        Integer restPeopleNum = num;
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < num - 1; i++) {
            //随机范围：[1，剩余人均金额的两倍)，左闭右开
            int amount = random.nextInt(restAmount / restPeopleNum * 2 - 1) + 1;
            restAmount -= amount;
            restPeopleNum--;
            RedEnvelope o = new RedEnvelope(uuid, amount);

            list.add(JSON.toJSONString(o));
        }
        RedEnvelope o = new RedEnvelope(uuid, restAmount);
        list.add(JSON.toJSONString(o));
        Long count = redisTemplate.opsForList().leftPushAll(Constants.UNDISTURBED_LIST + uuid, list);

        log.info("存入redis未分配队列, count=" + count);
        return count == num;
    }

    /**
     * @param uuid undistributed 未分配队列的uuid, 即红包的uuid
     * @param userId 用户id
     */
    public List getRedEnvelope (String uuid, String userId) {

        String undisturbed = Constants.UNDISTURBED_LIST + uuid;
        String disturbed = Constants.DISTURBED_LIST + uuid;
        String distinct = Constants.DISTINCT_SET + uuid;
        userId = Constants.USER_PREFIX + userId;

        List<String> keys = new ArrayList<>();
        keys.add(undisturbed);
        keys.add(disturbed);
        keys.add(distinct);
        keys.add(userId);

        ArrayList res = (ArrayList) redisTemplate.execute(getRedisScript, keys);

        if (res != null) {
            sender.send2Persistence(res.get(0));
        }

        return res;
    }


}
