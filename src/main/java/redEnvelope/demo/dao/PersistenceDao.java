package redEnvelope.demo.dao;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Repository;
import redEnvelope.demo.model.RedEnvelope;

@Repository
public interface PersistenceDao {

    @Insert("INSERT INTO `red-envelope`.red_envelopes(uuid, userId, money) VALUES (#{uuid},#{userId},#{money})")
    int insert (RedEnvelope redEnvelope);

}
