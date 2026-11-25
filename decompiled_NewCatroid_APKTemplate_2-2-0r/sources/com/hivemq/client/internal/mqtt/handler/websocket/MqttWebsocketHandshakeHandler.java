package com.hivemq.client.internal.mqtt.handler.websocket;

import com.huawei.hms.ml.language.common.utils.LanguageCodeUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketHandshakeException;
import io.netty.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes10.dex */
class MqttWebsocketHandshakeHandler extends ChannelInboundHandlerAdapter {
    public static final String NAME = "ws.handshake";
    private final int handshakeTimeoutMs;
    private final WebSocketClientHandshaker handshaker;
    private final BiConsumer<Channel, Throwable> onError;
    private final Consumer<Channel> onSuccess;
    private ScheduledFuture<?> timeoutFuture;
    private boolean handshakeStarted = false;
    private boolean handshakeDone = false;

    @Override // io.netty.channel.ChannelHandlerAdapter
    public boolean isSharable() {
        return false;
    }

    MqttWebsocketHandshakeHandler(WebSocketClientHandshaker webSocketClientHandshaker, int i, Consumer<Channel> consumer, BiConsumer<Channel, Throwable> biConsumer) {
        this.handshaker = webSocketClientHandshaker;
        this.handshakeTimeoutMs = i;
        this.onSuccess = consumer;
        this.onError = biConsumer;
    }

    @Override // io.netty.channel.ChannelHandlerAdapter, io.netty.channel.ChannelHandler
    public void handlerAdded(ChannelHandlerContext channelHandlerContext) {
        if (channelHandlerContext.channel().isActive()) {
            startHandshake(channelHandlerContext);
        }
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void channelActive(ChannelHandlerContext channelHandlerContext) {
        startHandshake(channelHandlerContext);
        channelHandlerContext.fireChannelActive();
    }

    private void startHandshake(final ChannelHandlerContext channelHandlerContext) {
        if (this.handshakeStarted) {
            return;
        }
        this.handshakeStarted = true;
        if (this.handshakeTimeoutMs > 0) {
            this.timeoutFuture = channelHandlerContext.channel().eventLoop().schedule(new Runnable() { // from class: com.hivemq.client.internal.mqtt.handler.websocket.MqttWebsocketHandshakeHandler$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m2719x1fbb8799(channelHandlerContext);
                }
            }, this.handshakeTimeoutMs, TimeUnit.MILLISECONDS);
        }
        this.handshaker.handshake(channelHandlerContext.channel(), channelHandlerContext.voidPromise());
    }

    /* JADX INFO: renamed from: lambda$startHandshake$0$com-hivemq-client-internal-mqtt-handler-websocket-MqttWebsocketHandshakeHandler, reason: not valid java name */
    /* synthetic */ void m2719x1fbb8799(ChannelHandlerContext channelHandlerContext) {
        if (setHandshakeDone(channelHandlerContext)) {
            this.onError.accept(channelHandlerContext.channel(), new WebSocketHandshakeException("handshake timed out after " + this.handshakeTimeoutMs + LanguageCodeUtil.MS));
        }
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object obj) {
        if (obj instanceof FullHttpResponse) {
            finishHandshake(channelHandlerContext, (FullHttpResponse) obj);
        } else {
            channelHandlerContext.fireChannelRead(obj);
        }
    }

    private void finishHandshake(ChannelHandlerContext channelHandlerContext, FullHttpResponse fullHttpResponse) {
        if (setHandshakeDone(channelHandlerContext)) {
            try {
                this.handshaker.finishHandshake(channelHandlerContext.channel(), fullHttpResponse);
                this.onSuccess.accept(channelHandlerContext.channel());
            } catch (Throwable th) {
                this.onError.accept(channelHandlerContext.channel(), th);
            }
        }
        fullHttpResponse.release();
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void channelInactive(ChannelHandlerContext channelHandlerContext) {
        if (setHandshakeDone(channelHandlerContext)) {
            this.onError.accept(channelHandlerContext.channel(), new WebSocketHandshakeException("connection was closed during handshake"));
        }
        channelHandlerContext.fireChannelInactive();
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelHandlerAdapter, io.netty.channel.ChannelHandler, io.netty.channel.ChannelInboundHandler
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable th) {
        if (setHandshakeDone(channelHandlerContext)) {
            this.onError.accept(channelHandlerContext.channel(), th);
        } else {
            channelHandlerContext.fireExceptionCaught(th);
        }
    }

    private boolean setHandshakeDone(ChannelHandlerContext channelHandlerContext) {
        if (this.handshakeDone) {
            return false;
        }
        this.handshakeDone = true;
        channelHandlerContext.pipeline().remove(this);
        ScheduledFuture<?> scheduledFuture = this.timeoutFuture;
        if (scheduledFuture != null) {
            scheduledFuture.cancel(false);
            this.timeoutFuture = null;
        }
        return true;
    }
}
