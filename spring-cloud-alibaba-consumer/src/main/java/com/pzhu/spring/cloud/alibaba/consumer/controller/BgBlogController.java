package com.pzhu.spring.cloud.alibaba.consumer.controller;

import com.pzhu.spring.cloud.alibaba.common.domain.BgBlog;

import com.pzhu.spring.cloud.alibaba.consumer.service.BgBlogServiceFeign;
import com.pzhu.spring.cloud.alibaba.consumer.service.ExcelService;
import com.pzhu.spring.cloud.alibaba.consumer.service.ThreadUtilService;
import com.pzhu.spring.cloud.alibaba.consumer.service.FileService;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
    private FileService fileService;
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




    @PostMapping("/fileUpload")
    @ResponseBody
    public String getUplode(@RequestPart("one") MultipartFile mf ) throws IOException {

        log.info("单文件上传信息为:" + mf.getOriginalFilename());
        fileService.updatefile(mf);

        return "成功";
    }

    @GetMapping("/downLoad")
    //http://localhost:8083/downLoad?filename=1.pdf
    @ResponseBody
    public void download(HttpServletResponse response,@RequestParam("filename") String fileName) throws IOException {

        OutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            File file = new File("/Users/jinhaiyang/Desktop/upfileAndDownFile/" + fileName);
            if(!file.exists() || !file.isFile()){
                outputStream.write("no this file".getBytes());
                return;
            }
            FileInputStream fileInputStream =new FileInputStream(file);
            IOUtils.copy(fileInputStream,outputStream);
        } catch (IOException e) {
//            logger.error("获取文件,系统异常， 路径：{}",path);
            e.printStackTrace();
        }
    }
}
