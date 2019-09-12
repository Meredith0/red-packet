package redEnvelope.demo.model;
import lombok.Data;

/**
 * @author : Meredith
 * @date : 2019-09-05 16:35
 * @description : 一批红包的记录
 */
@Data
public class RedLog {

    String userId;
    String uuid;
    Integer sum;
    Integer money;

    public RedLog (String userId, String uuid, Integer sum, Integer money) {
        this.userId = userId;
        this.uuid = uuid;
        this.sum = sum;
        this.money = money;
    }

}
