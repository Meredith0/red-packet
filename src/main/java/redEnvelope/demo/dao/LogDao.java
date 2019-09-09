package redEnvelope.demo.dao;
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

    @Insert ("INSERT INTO `red-envelope`.`user-redenvelope-log` VALUES (#{userId},#{uuid},#{sum},#{money})")
    // public void insertLog (Integer userId, String uuid, int sum, int money);
    public void insertLog (RedLog redLog);
}
