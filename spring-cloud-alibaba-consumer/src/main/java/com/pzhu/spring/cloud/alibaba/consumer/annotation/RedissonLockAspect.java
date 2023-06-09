package com.pzhu.spring.cloud.alibaba.consumer.annotation;

import cn.hutool.core.util.StrUtil;
import com.pzhu.spring.cloud.alibaba.consumer.Util.SpElUtils;
import com.pzhu.spring.cloud.alibaba.consumer.Util.lockUtil.RedissionUtil;
import java.lang.reflect.Method;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Description: 分布式锁切面 Author: <a href="https://github.com/zongzibinbin">abin</a> Date: 2023-04-20
 */
@Slf4j
@Aspect
@Component
@Order(0)//确保比事务注解先执行，分布式锁在事务外
public class RedissonLockAspect {

    @Autowired
    private RedissionUtil lockService;

    @Around("@annotation(com.pzhu.spring.cloud.alibaba.consumer.annotation.RedissonLock)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        RedissonLock redissonLock = method.getAnnotation(RedissonLock.class);
        //默认方法限定名+注解排名（可能多个）
        String prefix = StrUtil.isBlank(redissonLock.prefixKey()) ? SpElUtils.getMethodKey(method) : redissonLock.prefixKey();
        String key = SpElUtils.parseSpEl(method, joinPoint.getArgs(), redissonLock.key());
        return lockService.executeWithLockThrows(prefix + ":" + key, redissonLock.waitTime(),redissonLock.expireTime(), redissonLock.unit(), joinPoint::proceed);
    }
}
