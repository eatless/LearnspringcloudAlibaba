package com.pzhu.spring.cloud.alibaba.consumer.controller;

import com.pzhu.spring.cloud.alibaba.common.domain.BgBlog;

import com.pzhu.spring.cloud.alibaba.consumer.req.UploadFileReqDTO;
import com.pzhu.spring.cloud.alibaba.consumer.service.BgBlogServiceFeign;
import com.pzhu.spring.cloud.alibaba.consumer.service.ExcelService;
import com.pzhu.spring.cloud.alibaba.consumer.service.ThreadUtilService;
import com.pzhu.spring.cloud.alibaba.consumer.service.UpFileService;
import io.swagger.annotations.ApiOperation;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.oas.annotations.EnableOpenApi;

/**
 * @Author Jin haiyang
 * @Date 2020/3/16 19:03
 */

@RestController
@EnableOpenApi
@Slf4j
public class BgBlogController {

    @Autowired
    private BgBlogServiceFeign serviceFeign;

    @Autowired
    private ExcelService excelService;

    @Autowired
    private ThreadUtilService threadUtilService;

    @Autowired
    private UpFileService upFileService;
    /**
     * 查询列表
     *
     * @return
     */
    @GetMapping("list")
    public List<BgBlog> list() {
        return serviceFeign.list();
    }

    /**
     * 查询列表
     *
     * @return
     */
    @PostMapping("writeExcel")
    public void writeExcel() {
        excelService.writeExcel();
    }


    /**
     * 读取列表
     *
     * @return
     */
    @PostMapping("readExcel")
    public void readExcel() {
        excelService.readExcel();
    }

    /**
     * 多线程处理数据计算数据
     *
     * @return
     */
    @PostMapping("muiltiThread")
    public void muiltiThread() {
        threadUtilService.muiltiThread();
    }

    /**
     * 内部类的方式使用多线程
     *
     * @return
     */
    @PostMapping("innerClassmuiltiThread")
    public void innerClassmuiltiThread() {
        threadUtilService.innerClassmuiltiThread();
    }


    /**
     * 传入对象的方式
     * @param dto
     */
    @PostMapping(value = "/fileUpload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void upload(UploadFileReqDTO dto) {

        MultipartFile file = dto.getFile();
        String fileName = file.getOriginalFilename();
        System.out.println(fileName);

    }


    @PostMapping("/uplode3")
    @ResponseBody
    public String getUplode(@RequestPart("one") MultipartFile mf ) throws IOException {

        log.info("单文件上传信息为:" + mf.getOriginalFilename());
        upFileService.updatefile(mf);

        return "成功";
    }
}
