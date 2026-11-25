package com.hivemq.client.mqtt.mqtt5.message.publish;

import com.hivemq.client.internal.mqtt.message.publish.MqttPublishBuilder;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt5WillPublish extends Mqtt5Publish {
    public static final long DEFAULT_DELAY_INTERVAL = 0;

    Mqtt5WillPublishBuilder.Complete extendAsWill();

    long getDelayInterval();

    static Mqtt5WillPublishBuilder builder() {
        return new MqttPublishBuilder.WillDefault();
    }
}
