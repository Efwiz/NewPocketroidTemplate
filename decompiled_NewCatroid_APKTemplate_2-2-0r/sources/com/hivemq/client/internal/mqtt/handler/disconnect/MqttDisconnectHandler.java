package com.hivemq.client.internal.mqtt.handler.disconnect;

import com.hivemq.client.internal.logging.InternalLogger;
import com.hivemq.client.internal.logging.InternalLoggerFactory;
import com.hivemq.client.internal.mqtt.MqttClientConfig;
import com.hivemq.client.internal.mqtt.MqttClientConnectionConfig;
import com.hivemq.client.internal.mqtt.datatypes.MqttUserPropertiesImpl;
import com.hivemq.client.internal.mqtt.exceptions.MqttClientStateExceptions;
import com.hivemq.client.internal.mqtt.handler.MqttConnectionAwareHandler;
import com.hivemq.client.internal.mqtt.handler.MqttSession;
import com.hivemq.client.internal.mqtt.handler.connect.MqttConnAckSingle;
import com.hivemq.client.internal.mqtt.ioc.ConnectionScope;
import com.hivemq.client.internal.mqtt.message.auth.MqttSimpleAuth;
import com.hivemq.client.internal.mqtt.message.connect.MqttConnect;
import com.hivemq.client.internal.mqtt.message.connect.MqttConnectRestrictions;
import com.hivemq.client.internal.mqtt.message.connect.connack.MqttConnAck;
import com.hivemq.client.internal.mqtt.message.disconnect.MqttDisconnect;
import com.hivemq.client.internal.mqtt.message.disconnect.MqttDisconnectBuilder;
import com.hivemq.client.internal.rx.CompletableFlow;
import com.hivemq.client.mqtt.MqttVersion;
import com.hivemq.client.mqtt.exceptions.ConnectionClosedException;
import com.hivemq.client.mqtt.lifecycle.MqttDisconnectSource;
import com.hivemq.client.mqtt.mqtt5.auth.Mqtt5EnhancedAuthMechanism;
import com.hivemq.client.mqtt.mqtt5.exceptions.Mqtt5ConnAckException;
import com.hivemq.client.mqtt.mqtt5.exceptions.Mqtt5DisconnectException;
import com.hivemq.client.mqtt.mqtt5.message.disconnect.Mqtt5DisconnectReasonCode;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoop;
import io.netty.channel.socket.DuplexChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.ScheduledFuture;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;

/* JADX INFO: loaded from: classes10.dex */
@ConnectionScope
public class MqttDisconnectHandler extends MqttConnectionAwareHandler {
    private static final int DISCONNECT_TIMEOUT = 10;
    public static final String NAME = "disconnect";
    private final MqttClientConfig clientConfig;
    private final MqttSession session;
    private Object state = null;
    private static final InternalLogger LOGGER = InternalLoggerFactory.getLogger(MqttDisconnectHandler.class);
    private static final Object STATE_CLOSED = new Object();

    @Override // com.hivemq.client.internal.mqtt.handler.MqttConnectionAwareHandler, io.netty.channel.ChannelHandlerAdapter
    public boolean isSharable() {
        return false;
    }

    @Inject
    MqttDisconnectHandler(MqttClientConfig mqttClientConfig, MqttSession mqttSession) {
        this.clientConfig = mqttClientConfig;
        this.session = mqttSession;
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object obj) {
        if (obj instanceof MqttDisconnect) {
            readDisconnect(channelHandlerContext, (MqttDisconnect) obj);
        } else if (obj instanceof MqttConnAck) {
            readConnAck(channelHandlerContext, (MqttConnAck) obj);
        } else {
            channelHandlerContext.fireChannelRead(obj);
        }
    }

    private void readDisconnect(ChannelHandlerContext channelHandlerContext, MqttDisconnect mqttDisconnect) {
        if (this.state == null) {
            this.state = STATE_CLOSED;
            MqttDisconnectUtil.fireDisconnectEvent(channelHandlerContext.channel(), new Mqtt5DisconnectException(mqttDisconnect, "Server sent DISCONNECT."), MqttDisconnectSource.SERVER);
        }
    }

    private void readConnAck(ChannelHandlerContext channelHandlerContext, MqttConnAck mqttConnAck) {
        if (this.state == null) {
            this.state = STATE_CLOSED;
            MqttDisconnectUtil.disconnect(channelHandlerContext.channel(), Mqtt5DisconnectReasonCode.PROTOCOL_ERROR, new Mqtt5ConnAckException(mqttConnAck, "Must not receive second CONNACK."));
        }
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void channelInactive(ChannelHandlerContext channelHandlerContext) {
        channelHandlerContext.fireChannelInactive();
        Object obj = this.state;
        if (obj == null) {
            this.state = STATE_CLOSED;
            MqttDisconnectUtil.fireDisconnectEvent(channelHandlerContext.channel(), new ConnectionClosedException("Server closed connection without DISCONNECT."), MqttDisconnectSource.SERVER);
        } else if (obj instanceof DisconnectingState) {
            DisconnectingState disconnectingState = (DisconnectingState) obj;
            this.state = STATE_CLOSED;
            disconnectingState.timeoutFuture.cancel(false);
            disconnected(disconnectingState.channel, disconnectingState.disconnectEvent);
            disconnectingState.disconnectEvent.getFlow().onComplete();
        }
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelHandlerAdapter, io.netty.channel.ChannelHandler, io.netty.channel.ChannelInboundHandler
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable th) {
        if (this.state == null) {
            this.state = STATE_CLOSED;
            MqttDisconnectUtil.fireDisconnectEvent(channelHandlerContext.channel(), new ConnectionClosedException(th), MqttDisconnectSource.CLIENT);
        } else {
            if (th instanceof IOException) {
                return;
            }
            LOGGER.warn("Exception while disconnecting: {}", th);
        }
    }

    public void disconnect(final MqttDisconnect mqttDisconnect, final CompletableFlow completableFlow) {
        if (this.clientConfig.executeInEventLoop(new Runnable() { // from class: com.hivemq.client.internal.mqtt.handler.disconnect.MqttDisconnectHandler$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m2708xc74b9149(mqttDisconnect, completableFlow);
            }
        })) {
            return;
        }
        completableFlow.onError(MqttClientStateExceptions.notConnected());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: writeDisconnect, reason: merged with bridge method [inline-methods] */
    public void m2708xc74b9149(MqttDisconnect mqttDisconnect, CompletableFlow completableFlow) {
        ChannelHandlerContext channelHandlerContext = this.ctx;
        if (channelHandlerContext != null && this.state == null) {
            this.state = STATE_CLOSED;
            MqttDisconnectUtil.fireDisconnectEvent(channelHandlerContext.channel(), new MqttDisconnectEvent.ByUser(mqttDisconnect, completableFlow));
        } else {
            completableFlow.onError(MqttClientStateExceptions.notConnected());
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.hivemq.client.internal.mqtt.handler.MqttConnectionAwareHandler
    protected void onDisconnectEvent(ChannelHandlerContext channelHandlerContext, final MqttDisconnectEvent mqttDisconnectEvent) {
        MqttClientConnectionConfig rawConnectionConfig;
        this.state = STATE_CLOSED;
        final Channel channel = channelHandlerContext.channel();
        if (mqttDisconnectEvent.getSource() == MqttDisconnectSource.SERVER) {
            disconnected(channel, mqttDisconnectEvent);
            channel.close();
            return;
        }
        MqttDisconnect disconnect = mqttDisconnectEvent.getDisconnect();
        if (disconnect != null) {
            long rawSessionExpiryInterval = disconnect.getRawSessionExpiryInterval();
            if (rawSessionExpiryInterval != -1 && (rawConnectionConfig = this.clientConfig.getRawConnectionConfig()) != null) {
                if (rawSessionExpiryInterval > 0 && rawConnectionConfig.isCleanStop()) {
                    LOGGER.warn("Session expiry interval must not be set in DISCONNECT if it was set to 0 in CONNECT");
                    disconnect = ((MqttDisconnectBuilder.Default) disconnect.extend().sessionExpiryInterval(0L)).build();
                } else {
                    rawConnectionConfig.setSessionExpiryInterval(rawSessionExpiryInterval);
                }
            }
            if (mqttDisconnectEvent instanceof MqttDisconnectEvent.ByUser) {
                final MqttDisconnectEvent.ByUser byUser = (MqttDisconnectEvent.ByUser) mqttDisconnectEvent;
                channelHandlerContext.writeAndFlush(disconnect).addListener(new GenericFutureListener() { // from class: com.hivemq.client.internal.mqtt.handler.disconnect.MqttDisconnectHandler$$ExternalSyntheticLambda1
                    @Override // io.netty.util.concurrent.GenericFutureListener
                    public final void operationComplete(Future future) throws Exception {
                        this.f$0.m2711x7296319e(channel, byUser, mqttDisconnectEvent, future);
                    }
                });
                return;
            } else if (this.clientConfig.getMqttVersion() == MqttVersion.MQTT_5_0) {
                channelHandlerContext.writeAndFlush(disconnect).addListener(new GenericFutureListener() { // from class: com.hivemq.client.internal.mqtt.handler.disconnect.MqttDisconnectHandler$$ExternalSyntheticLambda2
                    @Override // io.netty.util.concurrent.GenericFutureListener
                    public final void operationComplete(Future future) throws Exception {
                        this.f$0.m2712xa77823dc(channel, mqttDisconnectEvent, future);
                    }
                });
                return;
            } else {
                channel.close().addListener(new GenericFutureListener() { // from class: com.hivemq.client.internal.mqtt.handler.disconnect.MqttDisconnectHandler$$ExternalSyntheticLambda3
                    @Override // io.netty.util.concurrent.GenericFutureListener
                    public final void operationComplete(Future future) throws Exception {
                        this.f$0.m2713xc1e91cfb(channel, mqttDisconnectEvent, future);
                    }
                });
                return;
            }
        }
        channel.close().addListener(new GenericFutureListener() { // from class: com.hivemq.client.internal.mqtt.handler.disconnect.MqttDisconnectHandler$$ExternalSyntheticLambda4
            @Override // io.netty.util.concurrent.GenericFutureListener
            public final void operationComplete(Future future) throws Exception {
                this.f$0.m2714xdc5a161a(channel, mqttDisconnectEvent, future);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$onDisconnectEvent$2$com-hivemq-client-internal-mqtt-handler-disconnect-MqttDisconnectHandler, reason: not valid java name */
    /* synthetic */ void m2711x7296319e(final Channel channel, final MqttDisconnectEvent.ByUser byUser, final MqttDisconnectEvent mqttDisconnectEvent, Future future) throws Exception {
        if (future.isSuccess()) {
            ((DuplexChannel) channel).shutdownOutput().addListener(new GenericFutureListener() { // from class: com.hivemq.client.internal.mqtt.handler.disconnect.MqttDisconnectHandler$$ExternalSyntheticLambda5
                @Override // io.netty.util.concurrent.GenericFutureListener
                public final void operationComplete(Future future2) throws Exception {
                    this.f$0.m2709x88bbeadd(channel, byUser, mqttDisconnectEvent, future2);
                }
            });
        } else {
            disconnected(channel, mqttDisconnectEvent);
            byUser.getFlow().onError(new ConnectionClosedException(future.cause()));
        }
    }

    /* JADX INFO: renamed from: lambda$null$1$com-hivemq-client-internal-mqtt-handler-disconnect-MqttDisconnectHandler, reason: not valid java name */
    /* synthetic */ void m2709x88bbeadd(Channel channel, MqttDisconnectEvent.ByUser byUser, MqttDisconnectEvent mqttDisconnectEvent, Future future) throws Exception {
        if (future.isSuccess()) {
            this.state = new DisconnectingState(channel, byUser);
        } else {
            disconnected(channel, mqttDisconnectEvent);
            byUser.getFlow().onError(new ConnectionClosedException(future.cause()));
        }
    }

    /* JADX INFO: renamed from: lambda$null$3$com-hivemq-client-internal-mqtt-handler-disconnect-MqttDisconnectHandler, reason: not valid java name */
    /* synthetic */ void m2710xbd9ddd1b(Channel channel, MqttDisconnectEvent mqttDisconnectEvent, Future future) throws Exception {
        disconnected(channel, mqttDisconnectEvent);
    }

    /* JADX INFO: renamed from: lambda$onDisconnectEvent$4$com-hivemq-client-internal-mqtt-handler-disconnect-MqttDisconnectHandler, reason: not valid java name */
    /* synthetic */ void m2712xa77823dc(final Channel channel, final MqttDisconnectEvent mqttDisconnectEvent, Future future) throws Exception {
        channel.close().addListener(new GenericFutureListener() { // from class: com.hivemq.client.internal.mqtt.handler.disconnect.MqttDisconnectHandler$$ExternalSyntheticLambda6
            @Override // io.netty.util.concurrent.GenericFutureListener
            public final void operationComplete(Future future2) throws Exception {
                this.f$0.m2710xbd9ddd1b(channel, mqttDisconnectEvent, future2);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$onDisconnectEvent$5$com-hivemq-client-internal-mqtt-handler-disconnect-MqttDisconnectHandler, reason: not valid java name */
    /* synthetic */ void m2713xc1e91cfb(Channel channel, MqttDisconnectEvent mqttDisconnectEvent, Future future) throws Exception {
        disconnected(channel, mqttDisconnectEvent);
    }

    /* JADX INFO: renamed from: lambda$onDisconnectEvent$6$com-hivemq-client-internal-mqtt-handler-disconnect-MqttDisconnectHandler, reason: not valid java name */
    /* synthetic */ void m2714xdc5a161a(Channel channel, MqttDisconnectEvent mqttDisconnectEvent, Future future) throws Exception {
        disconnected(channel, mqttDisconnectEvent);
    }

    private void disconnected(Channel channel, MqttDisconnectEvent mqttDisconnectEvent) {
        MqttClientConnectionConfig rawConnectionConfig = this.clientConfig.getRawConnectionConfig();
        if (rawConnectionConfig != null) {
            this.session.expire(mqttDisconnectEvent.getCause(), rawConnectionConfig, channel.eventLoop());
            reconnect(mqttDisconnectEvent, rawConnectionConfig, channel.eventLoop());
            this.clientConfig.setConnectionConfig(null);
        }
    }

    private void reconnect(MqttDisconnectEvent mqttDisconnectEvent, MqttClientConnectionConfig mqttClientConnectionConfig, EventLoop eventLoop) {
        MqttClientConfig.ConnectDefaults connectDefaults = this.clientConfig.getConnectDefaults();
        Mqtt5EnhancedAuthMechanism rawEnhancedAuthMechanism = mqttClientConnectionConfig.getRawEnhancedAuthMechanism();
        int keepAlive = mqttClientConnectionConfig.getKeepAlive();
        boolean z = mqttClientConnectionConfig.getSessionExpiryInterval() == 0;
        long sessionExpiryInterval = mqttClientConnectionConfig.getSessionExpiryInterval();
        MqttConnectRestrictions mqttConnectRestrictions = new MqttConnectRestrictions(mqttClientConnectionConfig.getReceiveMaximum(), mqttClientConnectionConfig.getSendMaximum(), mqttClientConnectionConfig.getMaximumPacketSize(), mqttClientConnectionConfig.getSendMaximumPacketSize(), mqttClientConnectionConfig.getTopicAliasMaximum(), mqttClientConnectionConfig.getSendTopicAliasMaximum(), mqttClientConnectionConfig.isProblemInformationRequested(), mqttClientConnectionConfig.isResponseInformationRequested());
        MqttSimpleAuth simpleAuth = connectDefaults.getSimpleAuth();
        if (rawEnhancedAuthMechanism == null) {
            rawEnhancedAuthMechanism = connectDefaults.getEnhancedAuthMechanism();
        }
        MqttConnAckSingle.reconnect(this.clientConfig, mqttDisconnectEvent.getSource(), mqttDisconnectEvent.getCause(), new MqttConnect(keepAlive, z, sessionExpiryInterval, mqttConnectRestrictions, simpleAuth, rawEnhancedAuthMechanism, connectDefaults.getWillPublish(), MqttUserPropertiesImpl.NO_USER_PROPERTIES), eventLoop);
    }

    private static class DisconnectingState implements Runnable {
        private final Channel channel;
        private final MqttDisconnectEvent.ByUser disconnectEvent;
        private final ScheduledFuture<?> timeoutFuture;

        DisconnectingState(Channel channel, MqttDisconnectEvent.ByUser byUser) {
            this.channel = channel;
            this.disconnectEvent = byUser;
            this.timeoutFuture = channel.eventLoop().schedule((Runnable) this, 10L, TimeUnit.SECONDS);
        }

        @Override // java.lang.Runnable
        public void run() {
            this.channel.close();
        }
    }
}
