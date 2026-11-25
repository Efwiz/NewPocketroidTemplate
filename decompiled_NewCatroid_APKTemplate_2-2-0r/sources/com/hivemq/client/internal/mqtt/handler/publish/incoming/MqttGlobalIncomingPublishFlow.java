package com.hivemq.client.internal.mqtt.handler.publish.incoming;

import com.hivemq.client.internal.mqtt.MqttClientConfig;
import com.hivemq.client.internal.util.collections.HandleList;
import com.hivemq.client.mqtt.MqttGlobalPublishFilter;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish;
import org.reactivestreams.Subscriber;

/* JADX INFO: loaded from: classes10.dex */
public class MqttGlobalIncomingPublishFlow extends MqttIncomingPublishFlow {
    private final MqttGlobalPublishFilter filter;
    private HandleList.Handle<MqttGlobalIncomingPublishFlow> handle;

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

    MqttGlobalIncomingPublishFlow(Subscriber<? super Mqtt5Publish> subscriber, MqttClientConfig mqttClientConfig, MqttIncomingQosHandler mqttIncomingQosHandler, MqttGlobalPublishFilter mqttGlobalPublishFilter, boolean z) {
        super(subscriber, mqttClientConfig, mqttIncomingQosHandler, z);
        this.filter = mqttGlobalPublishFilter;
    }

    @Override // com.hivemq.client.internal.mqtt.handler.publish.incoming.MqttIncomingPublishFlow
    void runCancel() {
        this.incomingPublishService.incomingPublishFlows.cancelGlobal(this);
        super.runCancel();
    }

    MqttGlobalPublishFilter getFilter() {
        return this.filter;
    }

    void setHandle(HandleList.Handle<MqttGlobalIncomingPublishFlow> handle) {
        this.handle = handle;
    }

    HandleList.Handle<MqttGlobalIncomingPublishFlow> getHandle() {
        return this.handle;
    }
}
