package org.wlyyy.itrs.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.wlyyy.common.cache.JvmSimpleKeyValueCacheImpl;
import org.wlyyy.common.cache.SimpleKeyValueCache;

/**
 * 缓存配置
 *
 * @author wly
 */
@Configuration
public class CacheConfigurator {

    @Bean
    public SimpleKeyValueCache jvmSimpleKeyValueCache() {
        return new JvmSimpleKeyValueCacheImpl();
    }

    @Bean
    public LettuceConnectionFactory connectionFactory() {
        return new LettuceConnectionFactory();
    }
}
