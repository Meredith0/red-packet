package redEnvelope.demo.service;
import java.util.concurrent.Future;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redEnvelope.demo.dao.LogDao;
import redEnvelope.demo.model.RedLog;

/**
 * @author : Meredith
 * @date : 2019-09-08 23:44
 * @description :
 */
@Service
@Slf4j
public class LogService {

    @Autowired
    LogDao logDao;


    public Future<Boolean> addLog (RedLog redLog) {

        log.debug("插入redLog..." + redLog.toString());
        return logDao.insertLog(redLog);
    }
}
