package service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RpcServiceManager {

    private final Map<String, Object> providerBeanMap = new ConcurrentHashMap<>();


    public void addService(String name, Object bean) {
        providerBeanMap.put(name, bean);
    }

    public Map<String, Object> getProviderBeanMap() {
        return providerBeanMap;
    }

    public Object getService(String name) {
        Object service = providerBeanMap.get(name);
        if (null == service) {
            throw new RuntimeException("no service found exception");
        }
        return service;
    }
}
