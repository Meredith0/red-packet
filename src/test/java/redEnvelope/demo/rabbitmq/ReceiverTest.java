package redEnvelope.demo.rabbitmq;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith (SpringRunner.class)
@SpringBootTest
class ReceiverTest {

    @Autowired
    private Sender sender;

    @Autowired
    private Receiver receiver;

    @Test
    void process () throws InterruptedException {
        while (true) {

            this.sender.send("hello world");
            Thread.sleep(100);

        }

    }

}