package redEnvelope.demo.util;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author : Meredith
 * @date : 2019-09-08 20:07
 * @description : 拆分红包算法
 */
public class HBgenerate {

    public static final Random random = new Random();

    /**
     * 拆分核心算法
     *
     * @param num 拆分红包个数
     * @param money 总金额
     */
    public static List<Integer> divideRedPackage (Integer num, Integer money) {
        List<Integer> amountList = new ArrayList<Integer>();

        Integer restAmount = money;

        Integer restPeopleNum = num;

        for (int i = 0; i < num - 1; i++) {
            //随机范围：[1，剩余人均金额的两倍)，左闭右开
            int amount = random.nextInt(restAmount / restPeopleNum * 2 - 1) + 1;
            restAmount -= amount;
            restPeopleNum--;
            amountList.add(amount);
        }
        amountList.add(restAmount);
        return amountList;
    }

}