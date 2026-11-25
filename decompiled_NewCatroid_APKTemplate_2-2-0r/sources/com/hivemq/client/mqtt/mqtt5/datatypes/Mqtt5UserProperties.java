package com.hivemq.client.mqtt.mqtt5.datatypes;

import com.hivemq.client.internal.mqtt.datatypes.MqttUserPropertiesImpl;
import com.hivemq.client.internal.mqtt.datatypes.MqttUserPropertiesImplBuilder;
import com.hivemq.client.internal.mqtt.util.MqttChecks;
import java.util.Collection;
import java.util.List;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt5UserProperties {
    List<? extends Mqtt5UserProperty> asList();

    Mqtt5UserPropertiesBuilder extend();

    static Mqtt5UserProperties of() {
        return MqttUserPropertiesImpl.NO_USER_PROPERTIES;
    }

    static Mqtt5UserProperties of(Mqtt5UserProperty... mqtt5UserPropertyArr) {
        return MqttChecks.userProperties(mqtt5UserPropertyArr);
    }

    static Mqtt5UserProperties of(Collection<Mqtt5UserProperty> collection) {
        return MqttChecks.userProperties(collection);
    }

    static Mqtt5UserPropertiesBuilder builder() {
        return new MqttUserPropertiesImplBuilder.Default();
    }
}
