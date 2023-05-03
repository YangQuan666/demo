package aspect;

import annotation.RpcProvider;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


import java.util.Map;

@Component
public class RpcProviderBeanPostProcessor implements ApplicationContextAware {

    private Map<String, Object> providerBeanMap;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        providerBeanMap = applicationContext.getBeansWithAnnotation(RpcProvider.class);
    }

}