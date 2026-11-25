package com.hivemq.client.mqtt.mqtt5.advanced.interceptor;

import com.hivemq.client.mqtt.mqtt5.advanced.interceptor.Mqtt5ClientInterceptorsBuilderBase;
import com.hivemq.client.mqtt.mqtt5.advanced.interceptor.qos1.Mqtt5IncomingQos1Interceptor;
import com.hivemq.client.mqtt.mqtt5.advanced.interceptor.qos1.Mqtt5OutgoingQos1Interceptor;
import com.hivemq.client.mqtt.mqtt5.advanced.interceptor.qos2.Mqtt5IncomingQos2Interceptor;
import com.hivemq.client.mqtt.mqtt5.advanced.interceptor.qos2.Mqtt5OutgoingQos2Interceptor;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt5ClientInterceptorsBuilderBase<B extends Mqtt5ClientInterceptorsBuilderBase<B>> {
    B incomingQos1Interceptor(Mqtt5IncomingQos1Interceptor mqtt5IncomingQos1Interceptor);

    B incomingQos2Interceptor(Mqtt5IncomingQos2Interceptor mqtt5IncomingQos2Interceptor);

    B outgoingQos1Interceptor(Mqtt5OutgoingQos1Interceptor mqtt5OutgoingQos1Interceptor);

    B outgoingQos2Interceptor(Mqtt5OutgoingQos2Interceptor mqtt5OutgoingQos2Interceptor);
}
