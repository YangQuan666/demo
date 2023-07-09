package codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import serializer.ProtostuffSerializer;

import java.util.List;

public class RpcMessageDecoder extends ByteToMessageDecoder {

    private Class<?> clazz;

    public RpcMessageDecoder(Class<?> clazz) {
        this.clazz = clazz;
    }
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        // 标记当前readIndex的位置，以便后面重置 readIndex 的时候使用
        in.markReaderIndex();
        // 读取消息体（消息的长度）. readInt 操作会增加 readerIndex
        int length = in.readInt();
        // 如果可读字节数小于消息长度，说明是不完整的消息
        if (in.readableBytes() < length) {
            in.resetReaderIndex();
            return;
        }
        // 开始反序列化
        byte[] body = new byte[length];
        in.readBytes(body);
        Object obj = ProtostuffSerializer.deserialize(body, clazz);
        out.add(obj);
    }
}

