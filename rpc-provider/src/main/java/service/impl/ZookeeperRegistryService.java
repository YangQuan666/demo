package service.impl;

import jakarta.annotation.Resource;
import org.springframework.cloud.client.serviceregistry.ServiceRegistry;
import org.springframework.cloud.zookeeper.discovery.ZookeeperDiscoveryProperties;
import org.springframework.cloud.zookeeper.serviceregistry.ServiceInstanceRegistration;
import org.springframework.cloud.zookeeper.serviceregistry.ZookeeperRegistration;
import org.springframework.stereotype.Service;
import service.RpcRegistryService;

@Service
public class ZookeeperRegistryService implements RpcRegistryService {

    @Resource
    private ZookeeperDiscoveryProperties zookeeperDiscoveryProperties;

    @Resource
    private ServiceRegistry<ZookeeperRegistration> zookeeperServiceRegistry;

    @Override
    public void register(String name) {
        ServiceInstanceRegistration.RegistrationBuilder builder = ServiceInstanceRegistration.builder()
                .name(name)
                .address(zookeeperDiscoveryProperties.getInstanceHost())
                .port(zookeeperDiscoveryProperties.getInstancePort());
        zookeeperServiceRegistry.register(builder.build());
    }
}
