package codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import serializer.ProtostuffSerializer;


public class RpcMessageEncoder extends MessageToByteEncoder<Object> {

    private Class<?> clazz;

    public RpcMessageEncoder(Class<?> clazz) {
        this.clazz = clazz;
    }
    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        byte[] data = ProtostuffSerializer.serialize(msg, clazz); // 将对象序列化为字节数组
        out.writeInt(data.length); // 将消息体长度写入消息头
        out.writeBytes(data); // 将数据写入消息体
    }
}
