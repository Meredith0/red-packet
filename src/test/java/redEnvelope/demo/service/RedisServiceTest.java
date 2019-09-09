package redEnvelope.demo.service;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith (SpringRunner.class)
@SpringBootTest
class RedisServiceTest {

    @Autowired
    RedisService redisService;

    @Test
    public void test01(){
        redisService.init("123", 10, 12345 );
    }
}