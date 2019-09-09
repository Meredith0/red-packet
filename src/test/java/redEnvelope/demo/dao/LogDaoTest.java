package redEnvelope.demo.dao;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redEnvelope.demo.model.RedLog;

@RunWith (SpringRunner.class)
@SpringBootTest

class LogDaoTest {

    @Autowired
    LogDao logDao;

    @Test
    public void test01 () {
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replace("-", "");

        RedLog redLog = new RedLog(123, uuid, 10, 20);
             logDao.insertLog(redLog);
        // logDao.insertLog(123, uuid, 10, 20);
    }

}