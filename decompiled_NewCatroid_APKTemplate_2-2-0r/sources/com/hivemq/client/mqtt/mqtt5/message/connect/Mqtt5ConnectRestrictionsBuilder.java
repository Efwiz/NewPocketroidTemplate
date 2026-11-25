package com.hivemq.client.mqtt.mqtt5.message.connect;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt5ConnectRestrictionsBuilder extends Mqtt5ConnectRestrictionsBuilderBase<Mqtt5ConnectRestrictionsBuilder> {

    public interface Nested<P> extends Mqtt5ConnectRestrictionsBuilderBase<Nested<P>> {
        P applyRestrictions();
    }

    /* JADX INFO: renamed from: build */
    Mqtt5ConnectRestrictions mo2720build();
}
