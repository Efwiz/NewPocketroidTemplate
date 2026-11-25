package com.hivemq.client.internal.mqtt.handler.publish.incoming;

import com.hivemq.client.internal.mqtt.MqttClientConfig;
import com.hivemq.client.internal.mqtt.datatypes.MqttTopicFilterImpl;
import com.hivemq.client.internal.mqtt.handler.subscribe.MqttSubscriptionFlow;
import com.hivemq.client.internal.mqtt.message.subscribe.suback.MqttSubAck;
import com.hivemq.client.internal.util.collections.HandleList;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish;
import com.hivemq.client.rx.FlowableWithSingleSubscriber;
import org.reactivestreams.Subscriber;

/* JADX INFO: loaded from: classes10.dex */
public class MqttSubscribedPublishFlow extends MqttIncomingPublishFlow implements MqttSubscriptionFlow<MqttSubAck> {
    private final HandleList<MqttTopicFilterImpl> topicFilters;

    @Override // com.hivemq.client.internal.mqtt.handler.publish.incoming.MqttIncomingPublishFlow, io.reactivex.Emitter
    public /* bridge */ /* synthetic */ void onComplete() {
        super.onComplete();
    }

    @Override // com.hivemq.client.internal.mqtt.handler.publish.incoming.MqttIncomingPublishFlow, io.reactivex.Emitter
    public /* bridge */ /* synthetic */ void onError(Throwable th) {
        super.onError(th);
    }

    @Override // com.hivemq.client.internal.mqtt.handler.publish.incoming.MqttIncomingPublishFlow
    public /* bridge */ /* synthetic */ void onNext(Mqtt5Publish mqtt5Publish) {
        super.onNext(mqtt5Publish);
    }

    @Override // com.hivemq.client.internal.mqtt.handler.publish.incoming.MqttIncomingPublishFlow, org.reactivestreams.Subscription
    public /* bridge */ /* synthetic */ void request(long j) {
        super.request(j);
    }

    @Override // com.hivemq.client.internal.mqtt.handler.publish.incoming.MqttIncomingPublishFlow, java.lang.Runnable
    public /* bridge */ /* synthetic */ void run() {
        super.run();
    }

    MqttSubscribedPublishFlow(Subscriber<? super Mqtt5Publish> subscriber, MqttClientConfig mqttClientConfig, MqttIncomingQosHandler mqttIncomingQosHandler, boolean z) {
        super(subscriber, mqttClientConfig, mqttIncomingQosHandler, z);
        this.topicFilters = new HandleList<>();
    }

    @Override // com.hivemq.client.internal.mqtt.handler.subscribe.MqttSubscriptionFlow
    public void onSuccess(MqttSubAck mqttSubAck) {
        if (this.subscriber instanceof FlowableWithSingleSubscriber) {
            ((FlowableWithSingleSubscriber) this.subscriber).onSingle(mqttSubAck);
        }
    }

    @Override // com.hivemq.client.internal.mqtt.handler.publish.incoming.MqttIncomingPublishFlow
    void runCancel() {
        this.incomingPublishService.incomingPublishFlows.cancel(this);
        super.runCancel();
    }

    HandleList<MqttTopicFilterImpl> getTopicFilters() {
        return this.topicFilters;
    }
}
