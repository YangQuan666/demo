package handler;

import entity.RpcRequest;
import entity.RpcResponse;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;


/**
 * RPC 服务端处理器，接收请求并响应
 */
public class NettyRpcServerHandler extends SimpleChannelInboundHandler<RpcRequest> {

    private static final Logger log = LoggerFactory.getLogger(NettyRpcServerHandler.class);

    // 存储服务名称及服务对象之间的映射关系
    private final Map<String, Object> handlerMap;

    public NettyRpcServerHandler(Map<String, Object> handlerMap) {
        this.handlerMap = handlerMap;
    }

    /**
     * 处理/响应客户端的请求消息
     *
     * @param ctx        context
     * @param rpcRequest rpc请求
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequest rpcRequest) {
        RpcResponse rpcResponse = new RpcResponse();
        rpcResponse.setRequestId(rpcRequest.getRequestId());
        try {
            Object result = handle(rpcRequest);
            rpcResponse.setData(result);
        } catch (Exception e) {
            log.error("handle result failure", e);
            rpcResponse.setCode(501);
            rpcResponse.setMessage("handle result failure");
        }
        // 写入 RPC 响应对象并自动关闭连接
        ctx.writeAndFlush(rpcResponse).addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("server caught exception", cause);
        ctx.close();
    }

    /**
     * 获取客户端请求的方法和参数，通过反射进行调用）
     *
     * @param rpcRequest
     * @return
     * @throws Exception
     */
    private Object handle(RpcRequest rpcRequest) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String name = rpcRequest.getInterfaceName();
        String version = rpcRequest.getVersion();

        // 获取服务对象
        Object serviceBean = handlerMap.get(String.join("-", name, version));
        if (serviceBean == null) {
            throw new RuntimeException(String.format("can not find service bean by key: %s", name));
        }
        // 获取反射调用所需的参数
        Class<?> serviceClass = serviceBean.getClass();
        String methodName = rpcRequest.getMethodName();
        Object[] parameters = rpcRequest.getParameters();
        Class<?>[] parameterTypes = rpcRequest.getParameterTypes();
        // 通过反射调用客户端请求的方法
        Method method = serviceClass.getMethod(methodName, parameterTypes);
        method.setAccessible(true);
        return method.invoke(serviceBean, parameters);
    }
}