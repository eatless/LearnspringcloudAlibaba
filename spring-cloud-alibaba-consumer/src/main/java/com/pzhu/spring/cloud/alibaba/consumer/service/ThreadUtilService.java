package com.pzhu.spring.cloud.alibaba.consumer.service;

import com.pzhu.spring.cloud.alibaba.consumer.Util.ThreadPoolUtil.AsyncUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ThreadUtilService {

    //多线程处理数据并计算


    public  void muiltiThread() {
        AsyncUtil.runAllOfConcurrency("test",
            ()->{threadFunctions();},
            ()->{threadFunctions();});

    }


    public void threadFunctions() {
        System.out.println(Thread.currentThread().getName()+"执行了任务");
    }


}
