package org.wlyyy.itrs.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;

import java.util.concurrent.ScheduledThreadPoolExecutor;

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
}
