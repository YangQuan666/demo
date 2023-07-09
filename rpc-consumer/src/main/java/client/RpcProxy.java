package client;


import entity.RpcRequest;
import entity.RpcResponse;
import handler.NettyRpcClientHandler;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceInstance;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cloud.zookeeper.discovery.ZookeeperInstance;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

/**
 * RPC请求代理类
 */
public class RpcProxy {

    private ServiceDiscovery<ZookeeperInstance> serviceDiscovery;

    /**
     * 该构造函数用于提供给用户通过配置文件注入服务发现组件
     */
    public RpcProxy(ServiceDiscovery<ZookeeperInstance> serviceDiscovery) {
        this.serviceDiscovery = serviceDiscovery;
    }

    public Object create(Class<?> interfaceClass, String version) {
        // 使用 CGLIB 动态代理机制
        Enhancer enhancer = new Enhancer();
        enhancer.setClassLoader(interfaceClass.getClassLoader());
        enhancer.setSuperclass(interfaceClass);
        enhancer.setCallback(
                (MethodInterceptor) (obj, method, args, proxy) -> {
                    // 创建 RPC 请求并设置属性
                    RpcRequest rpcRequest = new RpcRequest();
                    rpcRequest.setRequestId(UUID.randomUUID().toString());
                    rpcRequest.setMethodName(method.getName());
                    rpcRequest.setParameterTypes(method.getParameterTypes());
                    rpcRequest.setParameters(args);
                    rpcRequest.setInterfaceName(interfaceClass.getName());
                    rpcRequest.setVersion(version);

                    ServiceInstance<ZookeeperInstance> instance = getInstance(rpcRequest.getInterfaceName(), rpcRequest.getVersion());
                    if (instance == null) {
                        throw new RuntimeException("server address is empty");
                    }
                    // 创建 RPC 客户端对象，建立连接/发送请求/接收请求
                    NettyRpcClientHandler handler = new NettyRpcClientHandler(instance.getAddress(), instance.getPort());
                    RpcResponse rpcResponse = handler.send(rpcRequest);
                    if (rpcResponse == null) {
                        throw new RuntimeException("response is null");
                    }
                    return rpcResponse.getData();
                });
        return enhancer.create();
    }

    /**
     * 这里后续可以根据指定的算法获取需要的服务，目前默认选第一个
     *
     * @param interfaceName 接口名
     * @param version       版本
     * @return ZookeeperInstance
     */
    private ServiceInstance<ZookeeperInstance> getInstance(String interfaceName, String version) throws Exception {
        String name = String.join("-", interfaceName, version);
        Collection<ServiceInstance<ZookeeperInstance>> serviceInstances = serviceDiscovery.queryForInstances(name);
        Optional<ServiceInstance<ZookeeperInstance>> first = serviceInstances.stream().findFirst();
        return first.orElse(null);
    }
}
