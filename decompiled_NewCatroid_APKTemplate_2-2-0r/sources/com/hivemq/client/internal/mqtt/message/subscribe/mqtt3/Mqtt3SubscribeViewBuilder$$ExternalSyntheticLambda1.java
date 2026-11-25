package com.hivemq.client.internal.mqtt.message.subscribe.mqtt3;

import com.hivemq.client.mqtt.mqtt3.message.subscribe.Mqtt3Subscription;
import java.util.function.Consumer;

/* JADX INFO: compiled from: D8$$SyntheticClass */
/* JADX INFO: loaded from: classes10.dex */
public final /* synthetic */ class Mqtt3SubscribeViewBuilder$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ Mqtt3SubscribeViewBuilder f$0;

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        this.f$0.addSubscription((Mqtt3Subscription) obj);
    }
}
