package com.pzhu.spring.cloud.alibaba.consumer.annotation;

import com.pzhu.spring.cloud.alibaba.consumer.Enum.RedisLockEnum;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * 分布式锁注解
 *
 * @author jinhaiyang
 */
@Retention(RetentionPolicy.RUNTIME)//运行时生效
@Target(ElementType.METHOD)//作用在方法上
public @interface RedissonLock {

    /**
     * key的前缀,默认取方法全限定名，除非我们在不同方法上对同一个资源做分布式锁，就自己指定
     *
     * @return key的前缀
     */
    String prefixKey() default "";

    /**
     * springEl 表达式
     *
     * @return 表达式
     */
    String key();

    /**
     * 等待锁的时间，默认-1，不等待直接失败,redisson默认也是-1
     *
     * @return 单位秒
     */
    int waitTime() default -1;

    /**
     * 自动释放锁时长，默认为-1，也就是不会自动释放锁。根据业务设置自动释放锁时长，避免死锁
     *
     * @return 单位秒
     */
    int expireTime() default -1;

    /**
     * 等待锁的时间单位，默认毫秒
     *
     * @return 单位
     */
    TimeUnit unit() default TimeUnit.MILLISECONDS;

}
