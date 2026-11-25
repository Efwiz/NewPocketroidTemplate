package com.hivemq.client.internal.mqtt.datatypes;

import com.hivemq.client.mqtt.mqtt5.datatypes.Mqtt5UserProperty;
import java.util.function.Consumer;

/* JADX INFO: compiled from: D8$$SyntheticClass */
/* JADX INFO: loaded from: classes10.dex */
public final /* synthetic */ class MqttUserPropertiesImplBuilder$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ MqttUserPropertiesImplBuilder f$0;

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        this.f$0.add((Mqtt5UserProperty) obj);
    }
}
