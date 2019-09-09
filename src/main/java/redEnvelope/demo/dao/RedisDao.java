package redEnvelope.demo.dao;
import java.util.List;
import java.util.stream.Collectors;
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
    private RedisTemplate<String, String[]> redisTemplate;

    public boolean initRedisList (String uuid, int num, int money) {

        List<Integer> list = HBgenerate.getPackage(num, money);
        List<String> collect = list.stream().map(Object :: toString).collect(Collectors.toList());

        Long res = redisTemplate.opsForList().leftPush(uuid, collect.toArray(new String[]{}));

        return res != null && res == list.size();
    }

}
