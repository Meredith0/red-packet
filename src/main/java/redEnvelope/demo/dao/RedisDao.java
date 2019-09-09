package redEnvelope.demo.dao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import redEnvelope.demo.util.HBgenerate;

/**
 * @author : Meredith
 * @date : 2019-09-06 14:33
 * @description :
 */
@Repository
public class RedisDao {

    @Autowired
    private RedisTemplate redisTemplate;

    public boolean initRedisList (String uuid, int num, int money) {

        List<Integer> data = HBgenerate.divideRedPackage(num, money);
        Long res = redisTemplate.opsForList().leftPush(uuid, data);
        return res != null && res == data.size();

    }

}
