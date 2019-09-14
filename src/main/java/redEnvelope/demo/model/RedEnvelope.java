package redEnvelope.demo.model;
import java.io.Serializable;

/**
 * @author : Meredith
 * @date : 2019-08-17 23:36
 * @description : 拆分后的红包
 */

public class RedEnvelope implements Serializable {

    private static final long serialVersionUID = 1L;

    //红包的id,用于判断是哪一组
    private String uuid;
    //抢到次红包的用户id, 默认是空
    private String userId = "";   //setUserId() 会去掉{hashTag}:
    //红包金额, 单位为分
    private Integer money;

    public RedEnvelope () {
    }

    public RedEnvelope (String uuid, Integer money) {
        this.uuid = uuid;
        this.money = money;
    }

    public RedEnvelope (String uuid, String userId, Integer money) {
        this.uuid = uuid;
        this.userId = userId;
        this.money = money;
    }

    public static long getSerialVersionUID () {
        return serialVersionUID;
    }

    public String getUuid () {
        return uuid;
    }

    public void setUuid (String uuid) {
        this.uuid = uuid;
    }

    public String getUserId () {
        return userId;
    }

    public void setUserId (String userId) {
        if (userId.charAt(0) == '{') {
            this.userId = userId.substring(7);
        } else {
            this.userId = userId;
        }
    }

    public Integer getMoney () {
        return money;
    }

    public void setMoney (Integer money) {
        this.money = money;
    }

    @Override
    public String toString () {
        return "RedEnvelope{" + "uuid='" + uuid + '\'' + ", userId='" + userId + '\'' + ", money=" + money + '}';
    }

}