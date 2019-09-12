package rabbitmq;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * @author : Meredith
 * @date : 2019-09-12 13:59
 * @description :
 */
public class testConn {

    public static final String QUENE_NAME = "test_queue";
    public static Connection getConnection () throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        factory.setUsername("root");
        factory.setPassword("123456");
        factory.setVirtualHost("/root");

        return factory.newConnection();
    }

    public static void send() throws IOException, TimeoutException {
        Connection connection = getConnection();

        Channel channel = connection.createChannel();

        channel.queueDeclare(QUENE_NAME, false, false, false, null);

        String msg = "hello world";
        channel.basicPublish("", QUENE_NAME, null, msg.getBytes());

        channel.close();
        connection.close();

    }

    public static void get () throws IOException, TimeoutException {
        Connection connection = getConnection();
        Channel channel = connection.createChannel();
        DefaultConsumer c = new DefaultConsumer(channel){
            @Override
            public void handleDelivery (String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
                throws IOException {
                super.handleDelivery(consumerTag, envelope, properties, body);

                String msg = new String(body, StandardCharsets.UTF_8);
                System.out.println("msg " + msg);
            }
        };

        channel.basicConsume(QUENE_NAME, c);


    }
    public static void main (String[] args) throws IOException, TimeoutException {
        send();

        ((Runnable) () -> {
            try {
                send();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }

        }).run();

        new Runnable(){
            /**
             * When an object implementing interface <code>Runnable</code> is used
             * to create a thread, starting the thread causes the object's
             * <code>run</code> method to be called in that separately executing
             * thread.
             * <p>
             * The general contract of the method <code>run</code> is that it may
             * take any action whatsoever.
             *
             * @see Thread#run()
             */
            @Override
            public void run () {
                try {
                    get();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }

            }
        }.run();


    }
}
