package com.pzhu.spring.cloud.alibaba.common.Enum;

/**
 * @author dingyx
 * @Date 2023-06-02 11:18
 * @Description
 */
public enum RedisLockEnum {

    ROOM_ARRANGE("ROOM_ARRANGE:",60L),
    CHECK_IN("CHECK_IN:",30L),
            ;

    private String preKey;

    private long expireTime;

    RedisLockEnum(String preKey, long expireTime) {
        this.preKey = preKey;
        this.expireTime = expireTime;
    }

    public String getPreKey() {
        return preKey;
    }

    public long getExpireTime() {
        return expireTime;
    }

}
