-- 唯一key
local tokens_key = KEYS[1]

-- ？
--local timestamp_key = KEYS[2]
local last_info = redis.call('HGETALL', tokens_key)


-- 限流速率
local rate = tonumber(ARGV[1])

-- 令牌桶容量
local capacity = tonumber(ARGV[2])

-- 当前时间戳
local time = redis.call('time')
local now = tonumber(time[1])
--local now = tonumber(ARGV[3])

-- 当前需要的token数量
local requested = tonumber(ARGV[3])

local fill_time = capacity / rate
local ttl = math.floor(fill_time * 2)

-- 上次剩余的token
--local last_tokens = tonumber(redis.call("get", tokens_key))
local last_tokens = tonumber(last_info[1])
if last_tokens == nil then
    last_tokens = capacity
end

-- 上一次的时间
--local last_refreshed = tonumber(redis.call("get", timestamp_key))
local last_refreshed = tonumber(last_info[3])
if last_refreshed == nil then
    last_refreshed = 0
end

local delta = math.max(0, now - last_refreshed)
local filled_tokens = math.min(capacity, last_tokens + (delta * rate))
local allowed = filled_tokens >= requested
local new_tokens = filled_tokens
local allowed_num = 0

if allowed then
    new_tokens = filled_tokens - requested
    allowed_num = 1
end

--redis.call("setex", tokens_key, ttl, new_tokens)
--redis.call("setex", timestamp_key, ttl, now)
redis.call('HMSET', tokens_key, 'tokens', new_tokens, 'timestamp', now)

return allowed_num
--return { allowed_num, new_tokens }
