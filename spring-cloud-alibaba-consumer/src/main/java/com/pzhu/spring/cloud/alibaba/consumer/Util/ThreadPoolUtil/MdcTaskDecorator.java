//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.pzhu.spring.cloud.alibaba.consumer.Util.ThreadPoolUtil;

import java.util.Map;
import java.util.Objects;
import org.slf4j.MDC;
import org.springframework.core.task.TaskDecorator;

public class MdcTaskDecorator implements TaskDecorator {
    public MdcTaskDecorator() {
    }

    @Override
    public Runnable decorate(Runnable runnable) {
        Map<String, String> contextMap = MDC.getCopyOfContextMap();
        return () -> {
            try {
                if (Objects.nonNull(contextMap)) {
                    MDC.setContextMap(contextMap);
                }

                runnable.run();
            } finally {
                MDC.clear();
            }

        };
    }
}
