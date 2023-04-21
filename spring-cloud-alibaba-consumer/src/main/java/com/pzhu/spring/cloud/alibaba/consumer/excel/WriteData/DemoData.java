package com.pzhu.spring.cloud.alibaba.consumer.excel.WriteData;


import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class DemoData {
    //学生编号，设置excel的表头
    @ExcelProperty("学生编号")
    private Integer sno;
    //学生名字，设置excel的表头
    @ExcelProperty("学生姓名")
    private String sname;

}