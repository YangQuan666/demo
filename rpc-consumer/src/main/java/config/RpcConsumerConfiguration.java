package config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import processor.RpcConsumerBeanPostProcessor;

@Configuration
public class RpcConsumerConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public RpcConsumerBeanPostProcessor rpcConsumerBeanPostProcessor() {
        return new RpcConsumerBeanPostProcessor();
    }
}
