package com.hivemq.client.mqtt.mqtt5.datatypes;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt5UserPropertiesBuilder extends Mqtt5UserPropertiesBuilderBase<Mqtt5UserPropertiesBuilder> {

    public interface Nested<P> extends Mqtt5UserPropertiesBuilderBase<Nested<P>> {
        P applyUserProperties();
    }

    Mqtt5UserProperties build();
}
