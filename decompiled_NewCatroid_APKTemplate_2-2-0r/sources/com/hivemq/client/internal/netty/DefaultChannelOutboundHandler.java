package com.hivemq.client.internal.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandler;
import io.netty.channel.ChannelPromise;
import java.net.SocketAddress;

/* JADX INFO: loaded from: classes10.dex */
public interface DefaultChannelOutboundHandler extends ChannelOutboundHandler {
    @Override // io.netty.channel.ChannelOutboundHandler
    default void bind(ChannelHandlerContext channelHandlerContext, SocketAddress socketAddress, ChannelPromise channelPromise) {
        channelHandlerContext.bind(socketAddress, channelPromise);
    }

    @Override // io.netty.channel.ChannelOutboundHandler
    default void connect(ChannelHandlerContext channelHandlerContext, SocketAddress socketAddress, SocketAddress socketAddress2, ChannelPromise channelPromise) {
        channelHandlerContext.connect(socketAddress, socketAddress2, channelPromise);
    }

    @Override // io.netty.channel.ChannelOutboundHandler
    default void disconnect(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) {
        channelHandlerContext.disconnect(channelPromise);
    }

    @Override // io.netty.channel.ChannelOutboundHandler
    default void close(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) {
        channelHandlerContext.close(channelPromise);
    }

    @Override // io.netty.channel.ChannelOutboundHandler
    default void deregister(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) {
        channelHandlerContext.deregister(channelPromise);
    }

    @Override // io.netty.channel.ChannelOutboundHandler
    default void read(ChannelHandlerContext channelHandlerContext) {
        channelHandlerContext.read();
    }

    @Override // io.netty.channel.ChannelOutboundHandler
    default void write(ChannelHandlerContext channelHandlerContext, Object obj, ChannelPromise channelPromise) {
        channelHandlerContext.write(obj, channelPromise);
    }

    @Override // io.netty.channel.ChannelOutboundHandler
    default void flush(ChannelHandlerContext channelHandlerContext) {
        channelHandlerContext.flush();
    }
}
