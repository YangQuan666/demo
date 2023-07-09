package processor;

import annotation.RpcConsumer;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cloud.zookeeper.discovery.ZookeeperInstance;
import org.springframework.stereotype.Component;
import client.RpcProxy;

import java.lang.reflect.Field;

@Component
public class RpcConsumerBeanPostProcessor implements BeanPostProcessor {

    @Autowired
    private ServiceDiscovery<ZookeeperInstance> serviceDiscovery;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        Class<?> clazz = bean.getClass();
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            RpcConsumer annotation = field.getAnnotation(RpcConsumer.class);
            if (annotation == null) {
                continue;
            }
            Class<?> interfaceClass = annotation.interfaceName();
            String version = annotation.version();

            RpcProxy rpcProxy = new RpcProxy(serviceDiscovery);
            Object proxy = rpcProxy.create(interfaceClass, version);
            field.setAccessible(true);
            try {
                field.set(bean, proxy);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return bean;
    }
}
