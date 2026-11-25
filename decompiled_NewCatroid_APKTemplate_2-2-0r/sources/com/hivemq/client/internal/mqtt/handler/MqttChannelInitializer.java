package com.hivemq.client.internal.mqtt.handler;

import com.hivemq.client.internal.mqtt.MqttClientConfig;
import com.hivemq.client.internal.mqtt.MqttClientSslConfigImpl;
import com.hivemq.client.internal.mqtt.MqttProxyConfigImpl;
import com.hivemq.client.internal.mqtt.MqttWebSocketConfigImpl;
import com.hivemq.client.internal.mqtt.codec.encoder.MqttEncoder;
import com.hivemq.client.internal.mqtt.handler.auth.MqttAuthHandler;
import com.hivemq.client.internal.mqtt.handler.connect.MqttConnAckFlow;
import com.hivemq.client.internal.mqtt.handler.connect.MqttConnAckSingle;
import com.hivemq.client.internal.mqtt.handler.connect.MqttConnectHandler;
import com.hivemq.client.internal.mqtt.handler.disconnect.MqttDisconnectHandler;
import com.hivemq.client.internal.mqtt.handler.proxy.MqttProxyInitializer;
import com.hivemq.client.internal.mqtt.handler.ssl.MqttSslInitializer;
import com.hivemq.client.internal.mqtt.handler.websocket.MqttWebSocketInitializer;
import com.hivemq.client.internal.mqtt.ioc.ConnectionScope;
import com.hivemq.client.internal.mqtt.message.connect.MqttConnect;
import com.hivemq.client.mqtt.exceptions.ConnectionFailedException;
import com.hivemq.client.mqtt.lifecycle.MqttDisconnectSource;
import dagger.Lazy;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.SocketChannel;
import java.util.function.Consumer;
import javax.inject.Inject;

/* JADX INFO: loaded from: classes10.dex */
@ConnectionScope
public class MqttChannelInitializer extends ChannelInboundHandlerAdapter {
    private final MqttAuthHandler authHandler;
    private final MqttClientConfig clientConfig;
    private final MqttConnAckFlow connAckFlow;
    private final MqttConnect connect;
    private final MqttConnectHandler connectHandler;
    private final MqttDisconnectHandler disconnectHandler;
    private final MqttEncoder encoder;
    private final Lazy<MqttWebSocketInitializer> webSocketInitializer;

    @Override // io.netty.channel.ChannelHandlerAdapter
    public boolean isSharable() {
        return false;
    }

    @Inject
    MqttChannelInitializer(MqttClientConfig mqttClientConfig, MqttConnect mqttConnect, MqttConnAckFlow mqttConnAckFlow, MqttEncoder mqttEncoder, MqttConnectHandler mqttConnectHandler, MqttDisconnectHandler mqttDisconnectHandler, MqttAuthHandler mqttAuthHandler, Lazy<MqttWebSocketInitializer> lazy) {
        this.clientConfig = mqttClientConfig;
        this.connect = mqttConnect;
        this.connAckFlow = mqttConnAckFlow;
        this.encoder = mqttEncoder;
        this.connectHandler = mqttConnectHandler;
        this.disconnectHandler = mqttDisconnectHandler;
        this.authHandler = mqttAuthHandler;
        this.webSocketInitializer = lazy;
    }

    @Override // io.netty.channel.ChannelHandlerAdapter, io.netty.channel.ChannelHandler
    public void handlerAdded(ChannelHandlerContext channelHandlerContext) {
        channelHandlerContext.pipeline().remove(this);
        ((SocketChannel) channelHandlerContext.channel()).config().setAutoClose(false).setKeepAlive(true).setTcpNoDelay(true).setConnectTimeoutMillis(this.clientConfig.getCurrentTransportConfig().getSocketConnectTimeoutMs());
        initProxy(channelHandlerContext.channel());
    }

    private void initProxy(Channel channel) {
        MqttProxyConfigImpl rawProxyConfig = this.clientConfig.getCurrentTransportConfig().getRawProxyConfig();
        if (rawProxyConfig == null) {
            initSsl(channel);
        } else {
            MqttProxyInitializer.initChannel(channel, this.clientConfig, rawProxyConfig, new Consumer() { // from class: com.hivemq.client.internal.mqtt.handler.MqttChannelInitializer$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.initSsl((Channel) obj);
                }
            }, new MqttChannelInitializer$$ExternalSyntheticLambda1(this));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initSsl(Channel channel) {
        MqttClientSslConfigImpl rawSslConfig = this.clientConfig.getCurrentTransportConfig().getRawSslConfig();
        if (rawSslConfig == null) {
            initWebsocket(channel);
        } else {
            MqttSslInitializer.initChannel(channel, this.clientConfig, rawSslConfig, new Consumer() { // from class: com.hivemq.client.internal.mqtt.handler.MqttChannelInitializer$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.initWebsocket((Channel) obj);
                }
            }, new MqttChannelInitializer$$ExternalSyntheticLambda1(this));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initWebsocket(Channel channel) {
        MqttWebSocketConfigImpl rawWebSocketConfig = this.clientConfig.getCurrentTransportConfig().getRawWebSocketConfig();
        if (rawWebSocketConfig == null) {
            initMqtt(channel);
        } else {
            this.webSocketInitializer.get().initChannel(channel, this.clientConfig, rawWebSocketConfig, new Consumer() { // from class: com.hivemq.client.internal.mqtt.handler.MqttChannelInitializer$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.initMqtt((Channel) obj);
                }
            }, new MqttChannelInitializer$$ExternalSyntheticLambda1(this));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initMqtt(Channel channel) {
        channel.pipeline().addLast(MqttEncoder.NAME, this.encoder).addLast(MqttAuthHandler.NAME, this.authHandler).addLast(MqttConnectHandler.NAME, this.connectHandler).addLast(MqttDisconnectHandler.NAME, this.disconnectHandler);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onError(Channel channel, Throwable th) {
        channel.close();
        MqttConnAckSingle.reconnect(this.clientConfig, MqttDisconnectSource.CLIENT, new ConnectionFailedException(th), this.connect, this.connAckFlow, channel.eventLoop());
    }
}
