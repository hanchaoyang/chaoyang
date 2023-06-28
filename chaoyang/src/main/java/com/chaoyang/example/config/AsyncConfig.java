package com.chaoyang.example.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 异步配置
 *
 * @author 韩朝阳
 * @since 2023/5/25
 */
@Configuration
@EnableAsync
public class AsyncConfig {

    @Value("${async.executor.core-pool-size}")
    private int corePoolSize;

    @Value("${async.executor.max-pool-size}")
    private int maxPoolSize;

    @Value("${async.executor.queue-capacity}")
    private int queueCapacity;

    @Value("${async.executor.keep-alive-seconds}")
    private int keepAliveSeconds;

    @Value("${async.executor.thread-name-prefix}")
    private String threadNamePrefix;

    @Bean("threadPoolTaskExecutor")
    public Executor executor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();

        threadPoolTaskExecutor.setCorePoolSize(this.corePoolSize);
        threadPoolTaskExecutor.setMaxPoolSize(this.maxPoolSize);
        threadPoolTaskExecutor.setQueueCapacity(this.queueCapacity);
        threadPoolTaskExecutor.setKeepAliveSeconds(this.keepAliveSeconds);
        threadPoolTaskExecutor.setThreadNamePrefix(this.threadNamePrefix);
        threadPoolTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        return threadPoolTaskExecutor;
    }

}