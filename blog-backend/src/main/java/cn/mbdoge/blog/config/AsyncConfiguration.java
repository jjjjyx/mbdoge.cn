package cn.mbdoge.blog.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author jyx
 */
@Configuration
@EnableAsync
@Slf4j
public class AsyncConfiguration {

    @Bean
    public ThreadPoolTaskExecutor singleTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix("Anno-Executor");
        executor.setMaxPoolSize(1);


        // 设置拒绝策略
        executor.setRejectedExecutionHandler((r, executor1) -> {
            // .....
            log.info("拒接提交");
        });
        // 使用预定义的异常处理类
        // executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }
}
