//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.pzhu.spring.cloud.alibaba.consumer.Util;

import cn.hutool.core.util.ObjectUtil;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;
import org.springframework.beans.factory.BeanFactory;

public class ExecutorBuilder {
    private int corePoolSize;
    private int maxPoolSize = 2147483647;
    private int keepAliveTime = 60;
    private int queueCapacity = 0;
    private ThreadFactory threadFactory;
    private RejectedExecutionHandler handler;
    private Boolean allowCoreThreadTimeOut = false;

    public static ExecutorBuilder create() {
        return new ExecutorBuilder();
    }

    public CustomThreadPoolExecutor build(BeanFactory beanFactory) {
        return build(this, beanFactory);
    }

    private static CustomThreadPoolExecutor build(ExecutorBuilder builder, BeanFactory beanFactory) {
        int corePoolSize = builder.corePoolSize;
        int maxPoolSize = builder.maxPoolSize;
        int keepAliveTime = builder.keepAliveTime;
        int queueCapacity = builder.queueCapacity;
        ThreadFactory threadFactory = null != builder.threadFactory ? builder.threadFactory : Executors.defaultThreadFactory();
        RejectedExecutionHandler handler = (RejectedExecutionHandler) ObjectUtil.defaultIfNull(builder.handler, new AbortPolicy());
        CustomThreadPoolExecutor threadPoolExecutor = new CustomThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveTime, queueCapacity, threadFactory, handler);
        if (null != builder.allowCoreThreadTimeOut) {
            threadPoolExecutor.setAllowCoreThreadTimeOut(builder.allowCoreThreadTimeOut);
        }

        threadPoolExecutor.setTaskDecorator(new MdcTaskDecorator());
        threadPoolExecutor.initialize();
        return threadPoolExecutor;
    }

    public ExecutorBuilder() {
    }

    public int getCorePoolSize() {
        return this.corePoolSize;
    }

    public int getMaxPoolSize() {
        return this.maxPoolSize;
    }

    public int getKeepAliveTime() {
        return this.keepAliveTime;
    }

    public int getQueueCapacity() {
        return this.queueCapacity;
    }

    public ThreadFactory getThreadFactory() {
        return this.threadFactory;
    }

    public RejectedExecutionHandler getHandler() {
        return this.handler;
    }

    public Boolean getAllowCoreThreadTimeOut() {
        return this.allowCoreThreadTimeOut;
    }

    public ExecutorBuilder setCorePoolSize(final int corePoolSize) {
        this.corePoolSize = corePoolSize;
        return this;
    }

    public ExecutorBuilder setMaxPoolSize(final int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
        return this;
    }

    public ExecutorBuilder setKeepAliveTime(final int keepAliveTime) {
        this.keepAliveTime = keepAliveTime;
        return this;
    }

    public ExecutorBuilder setQueueCapacity(final int queueCapacity) {
        this.queueCapacity = queueCapacity;
        return this;
    }

    public ExecutorBuilder setThreadFactory(final ThreadFactory threadFactory) {
        this.threadFactory = threadFactory;
        return this;
    }

    public ExecutorBuilder setHandler(final RejectedExecutionHandler handler) {
        this.handler = handler;
        return this;
    }

    public ExecutorBuilder setAllowCoreThreadTimeOut(final Boolean allowCoreThreadTimeOut) {
        this.allowCoreThreadTimeOut = allowCoreThreadTimeOut;
        return this;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof ExecutorBuilder)) {
            return false;
        } else {
            ExecutorBuilder other = (ExecutorBuilder)o;
            if (!other.canEqual(this)) {
                return false;
            } else if (this.getCorePoolSize() != other.getCorePoolSize()) {
                return false;
            } else if (this.getMaxPoolSize() != other.getMaxPoolSize()) {
                return false;
            } else if (this.getKeepAliveTime() != other.getKeepAliveTime()) {
                return false;
            } else if (this.getQueueCapacity() != other.getQueueCapacity()) {
                return false;
            } else {
                label57: {
                    Object this$threadFactory = this.getThreadFactory();
                    Object other$threadFactory = other.getThreadFactory();
                    if (this$threadFactory == null) {
                        if (other$threadFactory == null) {
                            break label57;
                        }
                    } else if (this$threadFactory.equals(other$threadFactory)) {
                        break label57;
                    }

                    return false;
                }

                Object this$handler = this.getHandler();
                Object other$handler = other.getHandler();
                if (this$handler == null) {
                    if (other$handler != null) {
                        return false;
                    }
                } else if (!this$handler.equals(other$handler)) {
                    return false;
                }

                Object this$allowCoreThreadTimeOut = this.getAllowCoreThreadTimeOut();
                Object other$allowCoreThreadTimeOut = other.getAllowCoreThreadTimeOut();
                if (this$allowCoreThreadTimeOut == null) {
                    if (other$allowCoreThreadTimeOut != null) {
                        return false;
                    }
                } else if (!this$allowCoreThreadTimeOut.equals(other$allowCoreThreadTimeOut)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ExecutorBuilder;
    }

    @Override
    public int hashCode() {
//        int PRIME = true;
        int result = 1;
        result = result * 59 + this.getCorePoolSize();
        result = result * 59 + this.getMaxPoolSize();
        result = result * 59 + this.getKeepAliveTime();
        result = result * 59 + this.getQueueCapacity();
        Object $threadFactory = this.getThreadFactory();
        result = result * 59 + ($threadFactory == null ? 43 : $threadFactory.hashCode());
        Object $handler = this.getHandler();
        result = result * 59 + ($handler == null ? 43 : $handler.hashCode());
        Object $allowCoreThreadTimeOut = this.getAllowCoreThreadTimeOut();
        result = result * 59 + ($allowCoreThreadTimeOut == null ? 43 : $allowCoreThreadTimeOut.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "ExecutorBuilder(corePoolSize=" + this.getCorePoolSize() + ", maxPoolSize=" + this.getMaxPoolSize() + ", keepAliveTime=" + this.getKeepAliveTime() + ", queueCapacity=" + this.getQueueCapacity() + ", threadFactory=" + this.getThreadFactory() + ", handler=" + this.getHandler() + ", allowCoreThreadTimeOut=" + this.getAllowCoreThreadTimeOut() + ")";
    }
}
