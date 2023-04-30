package com.pzhu.spring.cloud.alibaba.consumer.Util.ThreadPool;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;

/**
 * @Author Jin haiyang
 * @Date 2023/4/22
 */

//@Service
@Slf4j
public  class yewu1 {
    //为什么不建议用原有的业务分组呢？  一个类就是一个业务，这样清晰明了，一看就知道那个业务应该用那个线程池了。
    //为什么要给线程添加名字呢？

    //根据场景设置核心参数，最大参数，和队列
    public static ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10,
        1000, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10),
        new
        ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
//                t.setDaemon(true);
                //线程的名字还是线程池的名字。
                t.setName("业务1" + r.hashCode());
                //优先级
                t.setPriority(10);
                return t;
            }
        }
        , new RejectedExecutionHandler() {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            //加监控/报警/日志/持久化异常。或者落到表里，定时处理补偿机制。
            throw new IllegalStateException("aaa");
        }
    });

    /**
     * 并发执行多任务
     * @param tasks
     */
    public static void runAllOfConcurrency(Runnable... tasks) {
        if (Objects.isNull(tasks) || tasks.length == 0) {
            return;
        }
        Executor threadPoolExecutor = yewu1.executor;

        CompletableFuture<Void>[] cfs = new CompletableFuture[tasks.length];
        for (int i = 0; i < tasks.length; i++) {
            cfs[i] = CompletableFuture.runAsync(tasks[i], threadPoolExecutor);
        }
        try {
            CompletableFuture.allOf(cfs).join();
        } catch (CompletionException e) {
              log.info("Failed to complete");
//            Throwable cause = e.getCause();
//            if (cause instanceof BaseException) {
//                throw (BaseException) cause;
//            }
//            throw new RuntimeException(cause);
        }
    }




    /**
     * 并发执行多任务，且带有返回值
     * @param tasks
     */
    public static List<String>  runAllOfConcurrencyWithReturn(Supplier... tasks) {
        if (Objects.isNull(tasks) || tasks.length == 0) {
            return new ArrayList<>();
        }
        Executor threadPoolExecutor = yewu1.executor;
        CompletableFuture<String>[] cfs = new CompletableFuture[tasks.length];
        for (int i = 0; i < tasks.length; i++) {
                cfs[i] = CompletableFuture.supplyAsync(tasks[i], threadPoolExecutor);
        }
        List<String> result = new ArrayList<>();
        for (int i = 0; i < cfs.length; i++) {
            try {
                String s1 = cfs[i].get();
                result.add(s1);
            }catch (Exception e){
                //1秒得不到返回，抛出异常处理。
                e.printStackTrace();
            }
        }
        try {
            CompletableFuture.allOf(cfs).join();
        } catch (CompletionException e) {
            log.info("Failed to complete");
        }

        return result;
    }

    public  static class  withReturnCallAble implements Supplier {
        private String business;
        public withReturnCallAble(String business) {
            this.business = business;
        }

        @Override
        public String get() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //业务处理，然后返回结果
            return business;
        }
    }

}
