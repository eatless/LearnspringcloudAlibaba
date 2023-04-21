package com.pzhu.spring.cloud.alibaba.consumer.controller;

import com.pzhu.spring.cloud.alibaba.common.domain.BgBlog;

import com.pzhu.spring.cloud.alibaba.consumer.service.BgBlogServiceFeign;
import com.pzhu.spring.cloud.alibaba.consumer.service.ExcelService;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import springfox.documentation.oas.annotations.EnableOpenApi;

/**
 * @Author Guo Huaijian
 * @Date 2020/3/16 19:03
 */
@RestController
@EnableOpenApi
public class BgBlogController {

    @Autowired
    private BgBlogServiceFeign serviceFeign;

    @Autowired
    private ExcelService excelService;

    /**
     * 查询列表
     * @return
     */
    @GetMapping("list")
    public List<BgBlog> list(){
        return serviceFeign.list();
    }

    /**
     * 查询列表
     * @return
     */
    @PostMapping("writeExcel")
    public void writeExcel(){
        excelService.writeExcel();
    }


    /**
     * 读取列表
     * @return
     */
    @PostMapping("readExcel")
    public void readExcel(){
        excelService.readExcel();
    }


    public static class Main {
        public static void main(String[] args) throws InterruptedException {
            final CountDownLatch countDownLatch = new CountDownLatch(10);
            for (int i = 0; i < 10; i++) {
                int finalI = i;
                new Thread(() -> {
                    int second = new Random().nextInt(10);
                    try {
                        Thread.sleep(second * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("线程" + finalI + "干完活了");
                    countDownLatch.countDown();
                }).start();
            }
            countDownLatch.await();
            System.out.println("老板发话了，所有人干完活了！");
        }
    }
}
