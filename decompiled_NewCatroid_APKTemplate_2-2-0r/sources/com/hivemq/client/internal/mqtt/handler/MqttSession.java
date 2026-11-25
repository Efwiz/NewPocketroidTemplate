package com.hivemq.client.internal.mqtt.handler;

import com.hivemq.client.internal.mqtt.MqttClientConnectionConfig;
import com.hivemq.client.internal.mqtt.codec.decoder.MqttDecoder;
import com.hivemq.client.internal.mqtt.handler.publish.incoming.MqttIncomingQosHandler;
import com.hivemq.client.internal.mqtt.handler.publish.outgoing.MqttOutgoingQosHandler;
import com.hivemq.client.internal.mqtt.handler.subscribe.MqttSubscriptionHandler;
import com.hivemq.client.internal.mqtt.ioc.ClientScope;
import com.hivemq.client.internal.mqtt.message.connect.connack.MqttConnAck;
import com.hivemq.client.mqtt.exceptions.MqttSessionExpiredException;
import com.hivemq.client.mqtt.mqtt5.exceptions.Mqtt5ConnAckException;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoop;
import io.netty.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;

/* JADX INFO: loaded from: classes10.dex */
@ClientScope
public class MqttSession {
    private ScheduledFuture<?> expireFuture;
    private boolean hasSession;
    private final MqttIncomingQosHandler incomingQosHandler;
    private final MqttOutgoingQosHandler outgoingQosHandler;
    private final MqttSubscriptionHandler subscriptionHandler;

    @Inject
    MqttSession(MqttSubscriptionHandler mqttSubscriptionHandler, MqttIncomingQosHandler mqttIncomingQosHandler, MqttOutgoingQosHandler mqttOutgoingQosHandler) {
        this.subscriptionHandler = mqttSubscriptionHandler;
        this.incomingQosHandler = mqttIncomingQosHandler;
        this.outgoingQosHandler = mqttOutgoingQosHandler;
    }

    public void startOrResume(MqttConnAck mqttConnAck, MqttClientConnectionConfig mqttClientConnectionConfig, ChannelPipeline channelPipeline, EventLoop eventLoop) {
        if (this.hasSession && !mqttConnAck.isSessionPresent()) {
            end(new MqttSessionExpiredException("Session expired as CONNACK did not contain the session present flag.", new Mqtt5ConnAckException(mqttConnAck, "Session expired as CONNACK did not contain the session present flag.")));
        }
        this.hasSession = true;
        ScheduledFuture<?> scheduledFuture = this.expireFuture;
        if (scheduledFuture != null) {
            scheduledFuture.cancel(false);
            this.expireFuture = null;
        }
        channelPipeline.addAfter(MqttDecoder.NAME, MqttSubscriptionHandler.NAME, this.subscriptionHandler);
        channelPipeline.addAfter(MqttDecoder.NAME, MqttIncomingQosHandler.NAME, this.incomingQosHandler);
        channelPipeline.addAfter(MqttDecoder.NAME, MqttOutgoingQosHandler.NAME, this.outgoingQosHandler);
        this.subscriptionHandler.onSessionStartOrResume(mqttClientConnectionConfig, eventLoop);
        this.incomingQosHandler.onSessionStartOrResume(mqttClientConnectionConfig, eventLoop);
        this.outgoingQosHandler.onSessionStartOrResume(mqttClientConnectionConfig, eventLoop);
    }

    public void expire(final Throwable th, MqttClientConnectionConfig mqttClientConnectionConfig, EventLoop eventLoop) {
        long sessionExpiryInterval = mqttClientConnectionConfig.getSessionExpiryInterval();
        if (sessionExpiryInterval == 0) {
            eventLoop.execute(new Runnable() { // from class: com.hivemq.client.internal.mqtt.handler.MqttSession$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m2683x96be9fe5(th);
                }
            });
        } else if (sessionExpiryInterval != 4294967295L) {
            this.expireFuture = eventLoop.schedule(new Runnable() { // from class: com.hivemq.client.internal.mqtt.handler.MqttSession$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m2684x23f95166(th);
                }
            }, (long) (TimeUnit.SECONDS.toMillis(sessionExpiryInterval) * 1.1d), TimeUnit.MILLISECONDS);
        }
    }

    /* JADX INFO: renamed from: lambda$expire$0$com-hivemq-client-internal-mqtt-handler-MqttSession, reason: not valid java name */
    /* synthetic */ void m2683x96be9fe5(Throwable th) {
        end(new MqttSessionExpiredException("Session expired as connection was closed.", th));
    }

    /* JADX INFO: renamed from: lambda$expire$1$com-hivemq-client-internal-mqtt-handler-MqttSession, reason: not valid java name */
    /* synthetic */ void m2684x23f95166(Throwable th) {
        if (this.expireFuture != null) {
            this.expireFuture = null;
            end(new MqttSessionExpiredException("Session expired after expiry interval", th));
        }
    }

    private void end(Throwable th) {
        if (this.hasSession) {
            this.hasSession = false;
            this.outgoingQosHandler.onSessionEnd(th);
            this.incomingQosHandler.onSessionEnd(th);
            this.subscriptionHandler.onSessionEnd(th);
        }
    }
}
