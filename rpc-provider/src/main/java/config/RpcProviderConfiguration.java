package config;

import listener.NettyServerListener;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import processor.RpcProviderBeanPostProcessor;
import service.RpcServiceManager;
import service.ZookeeperRegistryService;

@ComponentScan({"listener", "processor", "service"})
@Configuration
@EnableAutoConfiguration
public class RpcProviderConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public NettyServerListener nettyServerListener() {
        return new NettyServerListener();
    }

    @Bean
    @ConditionalOnMissingBean
    public RpcProviderBeanPostProcessor rpcProviderBeanPostProcessor() {
        return new RpcProviderBeanPostProcessor();
    }

    @Bean
    @ConditionalOnMissingBean
    public ZookeeperRegistryService zookeeperRegistryService() {
        return new ZookeeperRegistryService();
    }

    @Bean
    @ConditionalOnMissingBean
    public RpcServiceManager rpcServiceManager() {
        return new RpcServiceManager();
    }
}
