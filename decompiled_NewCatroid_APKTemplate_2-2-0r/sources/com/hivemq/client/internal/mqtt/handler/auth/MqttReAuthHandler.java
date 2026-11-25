package com.hivemq.client.internal.mqtt.handler.auth;

import com.hivemq.client.internal.mqtt.exceptions.MqttClientStateExceptions;
import com.hivemq.client.internal.mqtt.handler.disconnect.MqttDisconnectEvent;
import com.hivemq.client.internal.mqtt.handler.disconnect.MqttDisconnectUtil;
import com.hivemq.client.internal.mqtt.ioc.ConnectionScope;
import com.hivemq.client.internal.mqtt.message.auth.MqttAuth;
import com.hivemq.client.internal.mqtt.message.auth.MqttAuthBuilder;
import com.hivemq.client.internal.mqtt.message.disconnect.MqttDisconnect;
import com.hivemq.client.internal.rx.CompletableFlow;
import com.hivemq.client.mqtt.mqtt5.exceptions.Mqtt5AuthException;
import com.hivemq.client.mqtt.mqtt5.message.auth.Mqtt5AuthReasonCode;
import com.hivemq.client.mqtt.mqtt5.message.disconnect.Mqtt5DisconnectReasonCode;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* JADX INFO: loaded from: classes10.dex */
@ConnectionScope
public class MqttReAuthHandler extends AbstractMqttAuthHandler {
    private CompletableFlow flow;

    MqttReAuthHandler(MqttConnectAuthHandler mqttConnectAuthHandler) {
        super(mqttConnectAuthHandler.clientConfig, mqttConnectAuthHandler.authMechanism);
    }

    void reauth(final CompletableFlow completableFlow) {
        if (this.clientConfig.executeInEventLoop(new Runnable() { // from class: com.hivemq.client.internal.mqtt.handler.auth.MqttReAuthHandler$$ExternalSyntheticLambda11
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m2704xcd34a5b7(completableFlow);
            }
        })) {
            return;
        }
        completableFlow.onError(MqttClientStateExceptions.notConnected());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: writeReAuth, reason: merged with bridge method [inline-methods] */
    public void m2704xcd34a5b7(CompletableFlow completableFlow) {
        if (this.ctx == null) {
            completableFlow.onError(MqttClientStateExceptions.notConnected());
            return;
        }
        if (this.state != AbstractMqttAuthHandler.MqttAuthState.NONE) {
            completableFlow.onError(new UnsupportedOperationException("Reauth is still pending."));
            return;
        }
        this.flow = completableFlow;
        final MqttAuthBuilder mqttAuthBuilder = new MqttAuthBuilder(Mqtt5AuthReasonCode.REAUTHENTICATE, getMethod());
        this.state = AbstractMqttAuthHandler.MqttAuthState.IN_PROGRESS_INIT;
        callMechanismFuture(new Supplier() { // from class: com.hivemq.client.internal.mqtt.handler.auth.MqttReAuthHandler$$ExternalSyntheticLambda8
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.m2705x56cc1e31(mqttAuthBuilder);
            }
        }, new Consumer() { // from class: com.hivemq.client.internal.mqtt.handler.auth.MqttReAuthHandler$$ExternalSyntheticLambda9
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.m2706xd52d2210(mqttAuthBuilder, (ChannelHandlerContext) obj);
            }
        }, new BiConsumer() { // from class: com.hivemq.client.internal.mqtt.handler.auth.MqttReAuthHandler$$ExternalSyntheticLambda10
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.m2707xd1ef29ce((ChannelHandlerContext) obj, (Throwable) obj2);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$writeReAuth$1$com-hivemq-client-internal-mqtt-handler-auth-MqttReAuthHandler, reason: not valid java name */
    /* synthetic */ CompletableFuture m2705x56cc1e31(MqttAuthBuilder mqttAuthBuilder) {
        return this.authMechanism.onReAuth(this.clientConfig, mqttAuthBuilder);
    }

    /* JADX INFO: renamed from: lambda$writeReAuth$2$com-hivemq-client-internal-mqtt-handler-auth-MqttReAuthHandler, reason: not valid java name */
    /* synthetic */ void m2706xd52d2210(MqttAuthBuilder mqttAuthBuilder, ChannelHandlerContext channelHandlerContext) {
        this.state = AbstractMqttAuthHandler.MqttAuthState.WAIT_FOR_SERVER;
        channelHandlerContext.writeAndFlush(mqttAuthBuilder.build()).addListener((GenericFutureListener<? extends Future<? super Void>>) this);
    }

    /* JADX INFO: renamed from: lambda$null$3$com-hivemq-client-internal-mqtt-handler-auth-MqttReAuthHandler, reason: not valid java name */
    /* synthetic */ void m2697xc46ef868(Throwable th) {
        this.authMechanism.onReAuthError(this.clientConfig, th);
    }

    /* JADX INFO: renamed from: lambda$writeReAuth$4$com-hivemq-client-internal-mqtt-handler-auth-MqttReAuthHandler, reason: not valid java name */
    /* synthetic */ void m2707xd1ef29ce(ChannelHandlerContext channelHandlerContext, final Throwable th) {
        callMechanism(new Runnable() { // from class: com.hivemq.client.internal.mqtt.handler.auth.MqttReAuthHandler$$ExternalSyntheticLambda12
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m2697xc46ef868(th);
            }
        });
        this.state = AbstractMqttAuthHandler.MqttAuthState.NONE;
        this.flow.onError(th);
        this.flow = null;
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object obj) {
        if (obj instanceof MqttAuth) {
            readAuth(channelHandlerContext, (MqttAuth) obj);
        } else if (obj instanceof MqttDisconnect) {
            readDisconnect(channelHandlerContext, (MqttDisconnect) obj);
        } else {
            channelHandlerContext.fireChannelRead(obj);
        }
    }

    @Override // com.hivemq.client.internal.mqtt.handler.auth.AbstractMqttAuthHandler
    void readAuthSuccess(ChannelHandlerContext channelHandlerContext, final MqttAuth mqttAuth) {
        if (this.state != AbstractMqttAuthHandler.MqttAuthState.WAIT_FOR_SERVER) {
            MqttDisconnectUtil.disconnect(channelHandlerContext.channel(), Mqtt5DisconnectReasonCode.PROTOCOL_ERROR, new Mqtt5AuthException(mqttAuth, "Must not receive AUTH with reason code SUCCESS if client side AUTH is pending."));
        } else {
            this.state = AbstractMqttAuthHandler.MqttAuthState.IN_PROGRESS_DONE;
            callMechanismFutureResult(new Supplier() { // from class: com.hivemq.client.internal.mqtt.handler.auth.MqttReAuthHandler$$ExternalSyntheticLambda4
                @Override // java.util.function.Supplier
                public final Object get() {
                    return this.f$0.m2699xf5027e02(mqttAuth);
                }
            }, new Consumer() { // from class: com.hivemq.client.internal.mqtt.handler.auth.MqttReAuthHandler$$ExternalSyntheticLambda5
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.m2700x736381e1((ChannelHandlerContext) obj);
                }
            }, new BiConsumer() { // from class: com.hivemq.client.internal.mqtt.handler.auth.MqttReAuthHandler$$ExternalSyntheticLambda6
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    MqttDisconnectUtil.disconnect(((ChannelHandlerContext) obj).channel(), Mqtt5DisconnectReasonCode.NOT_AUTHORIZED, new Mqtt5AuthException(mqttAuth, "Server AUTH with reason code SUCCESS not accepted."));
                }
            });
        }
    }

    /* JADX INFO: renamed from: lambda$readAuthSuccess$5$com-hivemq-client-internal-mqtt-handler-auth-MqttReAuthHandler, reason: not valid java name */
    /* synthetic */ CompletableFuture m2699xf5027e02(MqttAuth mqttAuth) {
        return this.authMechanism.onReAuthSuccess(this.clientConfig, mqttAuth);
    }

    /* JADX INFO: renamed from: lambda$readAuthSuccess$6$com-hivemq-client-internal-mqtt-handler-auth-MqttReAuthHandler, reason: not valid java name */
    /* synthetic */ void m2700x736381e1(ChannelHandlerContext channelHandlerContext) {
        this.state = AbstractMqttAuthHandler.MqttAuthState.NONE;
        CompletableFlow completableFlow = this.flow;
        if (completableFlow != null) {
            if (!completableFlow.isCancelled()) {
                this.flow.onComplete();
            } else {
                LOGGER.warn("Reauth was successful but the Completable has been cancelled.");
            }
            this.flow = null;
        }
    }

    @Override // com.hivemq.client.internal.mqtt.handler.auth.AbstractMqttAuthHandler
    void readReAuth(ChannelHandlerContext channelHandlerContext, final MqttAuth mqttAuth) {
        if (!this.clientConfig.getAdvancedConfig().isAllowServerReAuth()) {
            MqttDisconnectUtil.disconnect(channelHandlerContext.channel(), Mqtt5DisconnectReasonCode.PROTOCOL_ERROR, new Mqtt5AuthException(mqttAuth, "Must not receive AUTH with reason code REAUTHENTICATE."));
        } else {
            if (this.state != AbstractMqttAuthHandler.MqttAuthState.NONE) {
                MqttDisconnectUtil.disconnect(channelHandlerContext.channel(), Mqtt5DisconnectReasonCode.PROTOCOL_ERROR, new Mqtt5AuthException(mqttAuth, "Must not receive AUTH with reason code REAUTHENTICATE if reauth is still pending."));
                return;
            }
            final MqttAuthBuilder mqttAuthBuilder = new MqttAuthBuilder(Mqtt5AuthReasonCode.CONTINUE_AUTHENTICATION, getMethod());
            this.state = AbstractMqttAuthHandler.MqttAuthState.IN_PROGRESS_INIT;
            callMechanismFutureResult(new Supplier() { // from class: com.hivemq.client.internal.mqtt.handler.auth.MqttReAuthHandler$$ExternalSyntheticLambda1
                @Override // java.util.function.Supplier
                public final Object get() {
                    return this.f$0.m2702x770a2499(mqttAuth, mqttAuthBuilder);
                }
            }, new Consumer() { // from class: com.hivemq.client.internal.mqtt.handler.auth.MqttReAuthHandler$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.m2703xf56b2878(mqttAuthBuilder, (ChannelHandlerContext) obj);
                }
            }, new BiConsumer() { // from class: com.hivemq.client.internal.mqtt.handler.auth.MqttReAuthHandler$$ExternalSyntheticLambda3
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    MqttDisconnectUtil.disconnect(((ChannelHandlerContext) obj).channel(), Mqtt5DisconnectReasonCode.NOT_AUTHORIZED, new Mqtt5AuthException(mqttAuth, "Server AUTH with reason code REAUTHENTICATE not accepted."));
                }
            });
        }
    }

    /* JADX INFO: renamed from: lambda$readReAuth$8$com-hivemq-client-internal-mqtt-handler-auth-MqttReAuthHandler, reason: not valid java name */
    /* synthetic */ CompletableFuture m2702x770a2499(MqttAuth mqttAuth, MqttAuthBuilder mqttAuthBuilder) {
        return this.authMechanism.onServerReAuth(this.clientConfig, mqttAuth, mqttAuthBuilder);
    }

    /* JADX INFO: renamed from: lambda$readReAuth$9$com-hivemq-client-internal-mqtt-handler-auth-MqttReAuthHandler, reason: not valid java name */
    /* synthetic */ void m2703xf56b2878(MqttAuthBuilder mqttAuthBuilder, ChannelHandlerContext channelHandlerContext) {
        this.state = AbstractMqttAuthHandler.MqttAuthState.WAIT_FOR_SERVER;
        channelHandlerContext.writeAndFlush(mqttAuthBuilder.build()).addListener((GenericFutureListener<? extends Future<? super Void>>) this);
    }

    private void readDisconnect(ChannelHandlerContext channelHandlerContext, final MqttDisconnect mqttDisconnect) {
        cancelTimeout();
        if (this.state != AbstractMqttAuthHandler.MqttAuthState.NONE) {
            callMechanism(new Runnable() { // from class: com.hivemq.client.internal.mqtt.handler.auth.MqttReAuthHandler$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m2701x87223a66(mqttDisconnect);
                }
            });
            this.state = AbstractMqttAuthHandler.MqttAuthState.NONE;
        }
        channelHandlerContext.fireChannelRead((Object) mqttDisconnect);
    }

    /* JADX INFO: renamed from: lambda$readDisconnect$11$com-hivemq-client-internal-mqtt-handler-auth-MqttReAuthHandler, reason: not valid java name */
    /* synthetic */ void m2701x87223a66(MqttDisconnect mqttDisconnect) {
        this.authMechanism.onReAuthRejected(this.clientConfig, mqttDisconnect);
    }

    @Override // com.hivemq.client.internal.mqtt.handler.util.MqttTimeoutInboundHandler, com.hivemq.client.internal.mqtt.handler.MqttConnectionAwareHandler
    protected void onDisconnectEvent(ChannelHandlerContext channelHandlerContext, final MqttDisconnectEvent mqttDisconnectEvent) {
        super.onDisconnectEvent(channelHandlerContext, mqttDisconnectEvent);
        if (this.state != AbstractMqttAuthHandler.MqttAuthState.NONE) {
            callMechanism(new Runnable() { // from class: com.hivemq.client.internal.mqtt.handler.auth.MqttReAuthHandler$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m2698xbfd1e418(mqttDisconnectEvent);
                }
            });
            this.state = AbstractMqttAuthHandler.MqttAuthState.NONE;
        }
        CompletableFlow completableFlow = this.flow;
        if (completableFlow != null) {
            completableFlow.onError(mqttDisconnectEvent.getCause());
            this.flow = null;
        }
    }

    /* JADX INFO: renamed from: lambda$onDisconnectEvent$12$com-hivemq-client-internal-mqtt-handler-auth-MqttReAuthHandler, reason: not valid java name */
    /* synthetic */ void m2698xbfd1e418(MqttDisconnectEvent mqttDisconnectEvent) {
        this.authMechanism.onReAuthError(this.clientConfig, mqttDisconnectEvent.getCause());
    }

    @Override // com.hivemq.client.internal.mqtt.handler.util.MqttTimeoutInboundHandler
    protected String getTimeoutReasonString() {
        return "Timeout while waiting for AUTH or DISCONNECT.";
    }
}
