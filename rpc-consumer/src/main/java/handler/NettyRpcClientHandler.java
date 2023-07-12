package handler;

import codec.RpcMessageDecoder;
import codec.RpcMessageEncoder;
import entity.RpcRequest;
import entity.RpcResponse;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * RPC 客户端（建立连接，发送 RPC 请求，接收 RPC 响应）
 */
public class NettyRpcClientHandler extends SimpleChannelInboundHandler<RpcResponse> {


    private RpcResponse response;

    private String address;

    private Integer port;

    public NettyRpcClientHandler(String address, Integer port) {
        this.address = address;
        this.port = port;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcResponse response) throws Exception {
        this.response = response;
    }

    /**
     * 建立连接，发送请求，接收响应
     * 该方法的真正调用在代理类 RpcProxy 中（通过代理对此方法进行增强，屏蔽远程方法调用的细节）
     *
     * @param rpcRequest
     * @return
     */
    public RpcResponse send(RpcRequest rpcRequest) throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel socketChannel) throws Exception {
                    ChannelPipeline pipeline = socketChannel.pipeline();
                    pipeline.addLast(new RpcMessageEncoder(RpcRequest.class)); // 编码器
                    pipeline.addLast(new RpcMessageDecoder(RpcResponse.class)); // 解码器
                    pipeline.addLast(NettyRpcClientHandler.this); // 处理 RPC 响应
                }
            });
            bootstrap.option(ChannelOption.TCP_NODELAY, true);
            // 连接 RPC 服务器
            ChannelFuture channelFuture = bootstrap.connect(address, port).sync();
            // 写入 RPC 请求
            Channel channel = channelFuture.channel();
            channel.writeAndFlush(rpcRequest).sync();
            // 关闭连接
            channel.closeFuture().sync();
            // 返回 RPC 响应对象
            return response;
        } finally {
            group.shutdownGracefully();
        }
    }
}
