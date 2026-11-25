package com.hivemq.client.mqtt.mqtt5.message.disconnect;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt5DisconnectBuilder extends Mqtt5DisconnectBuilderBase<Mqtt5DisconnectBuilder> {

    public interface Nested<P> extends Mqtt5DisconnectBuilderBase<Nested<P>> {
        P applyDisconnect();
    }

    public interface Send<P> extends Mqtt5DisconnectBuilderBase<Send<P>> {
        P send();
    }

    public interface SendVoid extends Mqtt5DisconnectBuilderBase<SendVoid> {
        void send();
    }

    Mqtt5Disconnect build();
}
