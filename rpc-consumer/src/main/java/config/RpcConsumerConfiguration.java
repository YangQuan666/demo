package config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import processor.RpcConsumerBeanPostProcessor;

public class RpcConsumerConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public RpcConsumerBeanPostProcessor rpcConsumerBeanPostProcessor() {
        return new RpcConsumerBeanPostProcessor();
    }
}
