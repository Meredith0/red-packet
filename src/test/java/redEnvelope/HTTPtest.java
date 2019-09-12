package redEnvelope;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * @author : Meredith
 * @date : 2019-09-11 19:56
 * @description :
 */
@Slf4j
public class HTTPtest {

    // 总的请求个数
    public static final int requestTotal = 2000;

    // 同一时刻最大的并发线程的个数
    public static final int concurrentThreadNum = 2000;

    private static final String uuid = "15250427de4d4582b403be43f50ec75f";

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        CountDownLatch countDownLatch = new CountDownLatch(requestTotal);
        Semaphore semaphore = new Semaphore(concurrentThreadNum);
        for (int i = 0; i< requestTotal; i++) {
            executorService.execute(()->{
                try {
                    semaphore.acquire();
                    String result = testRequestUri();
                    log.info("result:{}.", result);
                    semaphore.release();
                } catch (InterruptedException | IOException e) {
                    log.error("exception", e);
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        executorService.shutdown();
        log.info("请求完成");
    }

    private static String testRequestUri() throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet("http://127.0.0.1:8080/get?uuid="+ uuid);
        //3.执行get请求并返回结果
        CloseableHttpResponse response = httpclient.execute(httpget);
        try {
            //4.处理结果
            return response.toString();
        } finally {
            response.close();
        }
    }
}
