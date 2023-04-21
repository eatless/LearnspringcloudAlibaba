package com.pzhu.spring.cloud.alibaba.consumer.service;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
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
public class ExcelService implements ApplicationContextAware {

    public  ApplicationContext applicationContext;

    public  void writeExcel() {
        log.info("test");
        //实现excel写操作
        //1、设置写入的文件夹的地址和easyexcel名称
        String fileName = "/Users/jinhaiyang/Desktop/write.xlsx";
        //2、调用easyexcel实现写操作
        ExcelWriterBuilder write = EasyExcel.write(fileName, DemoData.class);
        //设置sheet的名称，doWrite里要放入要写的集合
        write.sheet("学生列表").doWrite(getData());

    }

    private static List<DemoData> getData() {
        List<DemoData> list = new ArrayList<DemoData>();

        for (int i = 10; i > 0; i--) {
            DemoData demoData = new DemoData();
            demoData.setSno(i);
            demoData.setSname("姓名" + i);
            list.add(demoData);
        }
        return list;
    }

    public void readExcel() {
        applicationContext.getEnvironment().getProperty("username");
        //        writeExcel();
        //设置读取的文件夹的地址和easyexcel名称
        String fileName = "/Users/jinhaiyang/Desktop/write.xlsx";
        //.sheet()是读取的哪一个sheet页，ExceListener内中的类对象要和DemoData一致。
        EasyExcel.read(fileName, DemoData.class,new ExcelListener()).sheet().doRead();
//        EasyExcel.read(fileName)
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
