//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.pzhu.spring.cloud.alibaba.consumer.Util;

import cn.hutool.core.exceptions.UtilException;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.function.Supplier;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Component
public class GlobalThreadPool {
    private static CustomThreadPoolExecutor executor;
    @Resource
    private BeanFactory beanFactory;

    private GlobalThreadPool() {
    }

    @PostConstruct
    public synchronized void init() {
        if (!Objects.nonNull(executor)) {
            executor = ExecutorBuilder.create().setCorePoolSize(64).setMaxPoolSize(512).build(this.beanFactory);
        }
    }

    public synchronized void shutdown() {
        if (null != executor) {
            executor.shutdown();
        }

    }

    public CustomThreadPoolExecutor getExecutor() {
        return executor;
    }

    public static void execute(Runnable runnable) {
        try {
            transactionDecorator(() -> {
                executor.execute(runnable);
                return null;
            });
        } catch (Exception var2) {
            throw new UtilException(var2, "Exception when running task!", new Object[0]);
        }
    }

    public static <T> Future<T> submit(Callable<T> task) {
        return transactionDecorator(() -> {
            return executor.submit(task);
        });
    }

    public static Future<?> submit(Runnable runnable) {
        return transactionDecorator(() -> {
            return executor.submit(runnable);
        });
    }

    private static <T> Future<T> transactionDecorator(Supplier<Future<T>> supplier) {
        boolean isActive = TransactionSynchronizationManager.isActualTransactionActive();
        if (!isActive) {
            return (Future)supplier.get();
        } else {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
                public void afterCommit() {
                    supplier.get();
                }
            });
            return null;
        }
    }
}
