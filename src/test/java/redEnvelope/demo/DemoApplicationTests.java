package redEnvelope.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.JedisPool;

@RunWith (SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Autowired
    private JedisPool jedis;

    @Test
    public void contextLoads () {
    }

    @Test
    public void testConn(){
        jedis.getResource().set("1", "1");
        jedis.getResource().set("2", "2");
        System.out.println(jedis.getResource().get("1"));

    }
}
