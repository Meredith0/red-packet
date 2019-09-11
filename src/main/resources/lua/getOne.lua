--
-- Created by IntelliJ IDEA.
-- User: Administrator
-- Date: 2019/9/5
-- Time: 16:44
-- To change this template use File | Settings | File Templates.

-- 函数：尝试获得红包，如果成功，则返回json字符串，如果不成功，则返回空
-- 参数：红包队列名， 已消费的队列名，去重的set名，用户ID
-- 返回值：nil 或者 json字符串，包含用户ID：userId，红包ID：id，红包金额：money

-- 如果用户已抢过红包，则返回nil
if redis.call('hexists', KEYS[3], KEYS[4]) ~= 0 then
    return nil
else
    -- 先取出一个小红包
    local one = redis.call('rpop', KEYS[1]);
    if one then
        local x = cjson.decode(one);
        -- 加入用户ID信息
        x['userId'] = KEYS[4];
        local res = cjson.encode(x);
        -- 把用户ID放到去重的set里
        redis.call('hset', KEYS[3], KEYS[4], KEYS[4]);
        -- 把红包放到已消费队列里
        redis.call('lpush', KEYS[2], res);
        return res;
    end
end
return nil