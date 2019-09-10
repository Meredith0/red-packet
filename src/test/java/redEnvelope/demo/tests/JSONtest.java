package redEnvelope.demo.tests;
import com.alibaba.fastjson.JSON;
import redEnvelope.demo.model.RedEnvelope;

/**
 * @author : Meredith
 * @date : 2019-09-11 00:13
 * @description :
 */
public class JSONtest {

    public static void main(String[] args){

        RedEnvelope o = new RedEnvelope("uuid", 1);
        System.out.println(o.toString());
        System.out.println(JSON.toJSONString(o));


    }
}
