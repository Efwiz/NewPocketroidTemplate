package com.hivemq.client.internal.mqtt.handler;

import io.netty.channel.Channel;
import java.util.function.BiConsumer;

/* JADX INFO: compiled from: D8$$SyntheticClass */
/* JADX INFO: loaded from: classes10.dex */
public final /* synthetic */ class MqttChannelInitializer$$ExternalSyntheticLambda1 implements BiConsumer {
    public final /* synthetic */ MqttChannelInitializer f$0;

    public /* synthetic */ MqttChannelInitializer$$ExternalSyntheticLambda1(MqttChannelInitializer mqttChannelInitializer) {
        this.f$0 = mqttChannelInitializer;
    }

    @Override // java.util.function.BiConsumer
    public final void accept(Object obj, Object obj2) {
        this.f$0.onError((Channel) obj, (Throwable) obj2);
    }
}
