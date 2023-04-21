package com.pzhu.spring.cloud.alibaba.consumer.excel.Listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.CellData;

import com.pzhu.spring.cloud.alibaba.consumer.excel.WriteData.DemoData;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExcelListener extends AnalysisEventListener<DemoData> {

    /**
     * 一行一行读取，但是不读取表头
     *
     * @param demoData
     * @param analysisContext
     */
    @Override
    public void invoke(DemoData demoData, AnalysisContext analysisContext) {
        log.info("*******" + demoData);
    }

    /**
     * 读取表头
     *
     * @param headMap
     * @param context
     */
    @Override
    public void invokeHead(Map<Integer, CellData> headMap, AnalysisContext context) {
        log.info("表头：" + headMap);
    }

    /**
     * 读取完成之后做的事情
     *
     * @param analysisContext
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}