package org.wlyyy.itrs.spring;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Configuration
public class ThreadPoolConfigurator {

    @Bean
    public ScheduledThreadPoolExecutor scheduledPoolExecutor() {

        return new ScheduledThreadPoolExecutor(8);
    }

    @Bean
    public TaskScheduler taskScheduler() {
        return new ConcurrentTaskScheduler(); // single threaded by default
    }

    @Bean
    public SimpleApplicationEventMulticaster applicationEventMulticaster(BeanFactory beanFactory) {
        SimpleApplicationEventMulticaster multicaster = new SimpleApplicationEventMulticaster(beanFactory);
        multicaster.setTaskExecutor(new ThreadPoolExecutor(8, 8, 5000, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), new MultiCasterThreadFactory()));
        return multicaster;
    }

    private static class MultiCasterThreadFactory implements ThreadFactory {
        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        MultiCasterThreadFactory() {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
            namePrefix = "event-multicaster-" + poolNumber.getAndIncrement() + "-thread-";
        }

        public Thread newThread(Runnable r) {
            if (r == null)
                return null;
            Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
            if (t.isDaemon())
                t.setDaemon(false);
            if (t.getPriority() != Thread.NORM_PRIORITY)
                t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }
    }
}
