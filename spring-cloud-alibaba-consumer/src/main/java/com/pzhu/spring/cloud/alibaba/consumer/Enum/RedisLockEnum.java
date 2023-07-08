package com.pzhu.spring.cloud.alibaba.consumer.Enum;

import java.util.concurrent.TimeUnit;

/**
 * @author dingyx
 * @Date 2023-06-02 11:18
 * @Description
 */
public enum RedisLockEnum {


    /**
     * 锁1，自动释放时间60S
     */
    ROOM_ARRANGE("lock1",60L,TimeUnit.SECONDS),
    /**
     * 加锁2，自动释放时间30S
     */
    CHECK_IN("lock2:",30L,TimeUnit.SECONDS),
            ;

    RedisLockEnum(String preKey, long expireTime, TimeUnit timeUnit) {
        this.preKey = preKey;
        this.expireTime = expireTime;
        this.timeUnit = timeUnit;
    }

    private String preKey;

    private long expireTime;

    private TimeUnit timeUnit;

    public String getPreKey() {
        return preKey;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }
}
