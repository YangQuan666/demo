package service;

import jakarta.annotation.Resource;
import org.springframework.cloud.client.serviceregistry.ServiceRegistry;
import org.springframework.cloud.zookeeper.discovery.ZookeeperDiscoveryProperties;
import org.springframework.cloud.zookeeper.serviceregistry.ServiceInstanceRegistration;
import org.springframework.cloud.zookeeper.serviceregistry.ZookeeperRegistration;
import org.springframework.stereotype.Service;

@Service
public class ZookeeperRegistryService {

    @Resource
    private ZookeeperDiscoveryProperties zookeeperDiscoveryProperties;

    @Resource
    private ServiceRegistry<ZookeeperRegistration> zookeeperServiceRegistry;

    public void register(String name) {
        ServiceInstanceRegistration.RegistrationBuilder builder = ServiceInstanceRegistration.builder()
                .name(name)
                .address(zookeeperDiscoveryProperties.getInstanceHost())
                .port(zookeeperDiscoveryProperties.getInstancePort());
        zookeeperServiceRegistry.register(builder.build());
    }
}
