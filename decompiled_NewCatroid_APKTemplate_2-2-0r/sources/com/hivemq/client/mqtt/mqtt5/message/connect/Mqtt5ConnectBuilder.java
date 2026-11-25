package com.hivemq.client.mqtt.mqtt5.message.connect;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt5ConnectBuilder extends Mqtt5ConnectBuilderBase<Mqtt5ConnectBuilder> {

    public interface Nested<P> extends Mqtt5ConnectBuilderBase<Nested<P>> {
        P applyConnect();
    }

    public interface Send<P> extends Mqtt5ConnectBuilderBase<Send<P>> {
        P send();
    }

    Mqtt5Connect build();
}
