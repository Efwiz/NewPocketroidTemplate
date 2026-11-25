package com.hivemq.client.internal.mqtt.handler.proxy;

import com.hivemq.client.internal.mqtt.MqttProxyConfigImpl;
import com.hivemq.client.mqtt.MqttProxyProtocol;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;
import io.netty.handler.proxy.HttpProxyHandler;
import io.netty.handler.proxy.Socks4ProxyHandler;
import io.netty.handler.proxy.Socks5ProxyHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.NoSuchElementException;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes10.dex */
class MqttProxyAdapterHandler extends ChannelDuplexHandler implements FutureListener<Channel> {
    public static final String NAME = "proxy.adapter";
    private static final String PROXY_HANDLER_NAME = "proxy";
    private boolean handshakeDone = false;
    private final BiConsumer<Channel, Throwable> onError;
    private final Consumer<Channel> onSuccess;
    private final MqttProxyConfigImpl proxyConfig;
    private final InetSocketAddress serverAddress;

    @Override // io.netty.channel.ChannelHandlerAdapter
    public boolean isSharable() {
        return false;
    }

    public MqttProxyAdapterHandler(MqttProxyConfigImpl mqttProxyConfigImpl, InetSocketAddress inetSocketAddress, Consumer<Channel> consumer, BiConsumer<Channel, Throwable> biConsumer) {
        this.proxyConfig = mqttProxyConfigImpl;
        this.serverAddress = inetSocketAddress;
        this.onSuccess = consumer;
        this.onError = biConsumer;
    }

    @Override // io.netty.channel.ChannelDuplexHandler, io.netty.channel.ChannelOutboundHandler
    public void connect(ChannelHandlerContext channelHandlerContext, SocketAddress socketAddress, SocketAddress socketAddress2, ChannelPromise channelPromise) {
        HttpProxyHandler socks4ProxyHandler;
        HttpProxyHandler socks5ProxyHandler;
        Channel channel = channelHandlerContext.channel();
        String rawUsername = this.proxyConfig.getRawUsername();
        String rawPassword = this.proxyConfig.getRawPassword();
        int i = AnonymousClass1.$SwitchMap$com$hivemq$client$mqtt$MqttProxyProtocol[this.proxyConfig.getProtocol().ordinal()];
        if (i != 1) {
            if (i == 2) {
                socks5ProxyHandler = new Socks5ProxyHandler(socketAddress, rawUsername, rawPassword);
            } else if (i != 3) {
                if (setHandshakeDone(channel.pipeline())) {
                    this.onError.accept(channel, new IllegalStateException("Unknown proxy protocol " + this.proxyConfig.getProtocol()));
                    return;
                }
                return;
            } else if (rawUsername == null && rawPassword == null) {
                socks4ProxyHandler = new HttpProxyHandler(socketAddress);
            } else {
                if (rawUsername == null) {
                    rawUsername = "";
                }
                if (rawPassword == null) {
                    rawPassword = "";
                }
                socks5ProxyHandler = new HttpProxyHandler(socketAddress, rawUsername, rawPassword);
            }
            socks4ProxyHandler = socks5ProxyHandler;
        } else {
            socks4ProxyHandler = new Socks4ProxyHandler(socketAddress, rawUsername);
        }
        socks4ProxyHandler.setConnectTimeoutMillis(this.proxyConfig.getHandshakeTimeoutMs());
        socks4ProxyHandler.connectFuture().addListener(this);
        channel.pipeline().addFirst(PROXY_HANDLER_NAME, (ChannelHandler) socks4ProxyHandler);
        channelHandlerContext.connect(this.serverAddress, socketAddress2, channelPromise);
    }

    /* JADX INFO: renamed from: com.hivemq.client.internal.mqtt.handler.proxy.MqttProxyAdapterHandler$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$hivemq$client$mqtt$MqttProxyProtocol;

        static {
            int[] iArr = new int[MqttProxyProtocol.values().length];
            $SwitchMap$com$hivemq$client$mqtt$MqttProxyProtocol = iArr;
            try {
                iArr[MqttProxyProtocol.SOCKS_4.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$hivemq$client$mqtt$MqttProxyProtocol[MqttProxyProtocol.SOCKS_5.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$hivemq$client$mqtt$MqttProxyProtocol[MqttProxyProtocol.HTTP.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    @Override // io.netty.util.concurrent.GenericFutureListener
    public void operationComplete(Future<Channel> future) {
        if (future.isSuccess()) {
            Channel now = future.getNow();
            if (setHandshakeDone(now.pipeline())) {
                this.onSuccess.accept(now);
            }
        }
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelHandlerAdapter, io.netty.channel.ChannelHandler, io.netty.channel.ChannelInboundHandler
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable th) {
        if (setHandshakeDone(channelHandlerContext.pipeline())) {
            this.onError.accept(channelHandlerContext.channel(), th);
        } else {
            channelHandlerContext.fireExceptionCaught(th);
        }
    }

    private boolean setHandshakeDone(ChannelPipeline channelPipeline) {
        if (this.handshakeDone) {
            return false;
        }
        this.handshakeDone = true;
        channelPipeline.remove(this);
        try {
            channelPipeline.remove(PROXY_HANDLER_NAME);
        } catch (NoSuchElementException unused) {
        }
        return true;
    }
}
