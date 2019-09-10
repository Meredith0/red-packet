package redEnvelope.demo.dao;
import com.alibaba.fastjson.JSON;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Repository;
import redEnvelope.demo.model.RedEnvelope;

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

    private DefaultRedisScript<List> getRedisScript;

    @PostConstruct
    public void init () {
        getRedisScript = new DefaultRedisScript<List>();
        getRedisScript.setResultType(List.class);
        getRedisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("lua/getOne.lua")));
    }

    public boolean initRedisList (String uuid, int num, int money) {

        log.info("初始化红包进redis未分配队列...");
        int count=0;
        Integer restAmount = money;
        Integer restPeopleNum = num;
        for (int i = 0; i < num - 1; i++) {
            //随机范围：[1，剩余人均金额的两倍)，左闭右开
            int amount = random.nextInt(restAmount / restPeopleNum * 2 - 1) + 1;
            restAmount -= amount;
            restPeopleNum--;
            RedEnvelope o = new RedEnvelope(uuid, amount);

            count+=redisTemplate.opsForList().leftPush(uuid, JSON.toJSONString(o));
        }
        RedEnvelope o = new RedEnvelope(uuid, restAmount);
        count+=redisTemplate.opsForList().leftPush(uuid,  JSON.toJSONString(o));


        log.info("存入redis未分配队列, count="+ count);
        return  count == num;
    }

    /**
     * @param und8ed undistributed 未分配队列的uuid, 即红包的uuid
     * @param userId 用户id
     */
    public List getRedEnvelope (String und8ed,String userId) {

        //d8ed=distributed 已分配红包的uuid
        String d8ed = UUID.randomUUID().toString();
        d8ed = d8ed.replace("-", "");

        //去重set的uuid
        String distinct = UUID.randomUUID().toString();
        distinct = distinct.replace("-", "");

        List<String> keys = new ArrayList<>();
        keys.add(und8ed);
        keys.add(d8ed);
        keys.add(distinct);
        keys.add(userId);

        ArrayList res = (ArrayList) redisTemplate.execute(getRedisScript, keys);
        System.out.println(res);
        return res;
    }

}
