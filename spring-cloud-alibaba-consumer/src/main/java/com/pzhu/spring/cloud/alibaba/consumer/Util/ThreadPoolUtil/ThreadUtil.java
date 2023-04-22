//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.pzhu.spring.cloud.alibaba.consumer.Util.ThreadPoolUtil;

import cn.hutool.core.thread.ConcurrencyTester;
import cn.hutool.core.thread.NamedThreadFactory;
import cn.hutool.core.thread.ThreadFactoryBuilder;
import com.alibaba.ttl.TransmittableThreadLocal;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

@Component
public class ThreadUtil {
    public ThreadUtil() {
    }

    public static void execute(Runnable runnable) {
        GlobalThreadPool.execute(runnable);
    }

    public static <T> Future<T> execAsync(Callable<T> task) {
        return GlobalThreadPool.submit(task);
    }

    public static Future<?> execAsync(Runnable runnable) {
        return GlobalThreadPool.submit(runnable);
    }

//    public static Runnable execDeamon(final Runnable runnable) {
//        Thread thread = new Thread(() -> {
//            RunnableWrapper.of(runnable).run();
//        });
//        thread.setDaemon(true);
//        thread.start();
//        return runnable;
//    }

    public static ThreadPoolTaskExecutor newExecutor(BeanFactory beanFactory, int threadSize) {
        ExecutorBuilder builder = ExecutorBuilder.create();
        if (threadSize > 0) {
            builder.setCorePoolSize(threadSize);
        }

        return builder.build(beanFactory);
    }

    public static ThreadPoolTaskExecutor newExecutor(BeanFactory beanFactory) {
        return ExecutorBuilder.create().build(beanFactory);
    }

    public static ThreadPoolTaskExecutor newExecutor(BeanFactory beanFactory, int corePoolSize, int maximumPoolSize) {
        return ExecutorBuilder.create().setCorePoolSize(corePoolSize).setMaxPoolSize(maximumPoolSize).build(beanFactory);
    }

    public static ThreadPoolTaskExecutor newExecutorByBlockingCoefficient(BeanFactory beanFactory, float blockingCoefficient) {
        if (!(blockingCoefficient >= 1.0F) && !(blockingCoefficient < 0.0F)) {
            int poolSize = (int)((float)Runtime.getRuntime().availableProcessors() / (1.0F - blockingCoefficient));
            return ExecutorBuilder.create().setCorePoolSize(poolSize).setMaxPoolSize(poolSize).setKeepAliveTime(0).build(beanFactory);
        } else {
            throw new IllegalArgumentException("[blockingCoefficient] must between 0 and 1, or equals 0.");
        }
    }

    public static boolean sleep(Number timeout, TimeUnit timeUnit) {
        try {
            timeUnit.sleep(timeout.longValue());
            return true;
        } catch (InterruptedException var3) {
            return false;
        }
    }

    public static boolean sleep(Number millis) {
        if (millis == null) {
            return true;
        } else {
            try {
                TimeUnit.MILLISECONDS.sleep(millis.longValue());
                return true;
            } catch (InterruptedException var2) {
                return false;
            }
        }
    }

    public static boolean safeSleep(Number millis) {
        long millisLong = millis.longValue();

        long before;
        long after;
        for(long done = 0L; done < millisLong; done += after - before) {
            before = System.currentTimeMillis();
            if (!sleep(millisLong - done)) {
                return false;
            }

            after = System.currentTimeMillis();
        }

        return true;
    }

    public static StackTraceElement[] getStackTrace() {
        return Thread.currentThread().getStackTrace();
    }

    public static StackTraceElement getStackTraceElement(int i) {
        StackTraceElement[] stackTrace = getStackTrace();
        if (i < 0) {
            i += stackTrace.length;
        }

        return stackTrace[i];
    }

    public static <T> ThreadLocal<T> createThreadLocal(boolean isInheritable) {
        return (ThreadLocal)(isInheritable ? new TransmittableThreadLocal() : new ThreadLocal());
    }

    public static ThreadFactoryBuilder createThreadFactoryBuilder() {
        return ThreadFactoryBuilder.create();
    }

    public static void interupt(Thread thread, boolean isJoin) {
        if (null != thread && !thread.isInterrupted()) {
            thread.interrupt();
            if (isJoin) {
                waitForDie(thread);
            }
        }

    }

    public static void waitForDie(Thread thread) {
        boolean dead = false;

        do {
            try {
                thread.join();
                dead = true;
            } catch (InterruptedException var3) {
            }
        } while(!dead);

    }

    public static Thread[] getThreads() {
        return getThreads(Thread.currentThread().getThreadGroup().getParent());
    }

    public static Thread[] getThreads(ThreadGroup group) {
        Thread[] slackList = new Thread[group.activeCount() * 2];
        int actualSize = group.enumerate(slackList);
        Thread[] result = new Thread[actualSize];
        System.arraycopy(slackList, 0, result, 0, actualSize);
        return result;
    }

    public static Thread getMainThread() {
        Thread[] var0 = getThreads();
        int var1 = var0.length;

        for(int var2 = 0; var2 < var1; ++var2) {
            Thread thread = var0[var2];
            if (thread.getId() == 1L) {
                return thread;
            }
        }

        return null;
    }

    public static ThreadGroup currentThreadGroup() {
        SecurityManager s = System.getSecurityManager();
        return null != s ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
    }

    public static ThreadFactory newNamedThreadFactory(String prefix, boolean isDeamon) {
        return new NamedThreadFactory(prefix, isDeamon);
    }

    public static ThreadFactory newNamedThreadFactory(String prefix, ThreadGroup threadGroup, boolean isDeamon) {
        return new NamedThreadFactory(prefix, threadGroup, isDeamon);
    }

    public static ThreadFactory newNamedThreadFactory(String prefix, ThreadGroup threadGroup, boolean isDeamon, UncaughtExceptionHandler handler) {
        return new NamedThreadFactory(prefix, threadGroup, isDeamon, handler);
    }

    public static void sync(Object obj) {
        synchronized(obj) {
            try {
                obj.wait();
            } catch (InterruptedException var4) {
            }

        }
    }

    public static ConcurrencyTester concurrencyTest(int threadSize, Runnable runnable) {
        return (new ConcurrencyTester(threadSize)).test(runnable);
    }
}
