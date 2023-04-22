package com.pzhu.spring.cloud.alibaba.consumer.service;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.pzhu.spring.cloud.alibaba.consumer.excel.Listener.ExcelListener;
import com.pzhu.spring.cloud.alibaba.consumer.excel.WriteData.DemoData;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class UpFileService {

    public void updatefile(MultipartFile file) throws IOException {
        // 文件大小不能超过5M
        if(Objects.isNull(file) || file.getSize() == 0){
            log.info("附件或原始简历不能为空");
        }

        if(Objects.nonNull(file) && file.getSize() > (5 * 1024 * 1024)){
            log.info("附件或原始简历不能为空或大于5M;");
        }
        /*将文件上传到指定文件夹*/
        if (!file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            log.info("文件名字为"+fileName);
            //文件上传
            file.transferTo(new File("E:\\" + fileName));
        }
    }


}
