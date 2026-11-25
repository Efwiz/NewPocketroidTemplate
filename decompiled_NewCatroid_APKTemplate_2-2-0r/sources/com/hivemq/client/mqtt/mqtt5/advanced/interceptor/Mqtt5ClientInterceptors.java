package com.hivemq.client.mqtt.mqtt5.advanced.interceptor;

import com.hivemq.client.internal.mqtt.advanced.interceptor.MqttClientInterceptorsBuilder;
import com.hivemq.client.mqtt.mqtt5.advanced.interceptor.qos1.Mqtt5IncomingQos1Interceptor;
import com.hivemq.client.mqtt.mqtt5.advanced.interceptor.qos1.Mqtt5OutgoingQos1Interceptor;
import com.hivemq.client.mqtt.mqtt5.advanced.interceptor.qos2.Mqtt5IncomingQos2Interceptor;
import com.hivemq.client.mqtt.mqtt5.advanced.interceptor.qos2.Mqtt5OutgoingQos2Interceptor;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt5ClientInterceptors {
    Mqtt5ClientInterceptorsBuilder extend();

    Mqtt5IncomingQos1Interceptor getIncomingQos1Interceptor();

    Mqtt5IncomingQos2Interceptor getIncomingQos2Interceptor();

    Mqtt5OutgoingQos1Interceptor getOutgoingQos1Interceptor();

    Mqtt5OutgoingQos2Interceptor getOutgoingQos2Interceptor();

    static Mqtt5ClientInterceptorsBuilder builder() {
        return new MqttClientInterceptorsBuilder.Default();
    }
}
