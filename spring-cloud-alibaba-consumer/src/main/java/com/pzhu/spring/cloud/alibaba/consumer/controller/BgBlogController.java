package com.pzhu.spring.cloud.alibaba.consumer.controller;

import com.pzhu.spring.cloud.alibaba.common.domain.BgBlog;

import com.pzhu.spring.cloud.alibaba.consumer.service.BgBlogServiceFeign;
import com.pzhu.spring.cloud.alibaba.consumer.service.ExcelService;
import com.pzhu.spring.cloud.alibaba.consumer.service.ThreadUtilService;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import springfox.documentation.oas.annotations.EnableOpenApi;

/**
 * @Author Jin haiyang
 * @Date 2020/3/16 19:03
 */

@RestController
@EnableOpenApi
public class BgBlogController {

    @Autowired
    private BgBlogServiceFeign serviceFeign;

    @Autowired
    private ExcelService excelService;

    @Autowired
    private ThreadUtilService threadUtilService;

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

    /**
     * 多线程处理数据计算数据
     * @return
     */
    @PostMapping("muiltiThread")
    public void muiltiThread(){
        threadUtilService.muiltiThread();
    }

}
