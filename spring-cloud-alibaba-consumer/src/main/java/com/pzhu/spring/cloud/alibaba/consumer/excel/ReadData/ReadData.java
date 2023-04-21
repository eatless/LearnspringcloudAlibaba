package com.pzhu.spring.cloud.alibaba.consumer.excel.ReadData;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class ReadData {
    //设置列对应的属性，这表示的是第一列
    @ExcelProperty(value = "sno",index = 0)
    private Integer sno;
    //这表示第二列

    @ExcelProperty(value = "sname",index = 1)
    private String sname;
}