package com.hivemq.client.internal.mqtt.message.subscribe;

import com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5Subscription;
import java.util.function.Consumer;

/* JADX INFO: compiled from: D8$$SyntheticClass */
/* JADX INFO: loaded from: classes10.dex */
public final /* synthetic */ class MqttSubscribeBuilder$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ MqttSubscribeBuilder f$0;

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        this.f$0.addSubscription((Mqtt5Subscription) obj);
    }
}
