--
-- Created by IntelliJ IDEA.
-- User: Administrator
-- Date: 2019/9/5
-- Time: 16:44
-- To change this template use File | Settings | File Templates.

-- 函数：尝试获得红包，如果成功，则返回json字符串，如果不成功，则返回空
-- redis中一个未分配红包队列和一个已分配红包map
-- 参数：list id, map id, 红包uuid，用户ID
-- 返回值：nil 或者 json字符串，包含用户ID：userId，红包ID：id，红包金额：money

-- 如果用户已抢过红包，则返回nil

if redis.call('hexists', KEYS[2], ARGV[1]) ~= 0 then
    return nil
else
    -- 先取出一个小红包
    local one = redis.call('rpop', KEYS[1]);

    if one then
        local x = cjson.decode(one);
        -- 加入用户ID信息
        x['userId'] = ARGV[1];
        local res = cjson.encode(x);
        -- 把用户ID放到去重的set里
       -- redis.call('hset', KEYS[3], KEYS[4], money);
        -- 把红包放到已消费map里
        redis.call('hset', KEYS[2], ARGV[1], res);
        return res;
    end
end
return nil