package com.hivemq.client.internal.mqtt.handler.ssl;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.ssl.SslHandshakeCompletionEvent;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLHandshakeException;

/* JADX INFO: loaded from: classes10.dex */
class MqttSslAdapterHandler extends ChannelInboundHandlerAdapter {
    public static final String NAME = "ssl.adapter";
    private boolean handshakeDone = false;
    private final String host;
    private final HostnameVerifier hostnameVerifier;
    private final BiConsumer<Channel, Throwable> onError;
    private final Consumer<Channel> onSuccess;
    private final SslHandler sslHandler;

    @Override // io.netty.channel.ChannelHandlerAdapter
    public boolean isSharable() {
        return false;
    }

    public MqttSslAdapterHandler(SslHandler sslHandler, String str, HostnameVerifier hostnameVerifier, Consumer<Channel> consumer, BiConsumer<Channel, Throwable> biConsumer) {
        this.sslHandler = sslHandler;
        this.host = str;
        this.hostnameVerifier = hostnameVerifier;
        this.onSuccess = consumer;
        this.onError = biConsumer;
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void userEventTriggered(ChannelHandlerContext channelHandlerContext, Object obj) {
        if (obj instanceof SslHandshakeCompletionEvent) {
            handshakeComplete(channelHandlerContext, (SslHandshakeCompletionEvent) obj);
        } else {
            channelHandlerContext.fireUserEventTriggered(obj);
        }
    }

    private void handshakeComplete(ChannelHandlerContext channelHandlerContext, SslHandshakeCompletionEvent sslHandshakeCompletionEvent) {
        if (setHandshakeDone()) {
            if (sslHandshakeCompletionEvent.isSuccess()) {
                channelHandlerContext.pipeline().remove(this);
                HostnameVerifier hostnameVerifier = this.hostnameVerifier;
                if (hostnameVerifier == null || hostnameVerifier.verify(this.host, this.sslHandler.engine().getSession())) {
                    this.onSuccess.accept(channelHandlerContext.channel());
                    return;
                } else {
                    this.onError.accept(channelHandlerContext.channel(), new SSLHandshakeException("Hostname verification failed"));
                    return;
                }
            }
            this.onError.accept(channelHandlerContext.channel(), sslHandshakeCompletionEvent.cause());
        }
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelHandlerAdapter, io.netty.channel.ChannelHandler, io.netty.channel.ChannelInboundHandler
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable th) {
        channelHandlerContext.pipeline().remove(this);
        if (setHandshakeDone()) {
            this.onError.accept(channelHandlerContext.channel(), th);
        }
    }

    private boolean setHandshakeDone() {
        if (this.handshakeDone) {
            return false;
        }
        this.handshakeDone = true;
        return true;
    }
}
