package com.hivemq.client.internal.mqtt.handler.auth;

import com.hivemq.client.internal.mqtt.MqttClientConfig;
import com.hivemq.client.internal.mqtt.handler.disconnect.MqttDisconnectEvent;
import com.hivemq.client.internal.mqtt.handler.disconnect.MqttDisconnectUtil;
import com.hivemq.client.internal.mqtt.ioc.ConnectionScope;
import com.hivemq.client.internal.mqtt.message.auth.MqttAuth;
import com.hivemq.client.internal.mqtt.message.auth.MqttEnhancedAuthBuilder;
import com.hivemq.client.internal.mqtt.message.connect.MqttConnect;
import com.hivemq.client.internal.mqtt.message.connect.connack.MqttConnAck;
import com.hivemq.client.internal.netty.DefaultChannelOutboundHandler;
import com.hivemq.client.internal.util.Checks;
import com.hivemq.client.mqtt.exceptions.ConnectionFailedException;
import com.hivemq.client.mqtt.lifecycle.MqttDisconnectSource;
import com.hivemq.client.mqtt.mqtt5.auth.Mqtt5EnhancedAuthMechanism;
import com.hivemq.client.mqtt.mqtt5.exceptions.Mqtt5AuthException;
import com.hivemq.client.mqtt.mqtt5.exceptions.Mqtt5ConnAckException;
import com.hivemq.client.mqtt.mqtt5.message.auth.Mqtt5EnhancedAuth;
import com.hivemq.client.mqtt.mqtt5.message.connect.connack.Mqtt5ConnAckReasonCode;
import com.hivemq.client.mqtt.mqtt5.message.disconnect.Mqtt5DisconnectReasonCode;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import javax.inject.Inject;

/* JADX INFO: loaded from: classes10.dex */
@ConnectionScope
public class MqttConnectAuthHandler extends AbstractMqttAuthHandler implements DefaultChannelOutboundHandler {
    @Inject
    MqttConnectAuthHandler(MqttClientConfig mqttClientConfig, MqttConnect mqttConnect) {
        super(mqttClientConfig, (Mqtt5EnhancedAuthMechanism) Checks.stateNotNull(mqttConnect.getRawEnhancedAuthMechanism(), "Auth mechanism"));
    }

    @Override // com.hivemq.client.internal.netty.DefaultChannelOutboundHandler, io.netty.channel.ChannelOutboundHandler
    public void write(ChannelHandlerContext channelHandlerContext, Object obj, ChannelPromise channelPromise) {
        if (obj instanceof MqttConnect) {
            writeConnect((MqttConnect) obj, channelPromise);
        } else {
            channelHandlerContext.write(obj, channelPromise);
        }
    }

    private void writeConnect(final MqttConnect mqttConnect, final ChannelPromise channelPromise) {
        final MqttEnhancedAuthBuilder mqttEnhancedAuthBuilder = new MqttEnhancedAuthBuilder(getMethod());
        this.state = AbstractMqttAuthHandler.MqttAuthState.IN_PROGRESS_INIT;
        callMechanismFuture(new Supplier() { // from class: com.hivemq.client.internal.mqtt.handler.auth.MqttConnectAuthHandler$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.m2695xa65438c0(mqttConnect, mqttEnhancedAuthBuilder);
            }
        }, new Consumer() { // from class: com.hivemq.client.internal.mqtt.handler.auth.MqttConnectAuthHandler$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.m2696x97a5c841(mqttConnect, mqttEnhancedAuthBuilder, channelPromise, (ChannelHandlerContext) obj);
            }
        }, new BiConsumer() { // from class: com.hivemq.client.internal.mqtt.handler.auth.MqttConnectAuthHandler$$ExternalSyntheticLambda2
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                MqttDisconnectUtil.close(((ChannelHandlerContext) obj).channel(), new ConnectionFailedException((Throwable) obj2));
            }
        });
    }

    /* JADX INFO: renamed from: lambda$writeConnect$0$com-hivemq-client-internal-mqtt-handler-auth-MqttConnectAuthHandler, reason: not valid java name */
    /* synthetic */ CompletableFuture m2695xa65438c0(MqttConnect mqttConnect, MqttEnhancedAuthBuilder mqttEnhancedAuthBuilder) {
        return this.authMechanism.onAuth(this.clientConfig, mqttConnect, mqttEnhancedAuthBuilder);
    }

    /* JADX INFO: renamed from: lambda$writeConnect$1$com-hivemq-client-internal-mqtt-handler-auth-MqttConnectAuthHandler, reason: not valid java name */
    /* synthetic */ void m2696x97a5c841(MqttConnect mqttConnect, MqttEnhancedAuthBuilder mqttEnhancedAuthBuilder, ChannelPromise channelPromise, ChannelHandlerContext channelHandlerContext) {
        this.state = AbstractMqttAuthHandler.MqttAuthState.WAIT_FOR_SERVER;
        channelHandlerContext.writeAndFlush(mqttConnect.createStateful(this.clientConfig.getRawClientIdentifier(), mqttEnhancedAuthBuilder.build()), channelPromise).addListener((GenericFutureListener<? extends Future<? super Void>>) this);
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object obj) {
        if (obj instanceof MqttConnAck) {
            readConnAck(channelHandlerContext, (MqttConnAck) obj);
        } else if (obj instanceof MqttAuth) {
            readAuth(channelHandlerContext, (MqttAuth) obj);
        } else {
            channelHandlerContext.fireChannelRead(obj);
        }
    }

    private void readConnAck(ChannelHandlerContext channelHandlerContext, MqttConnAck mqttConnAck) {
        cancelTimeout();
        if (((Mqtt5ConnAckReasonCode) mqttConnAck.getReasonCode()).isError()) {
            readConnAckError(channelHandlerContext, mqttConnAck);
        } else if (validateConnAck(channelHandlerContext, mqttConnAck)) {
            readConnAckSuccess(channelHandlerContext, mqttConnAck);
        }
    }

    private void readConnAckError(ChannelHandlerContext channelHandlerContext, final MqttConnAck mqttConnAck) {
        callMechanism(new Runnable() { // from class: com.hivemq.client.internal.mqtt.handler.auth.MqttConnectAuthHandler$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m2692x28f1eb39(mqttConnAck);
            }
        });
        this.state = AbstractMqttAuthHandler.MqttAuthState.NONE;
        MqttDisconnectUtil.fireDisconnectEvent(channelHandlerContext.channel(), new Mqtt5ConnAckException(mqttConnAck, "CONNECT failed as CONNACK contained an Error Code: " + mqttConnAck.getReasonCode() + "."), MqttDisconnectSource.SERVER);
    }

    /* JADX INFO: renamed from: lambda$readConnAckError$3$com-hivemq-client-internal-mqtt-handler-auth-MqttConnectAuthHandler, reason: not valid java name */
    /* synthetic */ void m2692x28f1eb39(MqttConnAck mqttConnAck) {
        this.authMechanism.onAuthRejected(this.clientConfig, mqttConnAck);
    }

    private void readConnAckSuccess(ChannelHandlerContext channelHandlerContext, final MqttConnAck mqttConnAck) {
        if (this.state != AbstractMqttAuthHandler.MqttAuthState.WAIT_FOR_SERVER) {
            MqttDisconnectUtil.disconnect(channelHandlerContext.channel(), Mqtt5DisconnectReasonCode.PROTOCOL_ERROR, new Mqtt5ConnAckException(mqttConnAck, "Must not receive CONNACK with reason code SUCCESS if client side AUTH is pending."));
        } else {
            this.state = AbstractMqttAuthHandler.MqttAuthState.IN_PROGRESS_DONE;
            callMechanismFutureResult(new Supplier() { // from class: com.hivemq.client.internal.mqtt.handler.auth.MqttConnectAuthHandler$$ExternalSyntheticLambda3
                @Override // java.util.function.Supplier
                public final Object get() {
                    return this.f$0.m2693xdfccd2f5(mqttConnAck);
                }
            }, new Consumer() { // from class: com.hivemq.client.internal.mqtt.handler.auth.MqttConnectAuthHandler$$ExternalSyntheticLambda4
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.m2694xd11e6276(mqttConnAck, (ChannelHandlerContext) obj);
                }
            }, new BiConsumer() { // from class: com.hivemq.client.internal.mqtt.handler.auth.MqttConnectAuthHandler$$ExternalSyntheticLambda5
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    MqttDisconnectUtil.disconnect(((ChannelHandlerContext) obj).channel(), Mqtt5DisconnectReasonCode.NOT_AUTHORIZED, new Mqtt5ConnAckException(mqttConnAck, "Server CONNACK with reason code SUCCESS not accepted."));
                }
            });
        }
    }

    /* JADX INFO: renamed from: lambda$readConnAckSuccess$4$com-hivemq-client-internal-mqtt-handler-auth-MqttConnectAuthHandler, reason: not valid java name */
    /* synthetic */ CompletableFuture m2693xdfccd2f5(MqttConnAck mqttConnAck) {
        return this.authMechanism.onAuthSuccess(this.clientConfig, mqttConnAck);
    }

    /* JADX INFO: renamed from: lambda$readConnAckSuccess$5$com-hivemq-client-internal-mqtt-handler-auth-MqttConnectAuthHandler, reason: not valid java name */
    /* synthetic */ void m2694xd11e6276(MqttConnAck mqttConnAck, ChannelHandlerContext channelHandlerContext) {
        this.state = AbstractMqttAuthHandler.MqttAuthState.NONE;
        channelHandlerContext.pipeline().replace(this, MqttAuthHandler.NAME, new MqttReAuthHandler(this));
        channelHandlerContext.fireChannelRead((Object) mqttConnAck);
    }

    private boolean validateConnAck(ChannelHandlerContext channelHandlerContext, MqttConnAck mqttConnAck) {
        Mqtt5EnhancedAuth rawEnhancedAuth = mqttConnAck.getRawEnhancedAuth();
        if (rawEnhancedAuth == null) {
            MqttDisconnectUtil.disconnect(channelHandlerContext.channel(), Mqtt5DisconnectReasonCode.PROTOCOL_ERROR, new Mqtt5ConnAckException(mqttConnAck, "Auth method in CONNACK must be present."));
            return false;
        }
        if (rawEnhancedAuth.getMethod().equals(getMethod())) {
            return true;
        }
        MqttDisconnectUtil.disconnect(channelHandlerContext.channel(), Mqtt5DisconnectReasonCode.PROTOCOL_ERROR, new Mqtt5ConnAckException(mqttConnAck, "Auth method in CONNACK must be the same as in the CONNECT."));
        return false;
    }

    @Override // com.hivemq.client.internal.mqtt.handler.auth.AbstractMqttAuthHandler
    void readAuthSuccess(ChannelHandlerContext channelHandlerContext, MqttAuth mqttAuth) {
        MqttDisconnectUtil.disconnect(channelHandlerContext.channel(), Mqtt5DisconnectReasonCode.PROTOCOL_ERROR, new Mqtt5AuthException(mqttAuth, "Must not receive AUTH with reason code SUCCESS during connect auth."));
    }

    @Override // com.hivemq.client.internal.mqtt.handler.auth.AbstractMqttAuthHandler
    void readReAuth(ChannelHandlerContext channelHandlerContext, MqttAuth mqttAuth) {
        MqttDisconnectUtil.disconnect(channelHandlerContext.channel(), Mqtt5DisconnectReasonCode.PROTOCOL_ERROR, new Mqtt5AuthException(mqttAuth, "Must not receive AUTH with reason code REAUTHENTICATE during connect auth."));
    }

    @Override // com.hivemq.client.internal.mqtt.handler.util.MqttTimeoutInboundHandler, com.hivemq.client.internal.mqtt.handler.MqttConnectionAwareHandler
    protected void onDisconnectEvent(ChannelHandlerContext channelHandlerContext, final MqttDisconnectEvent mqttDisconnectEvent) {
        super.onDisconnectEvent(channelHandlerContext, mqttDisconnectEvent);
        if (this.state != AbstractMqttAuthHandler.MqttAuthState.NONE) {
            callMechanism(new Runnable() { // from class: com.hivemq.client.internal.mqtt.handler.auth.MqttConnectAuthHandler$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m2691x51348fe1(mqttDisconnectEvent);
                }
            });
            this.state = AbstractMqttAuthHandler.MqttAuthState.NONE;
        }
    }

    /* JADX INFO: renamed from: lambda$onDisconnectEvent$7$com-hivemq-client-internal-mqtt-handler-auth-MqttConnectAuthHandler, reason: not valid java name */
    /* synthetic */ void m2691x51348fe1(MqttDisconnectEvent mqttDisconnectEvent) {
        this.authMechanism.onAuthError(this.clientConfig, mqttDisconnectEvent.getCause());
    }

    @Override // com.hivemq.client.internal.mqtt.handler.util.MqttTimeoutInboundHandler
    protected String getTimeoutReasonString() {
        return "Timeout while waiting for AUTH or CONNACK.";
    }
}
