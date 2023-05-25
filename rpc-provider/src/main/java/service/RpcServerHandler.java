package service;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Map;

public class RpcServerHandler extends SimpleChannelInboundHandler<Object> {

    public RpcServerHandler(Map<String, Object> providerBeanMap) {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {

    }
}
