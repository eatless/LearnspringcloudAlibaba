package com.pzhu.spring.cloud.alibaba.consumer.service;

import com.pzhu.spring.cloud.alibaba.consumer.Util.ThreadPool.yewu1;
import com.pzhu.spring.cloud.alibaba.consumer.Util.ThreadPoolUtil.AsyncUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ThreadUtilService {

    //多线程处理数据并计算
    public void muiltiThread() {
        //业务使用的多线程池。  一个业务一个池子。
        AsyncUtil.runAllOfConcurrency("test",
            () -> {
                threadFunctions();
            },
            () -> {
                threadFunctions();
            });

    }


    /**
     * 一种是带有返回值的,使用指定线程池的业务处理
     */
    public List<String> withReturnMethod(List<String> methods) {
        //业务使用的多线程池。  一个业务一个池子。
        //不带有返回值的例子
        //带有返回值的
        CompletableFuture<String>[] cfs = new CompletableFuture[methods.size()];
        List<CompletableFuture<String>> completableFutures = new ArrayList<>();
        for (int j = 0; j < 10; j++) {
            //执行supplyAsync是带有返回值的。
            completableFutures.add(CompletableFuture.supplyAsync(new withReturnCallAble(methods.get(j)), yewu1.executor));
        }

        List<String> result = new ArrayList<>();
        for (CompletableFuture<String> completableFuture : completableFutures) {
            try {
                //得到所有结果。获取不到阻塞，会一直阻塞
                String s = completableFuture.get(1, TimeUnit.SECONDS);
                result.add(s);
            } catch (Exception e) {
                //1秒获取不到会抛出异常,所以要根据自己的业务来定。
                log.info("Exception");
            }
        }
        return result;

    }

    /**
     * 不带有返回值的，指定线程池的业务处理
     * @param methods
     */
    public void noReturen(List<String> methods) {
        //业务使用的多线程池。  一个业务一个池子。
        //不带有返回值的例子
        CompletableFuture<Void>[] noResultcompletable = new CompletableFuture[methods.size()];
        for (int i = 0; i < methods.size(); i++) {
            //使用指定的业务，并且创建自己的业务类，进行处理
            noResultcompletable[i] = CompletableFuture.runAsync(new noReturnRunable(methods.get(i)),yewu1.executor);
        }

    }

    public class  noReturnRunable implements Runnable {
        private String business ;
        public noReturnRunable (String business) {
            this.business = business;
        }
        @Override
        public void run () {
            //处理自己的业务的地方，我这里只是进行了打印
            log.info(business);
            return;
        }
    }


    public class  withReturnCallAble implements Supplier {
        private String business;
        public withReturnCallAble(String business) {
            this.business = business;
        }

        @Override
        public String get() {
            //业务处理，然后返回结果
            return business;
        }
    }




    public void threadFunctions() {
        System.out.println(Thread.currentThread().getName() + "执行了任务");
    }

    public void innerClassmuiltiThread() {
        //业务使用的多线程池。  一个业务一个池子。
        yewu1.runAllOfConcurrency(
            () -> {
                threadFunctions();
            },
            () -> {
                threadFunctions();
            });
    }
}
