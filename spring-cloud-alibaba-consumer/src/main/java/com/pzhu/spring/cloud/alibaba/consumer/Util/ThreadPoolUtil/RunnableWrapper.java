//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.pzhu.spring.cloud.alibaba.consumer.Util.ThreadPoolUtil;

import org.apache.skywalking.apm.toolkit.trace.TraceCrossThread;

@TraceCrossThread
public class RunnableWrapper implements Runnable {
    final Runnable runnable;

    public RunnableWrapper(Runnable runnable) {
        this.runnable = runnable;
    }

    public static RunnableWrapper of(Runnable r) {
        return new RunnableWrapper(r);
    }

    @Override
    public void run() {
        this.runnable.run();
    }
}
