--local key = KEYS[1]
--local val = ARGV[1]
--
--return redis.call('set', key, val)
function hello()
    print("hello world")
end

function hello1(str)
    print("hello from " .. str)
    return str
end

function redisOpt()
    local key = KEYS[1]
    local val = ARGV[1]

    return redis.call('set', key, val)
end
