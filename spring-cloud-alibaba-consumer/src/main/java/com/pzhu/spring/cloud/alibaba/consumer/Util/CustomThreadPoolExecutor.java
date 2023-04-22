//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.pzhu.spring.cloud.alibaba.consumer.Util;

import java.util.Objects;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public class CustomThreadPoolExecutor extends ThreadPoolTaskExecutor {
    private static final Logger log = LoggerFactory.getLogger(CustomThreadPoolExecutor.class);

    public CustomThreadPoolExecutor(int corePoolSize, int maximumPoolSize, int keepAliveTime, int queueCapacity, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super.setCorePoolSize(corePoolSize);
        super.setMaxPoolSize(maximumPoolSize);
        super.setKeepAliveSeconds(keepAliveTime);
        super.setQueueCapacity(queueCapacity);
        super.setThreadNamePrefix("MyExecutor-");
        super.setThreadFactory(threadFactory);
        super.setRejectedExecutionHandler(handler);
    }

    public void afterExecute(Runnable r, Throwable t) {
        super.execute(r);
        if (Objects.isNull(t) && r instanceof Future) {
            try {
                Object var3 = ((Future)r).get();
            } catch (CancellationException var4) {
                t = var4;
            } catch (ExecutionException var5) {
                t = var5.getCause();
            } catch (InterruptedException var6) {
                Thread.currentThread().interrupt();
            }
        }

        if (t != null) {
            log.error(((Throwable)t).getMessage());
            ((Throwable)t).printStackTrace();
        }

    }
}
