package com.hivemq.client.internal.mqtt.handler.connect;

import com.hivemq.client.internal.logging.InternalLogger;
import com.hivemq.client.internal.logging.InternalLoggerFactory;
import com.hivemq.client.internal.mqtt.MqttClientConfig;
import com.hivemq.client.internal.mqtt.MqttClientConnectionConfig;
import com.hivemq.client.internal.mqtt.codec.decoder.MqttDecoder;
import com.hivemq.client.internal.mqtt.codec.encoder.MqttEncoder;
import com.hivemq.client.internal.mqtt.datatypes.MqttClientIdentifierImpl;
import com.hivemq.client.internal.mqtt.handler.MqttSession;
import com.hivemq.client.internal.mqtt.handler.disconnect.MqttDisconnectEvent;
import com.hivemq.client.internal.mqtt.handler.disconnect.MqttDisconnectUtil;
import com.hivemq.client.internal.mqtt.handler.ping.MqttPingHandler;
import com.hivemq.client.internal.mqtt.handler.util.MqttTimeoutInboundHandler;
import com.hivemq.client.internal.mqtt.ioc.ConnectionScope;
import com.hivemq.client.internal.mqtt.lifecycle.MqttClientConnectedContextImpl;
import com.hivemq.client.internal.mqtt.message.MqttMessage;
import com.hivemq.client.internal.mqtt.message.connect.MqttConnect;
import com.hivemq.client.internal.mqtt.message.connect.MqttConnectRestrictions;
import com.hivemq.client.internal.mqtt.message.connect.connack.MqttConnAck;
import com.hivemq.client.internal.mqtt.message.connect.connack.MqttConnAckRestrictions;
import com.hivemq.client.internal.util.collections.ImmutableList;
import com.hivemq.client.mqtt.MqttClientState;
import com.hivemq.client.mqtt.MqttVersion;
import com.hivemq.client.mqtt.lifecycle.MqttClientConnectedContext;
import com.hivemq.client.mqtt.lifecycle.MqttClientConnectedListener;
import com.hivemq.client.mqtt.lifecycle.MqttDisconnectSource;
import com.hivemq.client.mqtt.mqtt5.exceptions.Mqtt5ConnAckException;
import com.hivemq.client.mqtt.mqtt5.message.connect.connack.Mqtt5ConnAckReasonCode;
import com.hivemq.client.mqtt.mqtt5.message.disconnect.Mqtt5DisconnectReasonCode;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import javax.inject.Inject;

/* JADX INFO: loaded from: classes10.dex */
@ConnectionScope
public class MqttConnectHandler extends MqttTimeoutInboundHandler {
    private static final InternalLogger LOGGER = InternalLoggerFactory.getLogger(MqttConnectHandler.class);
    public static final String NAME = "connect";
    private final MqttClientConfig clientConfig;
    private final MqttConnAckFlow connAckFlow;
    private final MqttConnect connect;
    private long connectFlushTime;
    private boolean connectWritten = false;
    private final MqttDecoder decoder;
    private final MqttSession session;

    @Inject
    MqttConnectHandler(MqttConnect mqttConnect, MqttConnAckFlow mqttConnAckFlow, MqttClientConfig mqttClientConfig, MqttSession mqttSession, MqttDecoder mqttDecoder) {
        this.connect = mqttConnect;
        this.connAckFlow = mqttConnAckFlow;
        this.clientConfig = mqttClientConfig;
        this.session = mqttSession;
        this.decoder = mqttDecoder;
    }

    @Override // com.hivemq.client.internal.mqtt.handler.MqttConnectionAwareHandler, io.netty.channel.ChannelHandlerAdapter, io.netty.channel.ChannelHandler
    public void handlerAdded(ChannelHandlerContext channelHandlerContext) {
        super.handlerAdded(channelHandlerContext);
        if (channelHandlerContext.channel().isActive()) {
            writeConnect(channelHandlerContext);
        }
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void channelActive(ChannelHandlerContext channelHandlerContext) {
        writeConnect(channelHandlerContext);
        channelHandlerContext.fireChannelActive();
    }

    private void writeConnect(ChannelHandlerContext channelHandlerContext) {
        if (this.connectWritten) {
            return;
        }
        this.connectWritten = true;
        this.connectFlushTime = System.nanoTime();
        channelHandlerContext.writeAndFlush(this.connect.getRawEnhancedAuthMechanism() == null ? this.connect.createStateful(this.clientConfig.getRawClientIdentifier(), null) : this.connect).addListener((GenericFutureListener<? extends Future<? super Void>>) this);
    }

    @Override // com.hivemq.client.internal.mqtt.handler.util.MqttTimeoutInboundHandler
    protected void operationSuccessful(ChannelHandlerContext channelHandlerContext) {
        if (this.connect.getRawEnhancedAuthMechanism() == null) {
            scheduleTimeout(channelHandlerContext.channel());
        }
        channelHandlerContext.pipeline().addAfter(MqttEncoder.NAME, MqttDecoder.NAME, this.decoder);
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object obj) {
        cancelTimeout();
        if (obj instanceof MqttConnAck) {
            readConnAck((MqttConnAck) obj, channelHandlerContext.channel());
        } else {
            readOtherThanConnAck(obj, channelHandlerContext.channel());
        }
    }

    private void readConnAck(MqttConnAck mqttConnAck, Channel channel) {
        if (((Mqtt5ConnAckReasonCode) mqttConnAck.getReasonCode()).isError()) {
            MqttDisconnectUtil.fireDisconnectEvent(channel, new Mqtt5ConnAckException(mqttConnAck, "CONNECT failed as CONNACK contained an Error Code: " + mqttConnAck.getReasonCode() + "."), MqttDisconnectSource.SERVER);
            return;
        }
        if (validateClientIdentifier(mqttConnAck, channel)) {
            MqttClientConnectionConfig mqttClientConnectionConfigAddConnectionConfig = addConnectionConfig(mqttConnAck, channel);
            channel.pipeline().remove(this);
            ((MqttEncoder) channel.pipeline().get(MqttEncoder.NAME)).onConnected(mqttClientConnectionConfigAddConnectionConfig);
            this.session.startOrResume(mqttConnAck, mqttClientConnectionConfigAddConnectionConfig, channel.pipeline(), channel.eventLoop());
            int keepAlive = mqttClientConnectionConfigAddConnectionConfig.getKeepAlive();
            if (keepAlive > 0) {
                channel.pipeline().addAfter(MqttDecoder.NAME, MqttPingHandler.NAME, new MqttPingHandler(keepAlive, this.connectFlushTime, System.nanoTime()));
            }
            this.clientConfig.getRawState().set(MqttClientState.CONNECTED);
            ImmutableList<MqttClientConnectedListener> connectedListeners = this.clientConfig.getConnectedListeners();
            if (!connectedListeners.isEmpty()) {
                MqttClientConnectedContext mqttClientConnectedContextOf = MqttClientConnectedContextImpl.of(this.clientConfig, this.connect, mqttConnAck);
                ImmutableList.ImmutableListIterator<MqttClientConnectedListener> it = connectedListeners.iterator();
                while (it.hasNext()) {
                    try {
                        it.next().onConnected(mqttClientConnectedContextOf);
                    } catch (Throwable th) {
                        LOGGER.error("Unexpected exception thrown by connected listener.", th);
                    }
                }
            }
            this.connAckFlow.onSuccess(mqttConnAck);
        }
    }

    private void readOtherThanConnAck(Object obj, Channel channel) {
        if (obj instanceof MqttMessage) {
            MqttDisconnectUtil.disconnect(channel, Mqtt5DisconnectReasonCode.PROTOCOL_ERROR, ((MqttMessage) obj).getType() + " message must not be received before CONNACK");
        } else {
            MqttDisconnectUtil.close(channel, "No data must be received before CONNECT is sent");
        }
    }

    private boolean validateClientIdentifier(MqttConnAck mqttConnAck, Channel channel) {
        MqttClientIdentifierImpl rawClientIdentifier = this.clientConfig.getRawClientIdentifier();
        MqttClientIdentifierImpl rawAssignedClientIdentifier = mqttConnAck.getRawAssignedClientIdentifier();
        if (rawClientIdentifier == MqttClientIdentifierImpl.REQUEST_CLIENT_IDENTIFIER_FROM_SERVER) {
            if (this.clientConfig.getMqttVersion() == MqttVersion.MQTT_5_0 && rawAssignedClientIdentifier == null) {
                MqttDisconnectUtil.disconnect(channel, Mqtt5DisconnectReasonCode.PROTOCOL_ERROR, new Mqtt5ConnAckException(mqttConnAck, "Server did not assign a Client Identifier"));
                return false;
            }
        } else if (rawAssignedClientIdentifier != null) {
            LOGGER.warn("Server overwrote the Client Identifier {} with {}", rawClientIdentifier, rawAssignedClientIdentifier);
        }
        if (rawAssignedClientIdentifier == null) {
            return true;
        }
        this.clientConfig.setClientIdentifier(rawAssignedClientIdentifier);
        return true;
    }

    private MqttClientConnectionConfig addConnectionConfig(MqttConnAck mqttConnAck, Channel channel) {
        int rawServerKeepAlive = mqttConnAck.getRawServerKeepAlive();
        if (rawServerKeepAlive == -1) {
            rawServerKeepAlive = this.connect.getKeepAlive();
        }
        int i = rawServerKeepAlive;
        long rawSessionExpiryInterval = mqttConnAck.getRawSessionExpiryInterval();
        if (rawSessionExpiryInterval == -1) {
            rawSessionExpiryInterval = this.connect.getSessionExpiryInterval();
        }
        long j = rawSessionExpiryInterval;
        MqttConnectRestrictions restrictions = this.connect.getRestrictions();
        MqttConnAckRestrictions restrictions2 = mqttConnAck.getRestrictions();
        MqttClientConnectionConfig mqttClientConnectionConfig = new MqttClientConnectionConfig(this.clientConfig.getCurrentTransportConfig(), i, this.connect.isCleanStart(), this.connect.getSessionExpiryInterval() == 0, j, this.connect.getRawSimpleAuth() != null, this.connect.getRawWillPublish() != null, this.connect.getRawEnhancedAuthMechanism(), restrictions.getReceiveMaximum(), restrictions.getMaximumPacketSize(), restrictions.getTopicAliasMaximum(), restrictions.isRequestProblemInformation(), restrictions.isRequestResponseInformation(), Math.min(restrictions.getSendMaximum(), restrictions2.getReceiveMaximum()), Math.min(restrictions.getSendMaximumPacketSize(), restrictions2.getMaximumPacketSize()), Math.min(restrictions.getSendTopicAliasMaximum(), restrictions2.getTopicAliasMaximum()), restrictions2.getMaximumQos(), restrictions2.isRetainAvailable(), restrictions2.isWildcardSubscriptionAvailable(), restrictions2.isSharedSubscriptionAvailable(), restrictions2.areSubscriptionIdentifiersAvailable(), channel);
        this.clientConfig.setConnectionConfig(mqttClientConnectionConfig);
        return mqttClientConnectionConfig;
    }

    @Override // com.hivemq.client.internal.mqtt.handler.util.MqttTimeoutInboundHandler, com.hivemq.client.internal.mqtt.handler.MqttConnectionAwareHandler
    protected void onDisconnectEvent(ChannelHandlerContext channelHandlerContext, MqttDisconnectEvent mqttDisconnectEvent) {
        super.onDisconnectEvent(channelHandlerContext, mqttDisconnectEvent);
        MqttConnAckSingle.reconnect(this.clientConfig, mqttDisconnectEvent.getSource(), mqttDisconnectEvent.getCause(), this.connect, this.connAckFlow, channelHandlerContext.channel().eventLoop());
    }

    @Override // com.hivemq.client.internal.mqtt.handler.util.MqttTimeoutInboundHandler
    protected long getTimeoutMs() {
        return this.clientConfig.getCurrentTransportConfig().getMqttConnectTimeoutMs();
    }

    @Override // com.hivemq.client.internal.mqtt.handler.util.MqttTimeoutInboundHandler
    protected Mqtt5DisconnectReasonCode getTimeoutReasonCode() {
        return Mqtt5DisconnectReasonCode.PROTOCOL_ERROR;
    }

    @Override // com.hivemq.client.internal.mqtt.handler.util.MqttTimeoutInboundHandler
    protected String getTimeoutReasonString() {
        return "Timeout while waiting for CONNACK";
    }
}
