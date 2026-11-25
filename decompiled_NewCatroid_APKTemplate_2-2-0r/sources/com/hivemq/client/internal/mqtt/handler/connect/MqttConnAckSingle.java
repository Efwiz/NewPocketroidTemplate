package com.hivemq.client.internal.mqtt.handler.connect;

import androidx.lifecycle.LifecycleKt$$ExternalSyntheticBackportWithForwarding0;
import com.hivemq.client.internal.logging.InternalLogger;
import com.hivemq.client.internal.logging.InternalLoggerFactory;
import com.hivemq.client.internal.mqtt.MqttClientConfig;
import com.hivemq.client.internal.mqtt.MqttClientTransportConfigImpl;
import com.hivemq.client.internal.mqtt.exceptions.MqttClientStateExceptions;
import com.hivemq.client.internal.mqtt.lifecycle.MqttClientDisconnectedContextImpl;
import com.hivemq.client.internal.mqtt.lifecycle.MqttClientReconnector;
import com.hivemq.client.internal.mqtt.message.connect.MqttConnect;
import com.hivemq.client.internal.util.collections.ImmutableList;
import com.hivemq.client.mqtt.MqttClientState;
import com.hivemq.client.mqtt.exceptions.ConnectionFailedException;
import com.hivemq.client.mqtt.lifecycle.MqttClientDisconnectedContext;
import com.hivemq.client.mqtt.lifecycle.MqttClientDisconnectedListener;
import com.hivemq.client.mqtt.lifecycle.MqttDisconnectSource;
import com.hivemq.client.mqtt.mqtt5.message.connect.connack.Mqtt5ConnAck;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.EventLoop;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.internal.disposables.EmptyDisposable;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;

/* JADX INFO: loaded from: classes10.dex */
public class MqttConnAckSingle extends Single<Mqtt5ConnAck> {
    private static final InternalLogger LOGGER = InternalLoggerFactory.getLogger(MqttConnAckSingle.class);
    private final MqttClientConfig clientConfig;
    private final MqttConnect connect;

    public MqttConnAckSingle(MqttClientConfig mqttClientConfig, MqttConnect mqttConnect) {
        this.clientConfig = mqttClientConfig;
        this.connect = mqttConnect.setDefaults(mqttClientConfig);
    }

    @Override // io.reactivex.Single
    protected void subscribeActual(SingleObserver<? super Mqtt5ConnAck> singleObserver) {
        if (!LifecycleKt$$ExternalSyntheticBackportWithForwarding0.m(this.clientConfig.getRawState(), MqttClientState.DISCONNECTED, MqttClientState.CONNECTING)) {
            EmptyDisposable.error(MqttClientStateExceptions.alreadyConnected(), singleObserver);
            return;
        }
        MqttConnAckFlow mqttConnAckFlow = new MqttConnAckFlow(singleObserver);
        singleObserver.onSubscribe(mqttConnAckFlow.getDisposable());
        MqttClientConfig mqttClientConfig = this.clientConfig;
        connect(mqttClientConfig, this.connect, mqttConnAckFlow, mqttClientConfig.acquireEventLoop());
    }

    private static void connect(final MqttClientConfig mqttClientConfig, final MqttConnect mqttConnect, final MqttConnAckFlow mqttConnAckFlow, final EventLoop eventLoop) {
        if (mqttConnAckFlow.getDisposable().isDisposed()) {
            mqttClientConfig.releaseEventLoop();
            mqttClientConfig.getRawState().set(MqttClientState.DISCONNECTED);
        } else {
            Bootstrap bootstrap = mqttClientConfig.getClientComponent().connectionComponentBuilder().connect(mqttConnect).connAckFlow(mqttConnAckFlow).build().bootstrap();
            MqttClientTransportConfigImpl currentTransportConfig = mqttClientConfig.getCurrentTransportConfig();
            bootstrap.group(eventLoop).connect(currentTransportConfig.getRemoteAddress(), currentTransportConfig.getRawLocalAddress()).addListener(new GenericFutureListener() { // from class: com.hivemq.client.internal.mqtt.handler.connect.MqttConnAckSingle$$ExternalSyntheticLambda3
                @Override // io.netty.util.concurrent.GenericFutureListener
                public final void operationComplete(Future future) throws Exception {
                    MqttConnAckSingle.lambda$connect$1(eventLoop, mqttClientConfig, mqttConnect, mqttConnAckFlow, future);
                }
            });
        }
    }

    static /* synthetic */ void lambda$connect$1(final EventLoop eventLoop, final MqttClientConfig mqttClientConfig, final MqttConnect mqttConnect, final MqttConnAckFlow mqttConnAckFlow, Future future) throws Exception {
        Throwable thCause = future.cause();
        if (thCause != null) {
            final ConnectionFailedException connectionFailedException = new ConnectionFailedException(thCause);
            if (eventLoop.inEventLoop()) {
                reconnect(mqttClientConfig, MqttDisconnectSource.CLIENT, connectionFailedException, mqttConnect, mqttConnAckFlow, eventLoop);
            } else {
                eventLoop.execute(new Runnable() { // from class: com.hivemq.client.internal.mqtt.handler.connect.MqttConnAckSingle$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        MqttConnAckSingle.reconnect(mqttClientConfig, MqttDisconnectSource.CLIENT, connectionFailedException, mqttConnect, mqttConnAckFlow, eventLoop);
                    }
                });
            }
        }
    }

    public static void reconnect(MqttClientConfig mqttClientConfig, MqttDisconnectSource mqttDisconnectSource, Throwable th, MqttConnect mqttConnect, MqttConnAckFlow mqttConnAckFlow, EventLoop eventLoop) {
        if (mqttConnAckFlow.setDone()) {
            reconnect(mqttClientConfig, mqttDisconnectSource, th, mqttConnect, mqttConnAckFlow.getAttempts() + 1, mqttConnAckFlow, eventLoop);
        }
    }

    public static void reconnect(MqttClientConfig mqttClientConfig, MqttDisconnectSource mqttDisconnectSource, Throwable th, MqttConnect mqttConnect, EventLoop eventLoop) {
        reconnect(mqttClientConfig, mqttDisconnectSource, th, mqttConnect, 0, null, eventLoop);
    }

    private static void reconnect(final MqttClientConfig mqttClientConfig, MqttDisconnectSource mqttDisconnectSource, Throwable th, MqttConnect mqttConnect, int i, final MqttConnAckFlow mqttConnAckFlow, final EventLoop eventLoop) {
        final MqttClientReconnector mqttClientReconnector = new MqttClientReconnector(eventLoop, i, mqttConnect, mqttClientConfig.getCurrentTransportConfig());
        MqttClientDisconnectedContext mqttClientDisconnectedContextOf = MqttClientDisconnectedContextImpl.of(mqttClientConfig, mqttDisconnectSource, th, mqttClientReconnector);
        ImmutableList.ImmutableListIterator<MqttClientDisconnectedListener> it = mqttClientConfig.getDisconnectedListeners().iterator();
        while (it.hasNext()) {
            try {
                it.next().onDisconnected(mqttClientDisconnectedContextOf);
            } catch (Throwable th2) {
                LOGGER.error("Unexpected exception thrown by disconnected listener.", th2);
            }
        }
        if (mqttClientReconnector.isReconnect()) {
            mqttClientConfig.getRawState().set(MqttClientState.DISCONNECTED_RECONNECT);
            eventLoop.schedule(new Runnable() { // from class: com.hivemq.client.internal.mqtt.handler.connect.MqttConnAckSingle$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    MqttClientReconnector mqttClientReconnector2 = mqttClientReconnector;
                    mqttClientReconnector2.getFuture().whenComplete(new BiConsumer() { // from class: com.hivemq.client.internal.mqtt.handler.connect.MqttConnAckSingle$$ExternalSyntheticLambda0
                        @Override // java.util.function.BiConsumer
                        public final void accept(Object obj, Object obj2) {
                            MqttConnAckSingle.lambda$null$2(mqttClientReconnector2, mqttClientConfig, mqttConnAckFlow, eventLoop, obj, (Throwable) obj2);
                        }
                    });
                }
            }, mqttClientReconnector.getDelay(TimeUnit.NANOSECONDS), TimeUnit.NANOSECONDS);
            mqttClientConfig.setResubscribeIfSessionExpired(mqttClientReconnector.isResubscribeIfSessionExpired());
            mqttClientConfig.setRepublishIfSessionExpired(mqttClientReconnector.isRepublishIfSessionExpired());
            mqttClientReconnector.afterOnDisconnected();
            return;
        }
        mqttClientConfig.getRawState().set(MqttClientState.DISCONNECTED);
        mqttClientConfig.releaseEventLoop();
        if (mqttConnAckFlow != null) {
            mqttConnAckFlow.onError(th);
        }
    }

    static /* synthetic */ void lambda$null$2(MqttClientReconnector mqttClientReconnector, MqttClientConfig mqttClientConfig, MqttConnAckFlow mqttConnAckFlow, EventLoop eventLoop, Object obj, Throwable th) {
        if (mqttClientReconnector.isReconnect()) {
            if (LifecycleKt$$ExternalSyntheticBackportWithForwarding0.m(mqttClientConfig.getRawState(), MqttClientState.DISCONNECTED_RECONNECT, MqttClientState.CONNECTING_RECONNECT)) {
                mqttClientConfig.setCurrentTransportConfig(mqttClientReconnector.getTransportConfig());
                connect(mqttClientConfig, mqttClientReconnector.getConnect(), new MqttConnAckFlow(mqttConnAckFlow), eventLoop);
                return;
            }
            return;
        }
        if (LifecycleKt$$ExternalSyntheticBackportWithForwarding0.m(mqttClientConfig.getRawState(), MqttClientState.DISCONNECTED_RECONNECT, MqttClientState.DISCONNECTED)) {
            mqttClientConfig.releaseEventLoop();
            if (mqttConnAckFlow != null) {
                if (th == null) {
                    mqttConnAckFlow.onError(new ConnectionFailedException("Reconnect was cancelled."));
                } else {
                    mqttConnAckFlow.onError(new ConnectionFailedException(th));
                }
            }
        }
    }
}
