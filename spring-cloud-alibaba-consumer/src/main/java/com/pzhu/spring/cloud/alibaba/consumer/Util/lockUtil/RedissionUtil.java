package com.pzhu.spring.cloud.alibaba.consumer.Util.lockUtil;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.RedissonMultiLock;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author jinhaiyang
 */
@Component
@Slf4j
public class RedissionUtil {


    @Autowired
    private Redisson redisson;

    /**
     * 排房锁前缀
     */
    public static final String ROOM_ARRANGE_LOCK_TITLE = "ROOM_ARRANGE:";
    public static final long LOCK_WAIT_TIME = 2000L;

    /**
     * 自动排房key
     */
    public static final String AUTO_ROOM_ARRAGE_LOCK = "AUTO_ROOM_ARRAGE_LOCK";

    //阻塞加锁
    public boolean acquire(String lockName, long expireTime) {
        //声明key对象
        String key = ROOM_ARRANGE_LOCK_TITLE + lockName;
        //获取锁对象
        RLock mylock = redisson.getLock(key);
        //加锁，并且设置锁过期时间3秒，防止死锁的产生  uuid+threadId
        mylock.lock(expireTime, TimeUnit.SECONDS);
        //加锁成功
        return true;
    }


    // 尝试加锁，加锁失败返回异常
    public boolean tryAcquire(String lockName, long expireTime) {
        // 声明key对象
        String key = ROOM_ARRANGE_LOCK_TITLE + lockName;
        // 获取锁对象
        RLock mylock = redisson.getLock(key);
        // 使用tryLock方法尝试加锁，并且设置锁过期时间
        try {
            // 如果成功获取锁，返回true，否则返回false
            return mylock.tryLock(0, expireTime, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            // 发生异常时，返回false
            return false;
        }
    }


    //锁的释放
    public void release(String lockName) {
        //必须是和加锁时的同一个key
        String key = ROOM_ARRANGE_LOCK_TITLE + lockName;
        //获取所对象
        RLock mylock = redisson.getLock(key);
        //释放锁（解锁）
        mylock.unlock();
    }

    /**
     * 批量添加锁
     *
     * @param resourceList 资源列表
     * @param expireTime   加锁时间，秒级别
     * @return 加锁结果
     */
    public boolean multiLockTryAcquire(List<String> resourceList, String hotelCode, long expireTime) {
        if(CollUtil.isEmpty(resourceList)){
            return true;
        }
        // 创建分布式锁对象数组
        RLock[] locks = new RLock[resourceList.size()];
        for (int i = 0; i < resourceList.size(); i++) {
            locks[i] = redisson.getLock(ROOM_ARRANGE_LOCK_TITLE + hotelCode +":"+ resourceList.get(i));
        }
        // 获取 RedissonMultiLock 对象
        RedissonMultiLock multiLock = new RedissonMultiLock(locks);
        // 尝试获取锁，如果成功，则返回 true；否则，返回 false
        try {
            long startLock = System.currentTimeMillis();
            //加锁
            boolean isLocked = multiLock.tryLock(2, expireTime, TimeUnit.SECONDS);
            long endLock = System.currentTimeMillis();
            log.info("rowHouses      加锁耗时为{}",endLock-startLock);
            if(endLock-startLock>LOCK_WAIT_TIME){
                //收集大于2S的次数，如果过多，需要调整。
                log.error("rowHouses        加锁时间过长");
            }
            if (!isLocked) {
                log.info("rowHouses        加锁失败!");
            }
            return isLocked;
        } catch (InterruptedException e) {
            return false;
        }
    }


    /**
     * 批量释放锁
     *
     * @param resourceList 释放锁资源
     */
    public void mutiLockRelease(List<String> resourceList, String hotelCode) {
        // 创建分布式锁对象数组
        RLock[] locks = new RLock[resourceList.size()];
        for (int i = 0; i < resourceList.size(); i++) {
            locks[i] = redisson.getLock(ROOM_ARRANGE_LOCK_TITLE + hotelCode +":"+ resourceList.get(i));
        }
        // 获取 RedissonMultiLock 对象
        RedissonMultiLock multiLock = new RedissonMultiLock(locks);
        // 尝试获取锁，如果成功，则返回 true；否则，返回 false
        multiLock.unlock();
    }


    /**
     * 获取某个前缀的rediskey， 底层使用scan命令。
     *
     * @param prefix
     * @return
     */
    public List<String> getKeysByPreUseScan(String prefix) {
        // 获取以"prefix"开头的key
        Iterable<String> keys = redisson.getKeys().getKeysByPattern(prefix);
        ArrayList<String> list = new ArrayList<>();
        for (String key : keys) {
            list.add(key);
        }
        log.info(" rowHouses  查询到的key有    {}" , JSON.toJSONString(list));
        return list;
    }


    /**
     * 校验锁是否被锁定
     * @param lockKey
     * @return
     */
    public Boolean checkLockIsLocked(String lockKey){
        // 获取指定名称的分布式锁对象
        RLock lock = redisson.getLock(lockKey);
        return lock.isLocked();
    }



}
