package redEnvelope.demo.model;
import java.io.Serializable;
import lombok.Data;

/**
 * @author : Meredith
 * @date : 2019-08-17 23:36
 * @description : 拆分后的红包
 */
@Data
public class RedEnvelope implements Serializable {

    private static final long serialVersionUID = 1L;

    //红包的id,用于判断是哪一组
    private String uuid;
    //抢到次红包的用户id, 默认是空
    private String userId="";
    //红包金额, 单位为分
    private Integer money;

    public RedEnvelope (String uuid, Integer money) {
        this.uuid = uuid;
        this.money = money;
    }

}