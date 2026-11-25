package com.hivemq.client.internal.mqtt.handler.auth;

import com.hivemq.client.internal.logging.InternalLogger;
import com.hivemq.client.internal.logging.InternalLoggerFactory;
import com.hivemq.client.internal.mqtt.MqttClientConfig;
import com.hivemq.client.internal.mqtt.datatypes.MqttUtf8StringImpl;
import com.hivemq.client.internal.mqtt.handler.disconnect.MqttDisconnectUtil;
import com.hivemq.client.internal.mqtt.handler.util.MqttTimeoutInboundHandler;
import com.hivemq.client.internal.mqtt.message.auth.MqttAuth;
import com.hivemq.client.internal.mqtt.message.auth.MqttAuthBuilder;
import com.hivemq.client.internal.util.Checks;
import com.hivemq.client.mqtt.mqtt5.auth.Mqtt5EnhancedAuthMechanism;
import com.hivemq.client.mqtt.mqtt5.exceptions.Mqtt5AuthException;
import com.hivemq.client.mqtt.mqtt5.message.auth.Mqtt5AuthReasonCode;
import com.hivemq.client.mqtt.mqtt5.message.disconnect.Mqtt5DisconnectReasonCode;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* JADX INFO: loaded from: classes10.dex */
abstract class AbstractMqttAuthHandler extends MqttTimeoutInboundHandler implements MqttAuthHandler {
    static final InternalLogger LOGGER = InternalLoggerFactory.getLogger(AbstractMqttAuthHandler.class);
    final Mqtt5EnhancedAuthMechanism authMechanism;
    final MqttClientConfig clientConfig;
    MqttAuthState state = MqttAuthState.NONE;

    enum MqttAuthState {
        NONE,
        WAIT_FOR_SERVER,
        IN_PROGRESS_INIT,
        IN_PROGRESS_RESPONSE,
        IN_PROGRESS_DONE
    }

    abstract void readAuthSuccess(ChannelHandlerContext channelHandlerContext, MqttAuth mqttAuth);

    abstract void readReAuth(ChannelHandlerContext channelHandlerContext, MqttAuth mqttAuth);

    AbstractMqttAuthHandler(MqttClientConfig mqttClientConfig, Mqtt5EnhancedAuthMechanism mqtt5EnhancedAuthMechanism) {
        this.clientConfig = mqttClientConfig;
        this.authMechanism = mqtt5EnhancedAuthMechanism;
    }

    final void readAuth(ChannelHandlerContext channelHandlerContext, MqttAuth mqttAuth) {
        cancelTimeout();
        if (validateAuth(channelHandlerContext, mqttAuth)) {
            int i = AnonymousClass1.$SwitchMap$com$hivemq$client$mqtt$mqtt5$message$auth$Mqtt5AuthReasonCode[((Mqtt5AuthReasonCode) mqttAuth.getReasonCode()).ordinal()];
            if (i == 1) {
                readAuthContinue(channelHandlerContext, mqttAuth);
            } else if (i == 2) {
                readAuthSuccess(channelHandlerContext, mqttAuth);
            } else {
                if (i != 3) {
                    return;
                }
                readReAuth(channelHandlerContext, mqttAuth);
            }
        }
    }

    /* JADX INFO: renamed from: com.hivemq.client.internal.mqtt.handler.auth.AbstractMqttAuthHandler$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$hivemq$client$mqtt$mqtt5$message$auth$Mqtt5AuthReasonCode;

        static {
            int[] iArr = new int[Mqtt5AuthReasonCode.values().length];
            $SwitchMap$com$hivemq$client$mqtt$mqtt5$message$auth$Mqtt5AuthReasonCode = iArr;
            try {
                iArr[Mqtt5AuthReasonCode.CONTINUE_AUTHENTICATION.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$hivemq$client$mqtt$mqtt5$message$auth$Mqtt5AuthReasonCode[Mqtt5AuthReasonCode.SUCCESS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$hivemq$client$mqtt$mqtt5$message$auth$Mqtt5AuthReasonCode[Mqtt5AuthReasonCode.REAUTHENTICATE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    private boolean validateAuth(ChannelHandlerContext channelHandlerContext, MqttAuth mqttAuth) {
        if (mqttAuth.getMethod().equals(getMethod())) {
            return true;
        }
        MqttDisconnectUtil.disconnect(channelHandlerContext.channel(), Mqtt5DisconnectReasonCode.PROTOCOL_ERROR, new Mqtt5AuthException(mqttAuth, "Auth method in AUTH must be the same as in the CONNECT."));
        return false;
    }

    private void readAuthContinue(ChannelHandlerContext channelHandlerContext, final MqttAuth mqttAuth) {
        if (this.state != MqttAuthState.WAIT_FOR_SERVER) {
            MqttDisconnectUtil.disconnect(channelHandlerContext.channel(), Mqtt5DisconnectReasonCode.PROTOCOL_ERROR, new Mqtt5AuthException(mqttAuth, "Must not receive AUTH with reason code CONTINUE_AUTHENTICATION if client side AUTH is pending."));
            return;
        }
        final MqttAuthBuilder mqttAuthBuilder = new MqttAuthBuilder(Mqtt5AuthReasonCode.CONTINUE_AUTHENTICATION, getMethod());
        this.state = MqttAuthState.IN_PROGRESS_RESPONSE;
        callMechanismFutureResult(new Supplier() { // from class: com.hivemq.client.internal.mqtt.handler.auth.AbstractMqttAuthHandler$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.m2689x9a51b79e(mqttAuth, mqttAuthBuilder);
            }
        }, new Consumer() { // from class: com.hivemq.client.internal.mqtt.handler.auth.AbstractMqttAuthHandler$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.m2690xd332183d(mqttAuthBuilder, (ChannelHandlerContext) obj);
            }
        }, new BiConsumer() { // from class: com.hivemq.client.internal.mqtt.handler.auth.AbstractMqttAuthHandler$$ExternalSyntheticLambda2
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                MqttDisconnectUtil.disconnect(((ChannelHandlerContext) obj).channel(), Mqtt5DisconnectReasonCode.NOT_AUTHORIZED, new Mqtt5AuthException(mqttAuth, "Server auth not accepted."));
            }
        });
    }

    /* JADX INFO: renamed from: lambda$readAuthContinue$0$com-hivemq-client-internal-mqtt-handler-auth-AbstractMqttAuthHandler, reason: not valid java name */
    /* synthetic */ CompletableFuture m2689x9a51b79e(MqttAuth mqttAuth, MqttAuthBuilder mqttAuthBuilder) {
        return this.authMechanism.onContinue(this.clientConfig, mqttAuth, mqttAuthBuilder);
    }

    /* JADX INFO: renamed from: lambda$readAuthContinue$1$com-hivemq-client-internal-mqtt-handler-auth-AbstractMqttAuthHandler, reason: not valid java name */
    /* synthetic */ void m2690xd332183d(MqttAuthBuilder mqttAuthBuilder, ChannelHandlerContext channelHandlerContext) {
        this.state = MqttAuthState.WAIT_FOR_SERVER;
        channelHandlerContext.writeAndFlush(mqttAuthBuilder.build()).addListener((GenericFutureListener<? extends Future<? super Void>>) this);
    }

    void callMechanism(Runnable runnable) {
        try {
            runnable.run();
        } catch (Throwable th) {
            LOGGER.error("Auth cancelled. Unexpected exception thrown by auth mechanism.", th);
        }
    }

    void callMechanismFuture(Supplier<CompletableFuture<Void>> supplier, final Consumer<ChannelHandlerContext> consumer, final BiConsumer<ChannelHandlerContext, Throwable> biConsumer) {
        if (this.ctx == null) {
            return;
        }
        try {
            supplier.get().whenComplete(new BiConsumer() { // from class: com.hivemq.client.internal.mqtt.handler.auth.AbstractMqttAuthHandler$$ExternalSyntheticLambda3
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    this.f$0.m2685xbdb627bb(biConsumer, consumer, (Void) obj, (Throwable) obj2);
                }
            });
        } catch (Throwable th) {
            LOGGER.error("Auth cancelled. Unexpected exception thrown by auth mechanism.", th);
            biConsumer.accept(this.ctx, th);
        }
    }

    /* JADX INFO: renamed from: lambda$callMechanismFuture$4$com-hivemq-client-internal-mqtt-handler-auth-AbstractMqttAuthHandler, reason: not valid java name */
    /* synthetic */ void m2685xbdb627bb(final BiConsumer biConsumer, final Consumer consumer, Void r4, final Throwable th) {
        this.clientConfig.executeInEventLoop(new Runnable() { // from class: com.hivemq.client.internal.mqtt.handler.auth.AbstractMqttAuthHandler$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m2687x85a23159(th, biConsumer, consumer);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$null$3$com-hivemq-client-internal-mqtt-handler-auth-AbstractMqttAuthHandler, reason: not valid java name */
    /* synthetic */ void m2687x85a23159(Throwable th, BiConsumer biConsumer, Consumer consumer) {
        if (this.ctx == null) {
            return;
        }
        if (th != null) {
            LOGGER.error("Auth cancelled. Unexpected exception thrown by auth mechanism.", th);
            biConsumer.accept(this.ctx, th);
        } else {
            consumer.accept(this.ctx);
        }
    }

    void callMechanismFutureResult(Supplier<CompletableFuture<Boolean>> supplier, final Consumer<ChannelHandlerContext> consumer, final BiConsumer<ChannelHandlerContext, Throwable> biConsumer) {
        if (this.ctx == null) {
            return;
        }
        try {
            supplier.get().whenComplete(new BiConsumer() { // from class: com.hivemq.client.internal.mqtt.handler.auth.AbstractMqttAuthHandler$$ExternalSyntheticLambda6
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    this.f$0.m2686x208ed2dc(biConsumer, consumer, (Boolean) obj, (Throwable) obj2);
                }
            });
        } catch (Throwable th) {
            LOGGER.error("Auth cancelled. Unexpected exception thrown by auth mechanism.", th);
            biConsumer.accept(this.ctx, th);
        }
    }

    /* JADX INFO: renamed from: lambda$callMechanismFutureResult$6$com-hivemq-client-internal-mqtt-handler-auth-AbstractMqttAuthHandler, reason: not valid java name */
    /* synthetic */ void m2686x208ed2dc(final BiConsumer biConsumer, final Consumer consumer, final Boolean bool, final Throwable th) {
        this.clientConfig.executeInEventLoop(new Runnable() { // from class: com.hivemq.client.internal.mqtt.handler.auth.AbstractMqttAuthHandler$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m2688xf762f297(th, biConsumer, bool, consumer);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$null$5$com-hivemq-client-internal-mqtt-handler-auth-AbstractMqttAuthHandler, reason: not valid java name */
    /* synthetic */ void m2688xf762f297(Throwable th, BiConsumer biConsumer, Boolean bool, Consumer consumer) {
        if (this.ctx == null) {
            return;
        }
        if (th != null) {
            LOGGER.error("Auth cancelled. Unexpected exception thrown by auth mechanism.", th);
            biConsumer.accept(this.ctx, th);
        } else if (bool == null) {
            LOGGER.error("Auth cancelled. Unexpected null result returned by auth mechanism.");
            biConsumer.accept(this.ctx, new NullPointerException("Result returned by auth mechanism must not be null."));
        } else if (!bool.booleanValue()) {
            biConsumer.accept(this.ctx, null);
        } else {
            consumer.accept(this.ctx);
        }
    }

    MqttUtf8StringImpl getMethod() {
        return (MqttUtf8StringImpl) Checks.notImplemented(this.authMechanism.getMethod(), MqttUtf8StringImpl.class, "Auth method");
    }

    @Override // com.hivemq.client.internal.mqtt.handler.util.MqttTimeoutInboundHandler
    protected final long getTimeoutMs() {
        return TimeUnit.SECONDS.toMillis(this.authMechanism.getTimeout());
    }

    @Override // com.hivemq.client.internal.mqtt.handler.util.MqttTimeoutInboundHandler
    protected final Mqtt5DisconnectReasonCode getTimeoutReasonCode() {
        return Mqtt5DisconnectReasonCode.NOT_AUTHORIZED;
    }
}
