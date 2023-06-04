package processor;

import annotation.RpcProvider;
import jakarta.annotation.Resource;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import service.RpcRegistryService;
import service.RpcServiceManager;

@Component
public class RpcProviderBeanPostProcessor implements BeanPostProcessor {

    @Resource
    private RpcServiceManager rpcServiceManager;
    @Resource
    private RpcRegistryService rpcRegistryService;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        RpcProvider rpcProvider = AnnotationUtils.findAnnotation(bean.getClass(), RpcProvider.class);
        if (rpcProvider != null) {
            // 获取服务名称
            String name = rpcProvider.interfaceName().getName();
            // 获取服务版本
            String version = rpcProvider.version();
            // 保存服务到map中
            rpcServiceManager.addService(String.join("-", name, version), bean);
            // 注册服务
            rpcRegistryService.register(name + "-" + version);
        }
        return bean;
    }
}