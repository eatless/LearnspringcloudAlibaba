package com.pzhu.spring.cloud.alibaba.consumer.service;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.pzhu.spring.cloud.alibaba.consumer.Util.AsyncUtil;
import com.pzhu.spring.cloud.alibaba.consumer.excel.Listener.ExcelListener;
import com.pzhu.spring.cloud.alibaba.consumer.excel.WriteData.DemoData;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
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
