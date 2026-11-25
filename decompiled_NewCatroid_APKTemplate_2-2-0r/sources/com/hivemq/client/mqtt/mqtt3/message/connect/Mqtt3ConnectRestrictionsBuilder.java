package com.hivemq.client.mqtt.mqtt3.message.connect;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt3ConnectRestrictionsBuilder extends Mqtt3ConnectRestrictionsBuilderBase<Mqtt3ConnectRestrictionsBuilder> {

    public interface Nested<P> extends Mqtt3ConnectRestrictionsBuilderBase<Nested<P>> {
        P applyRestrictions();
    }

    Mqtt3ConnectRestrictions build();
}
