package com.hivemq.client.internal.mqtt.message.unsubscribe.mqtt3;

import com.hivemq.client.mqtt.datatypes.MqttTopicFilter;
import java.util.function.Consumer;

/* JADX INFO: compiled from: D8$$SyntheticClass */
/* JADX INFO: loaded from: classes10.dex */
public final /* synthetic */ class Mqtt3UnsubscribeViewBuilder$$ExternalSyntheticLambda2 implements Consumer {
    public final /* synthetic */ Mqtt3UnsubscribeViewBuilder f$0;

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        this.f$0.addTopicFilter((MqttTopicFilter) obj);
    }
}
