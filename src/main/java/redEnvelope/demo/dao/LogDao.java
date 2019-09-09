package redEnvelope.demo.dao;
import java.util.concurrent.Future;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Repository;
import redEnvelope.demo.model.RedLog;

/**
 * @author : Meredith
 * @date : 2019-09-08 23:35
 * @description :
 */
@Repository
public interface LogDao {

    @Insert ("INSERT INTO `red-envelope`.user_red_log(userId, uuid, sum, money) VALUES (#{userId},#{uuid},#{sum}," +
                 "#{money})")
    Future<Boolean> insertLog (RedLog redLog);

}
