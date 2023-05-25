package processor;

import annotation.RpcProvider;
import com.ecwid.consul.v1.agent.model.NewService;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cloud.consul.serviceregistry.ConsulRegistration;
import org.springframework.cloud.consul.serviceregistry.ConsulServiceRegistry;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import service.RpcServerHandler;


import java.net.InetAddress;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

@Component
public class RpcProviderBeanPostProcessor implements BeanPostProcessor, InitializingBean {

    @Autowired
    private ConsulRegistration consulRegistration;

    @Autowired
    private ConsulServiceRegistry consulServiceRegistry;

    @Value("${server.port}")
    private int port;

    private String address;

    private Map<String, Object> providerBeanMap;

    public RpcProviderBeanPostProcessor() throws UnknownHostException {

        address = InetAddress.getLocalHost().getHostAddress();

        providerBeanMap = new HashMap<>();
    }


    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        RpcProvider rpcProvider = AnnotationUtils.findAnnotation(bean.getClass(), RpcProvider.class);
        if (rpcProvider != null) {
            // 获取服务名称
            String name = rpcProvider.interfaceName().getName();
            // 获取服务版本
            String version = rpcProvider.version();

            providerBeanMap.put(name + "-" + version, bean);
        }
        return bean;
    }

    @Override
    public void afterPropertiesSet() throws Exception {


        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup);
            serverBootstrap.channel(NioServerSocketChannel.class);
            serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    ChannelPipeline pipeline = socketChannel.pipeline();
//                    pipeline.addLast(new RpcDecoder(RpcRequest.class)); // 解码器
//                    pipeline.addLast(new RpcEncoder(RpcResponse.class)); // 编码器
                    pipeline.addLast(new RpcServerHandler(providerBeanMap)); // 处理 RPC 请求
                }
            });
            // 建立连接
//            ChannelFuture future = serverBootstrap.bind(address, port).sync();

            // 注册服务
            for (String key : providerBeanMap.keySet()) {
                NewService service = consulRegistration.getService();
                service.setName(key);
            }
            consulServiceRegistry.register(consulRegistration);

            // 关闭 RPC 服务器
//            future.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}