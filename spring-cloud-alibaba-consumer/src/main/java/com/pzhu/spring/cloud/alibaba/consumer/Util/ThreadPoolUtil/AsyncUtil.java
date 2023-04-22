package com.pzhu.spring.cloud.alibaba.consumer.Util.ThreadPoolUtil;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.jvm.ExecutorServiceMetrics;
import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.beans.factory.BeanFactory;


public final class AsyncUtil {

    private static final ConcurrentHashMap<String, CustomThreadPoolExecutor> group = new ConcurrentHashMap<>();

    /**
     * 并发执行任务，不需要返回结果
     * <p>封装MDC传递，trace传递，线程名称设置等操作<p/>
     *
     * @param nameGroup 任务分组名称，用于获取对应线程池以及执行线程名称前缀
     * @param tasks     要执行的任务，如各个job extend
     */
    public static void runAllOfConcurrency(String nameGroup, Runnable... tasks) {
        if (Objects.isNull(tasks) || tasks.length == 0) {
            return;
        }
        Executor threadPoolExecutor = getExecutorByName(nameGroup);
        CompletableFuture<Void>[] cfs = new CompletableFuture[tasks.length];
        for (int i = 0; i < tasks.length; i++) {
            cfs[i] = CompletableFuture.runAsync(tasks[i], threadPoolExecutor);
        }
        try {
            CompletableFuture.allOf(cfs).join();
        } catch (CompletionException e) {
//            Throwable cause = e.getCause();
//            if (cause instanceof BaseException) {
//                throw (BaseException) cause;
//            }
//            throw new RuntimeException(cause);
        }
    }

    public static void runAllOfConcurrency(String nameGroup, Collection<Runnable> tasks) {
        if (Objects.isNull(tasks) || tasks.isEmpty()) {
            return;
        }
        Runnable[] rs = new Runnable[tasks.size()];
        AtomicInteger i = new AtomicInteger(0);
        tasks.forEach(t -> rs[i.getAndAdd(1)] = t);
        runAllOfConcurrency(nameGroup, rs);
    }

    private static Executor getExecutorByName(String nameGroup) {
        BeanFactory beanFactory = SpringContextHolder.getApplicationContext();

        Integer corePoolSize = ConfigUtil.get("jc.thread.pool.core.size", Integer.class, 24);
        Integer maxPoolSize = ConfigUtil.get("jc.thread.pool.max.size", Integer.class, 64);
        Integer queueCapacity = ConfigUtil.get("jc.thread.pool.queue.size", Integer.class, 512);

        CustomThreadPoolExecutor executor = group.isEmpty() ? null : group.values().iterator().next();
        if (Objects.isNull(executor)) {
            executor = ExecutorBuilder.create()
                .setCorePoolSize(corePoolSize)
                .setMaxPoolSize(maxPoolSize)
                .setQueueCapacity(queueCapacity)
                .setHandler(new CallerRunsPolicy())
                .build(beanFactory);
            group.put("jc", executor);
            MeterRegistry registry = beanFactory.getBean(MeterRegistry.class);
            ExecutorServiceMetrics.monitor(registry, executor.getThreadPoolExecutor(), "jcBizExecutorCommon");
        } else {
            //若配置更新，则及时更新线程池配置
            if (!Objects.equals(corePoolSize, executor.getCorePoolSize())) {
                executor.setCorePoolSize(corePoolSize);
            }
            if (!Objects.equals(maxPoolSize, executor.getMaxPoolSize())) {
                executor.setMaxPoolSize(maxPoolSize);
            }
        }
        return executor;
    }

    public static void runAsync(Runnable task) {
        ThreadUtil.execute(task);
    }

}
