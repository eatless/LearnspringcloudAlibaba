package com.pzhu.spring.cloud.alibaba.consumer.service;

import com.pzhu.spring.cloud.alibaba.consumer.Util.ThreadPool.yewu1;
import com.pzhu.spring.cloud.alibaba.consumer.Util.ThreadPoolUtil.AsyncUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ThreadUtilService {

    //多线程处理数据并计算
    public  void muiltiThread() {
        //业务使用的多线程池。  一个业务一个池子。
        AsyncUtil.runAllOfConcurrency("test",
            ()->{threadFunctions();},
            ()->{threadFunctions();});

    }


    public  void muiltiThread2() {
        //业务使用的多线程池。  一个业务一个池子。
        //不带有返回值的例子
        CompletableFuture<Void>[] noResultcompletable = new CompletableFuture[10];
        for (int i = 0; i < noResultcompletable.length; i++) {
            noResultcompletable[i] = CompletableFuture.runAsync(() -> log.info("aaa"));
        }
        //带有返回值的
        CompletableFuture<String>[] cfs = new CompletableFuture[10];
        List<CompletableFuture<String>> completableFutures = new ArrayList<>();
        for (int j = 0; j < 10; j++) {
            //执行supplyAsync是带有返回值的。
            completableFutures.add(CompletableFuture.supplyAsync((Supplier) () -> "string",yewu1.executor));
        }

        for(CompletableFuture<String> completableFuture: completableFutures){
            try {
                //得到所有结果。获取不到阻塞，会一直阻塞
                String s = completableFuture.get(1, TimeUnit.SECONDS);
            }catch (Exception e){
                //1秒获取不到会抛出异常。
                log.info("Exception");
            }

        }

    }


    public void threadFunctions() {
        System.out.println(Thread.currentThread().getName()+"执行了任务");
    }

    public void innerClassmuiltiThread() {
        //业务使用的多线程池。  一个业务一个池子。
        yewu1.runAllOfConcurrency(
            ()->{threadFunctions();},
            ()->{threadFunctions();});
    }
}
