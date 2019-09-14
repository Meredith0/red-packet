package redEnvelope.demo.dao;
import static redEnvelope.demo.common.Constants.MAP1;

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
import org.springframework.data.redis.serializer.StringRedisSerializer;
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
        Long count = redisTemplate.opsForList().leftPushAll(Constants.LIST1 + uuid, list);

        log.info("存入redis未分配队列, count=" + count);
        return count == num;
    }

    /**
     * @param uuid undistributed 未分配队列的uuid, 即红包的uuid
     * @param userId 用户id
     */
    public void joinQueue (String uuid, String userId) {

        String listId = Constants.LIST1 + uuid;
        String mapId = MAP1 + uuid;

        List<String> keys = new ArrayList<>();
        keys.add(listId);
        keys.add(mapId);
        ArrayList res = (ArrayList) redisTemplate.execute(getRedisScript,new StringRedisSerializer(),new StringRedisSerializer(),keys,userId);

        if (res.get(0) != null) {

            sender.send2Persistence(res.get(0));
        }

    }

    public String check (String uuid, String userId) {
        String listId = Constants.LIST1 + uuid;

        String mapId = MAP1 + uuid;
        long size = this.redisTemplate.opsForList().size(listId);
        //红包发完了
        if (size == 0) {
            return "-1";
        }
        return (String) this.redisTemplate.opsForHash().get(mapId, userId);

    }

}
