package com.hivemq.client.mqtt.mqtt3.message.connect;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt3ConnectBuilder extends Mqtt3ConnectBuilderBase<Mqtt3ConnectBuilder> {

    public interface Nested<P> extends Mqtt3ConnectBuilderBase<Nested<P>> {
        P applyConnect();
    }

    public interface Send<P> extends Mqtt3ConnectBuilderBase<Send<P>> {
        P send();
    }

    Mqtt3Connect build();
}
