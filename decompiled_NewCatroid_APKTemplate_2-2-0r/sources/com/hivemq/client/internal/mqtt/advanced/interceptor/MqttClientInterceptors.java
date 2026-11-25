package com.hivemq.client.internal.mqtt.advanced.interceptor;

import com.hivemq.client.mqtt.mqtt5.advanced.interceptor.Mqtt5ClientInterceptors;
import com.hivemq.client.mqtt.mqtt5.advanced.interceptor.qos1.Mqtt5IncomingQos1Interceptor;
import com.hivemq.client.mqtt.mqtt5.advanced.interceptor.qos1.Mqtt5OutgoingQos1Interceptor;
import com.hivemq.client.mqtt.mqtt5.advanced.interceptor.qos2.Mqtt5IncomingQos2Interceptor;
import com.hivemq.client.mqtt.mqtt5.advanced.interceptor.qos2.Mqtt5OutgoingQos2Interceptor;
import java.util.Objects;

/* JADX INFO: loaded from: classes10.dex */
public class MqttClientInterceptors implements Mqtt5ClientInterceptors {
    private final Mqtt5IncomingQos1Interceptor incomingQos1Interceptor;
    private final Mqtt5IncomingQos2Interceptor incomingQos2Interceptor;
    private final Mqtt5OutgoingQos1Interceptor outgoingQos1Interceptor;
    private final Mqtt5OutgoingQos2Interceptor outgoingQos2Interceptor;

    MqttClientInterceptors(Mqtt5IncomingQos1Interceptor mqtt5IncomingQos1Interceptor, Mqtt5OutgoingQos1Interceptor mqtt5OutgoingQos1Interceptor, Mqtt5IncomingQos2Interceptor mqtt5IncomingQos2Interceptor, Mqtt5OutgoingQos2Interceptor mqtt5OutgoingQos2Interceptor) {
        this.incomingQos1Interceptor = mqtt5IncomingQos1Interceptor;
        this.outgoingQos1Interceptor = mqtt5OutgoingQos1Interceptor;
        this.incomingQos2Interceptor = mqtt5IncomingQos2Interceptor;
        this.outgoingQos2Interceptor = mqtt5OutgoingQos2Interceptor;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.advanced.interceptor.Mqtt5ClientInterceptors
    public Mqtt5IncomingQos1Interceptor getIncomingQos1Interceptor() {
        return this.incomingQos1Interceptor;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.advanced.interceptor.Mqtt5ClientInterceptors
    public Mqtt5OutgoingQos1Interceptor getOutgoingQos1Interceptor() {
        return this.outgoingQos1Interceptor;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.advanced.interceptor.Mqtt5ClientInterceptors
    public Mqtt5IncomingQos2Interceptor getIncomingQos2Interceptor() {
        return this.incomingQos2Interceptor;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.advanced.interceptor.Mqtt5ClientInterceptors
    public Mqtt5OutgoingQos2Interceptor getOutgoingQos2Interceptor() {
        return this.outgoingQos2Interceptor;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.advanced.interceptor.Mqtt5ClientInterceptors
    public MqttClientInterceptorsBuilder.Default extend() {
        return new MqttClientInterceptorsBuilder.Default(this);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MqttClientInterceptors)) {
            return false;
        }
        MqttClientInterceptors mqttClientInterceptors = (MqttClientInterceptors) obj;
        return Objects.equals(this.incomingQos1Interceptor, mqttClientInterceptors.incomingQos1Interceptor) && Objects.equals(this.outgoingQos1Interceptor, mqttClientInterceptors.outgoingQos1Interceptor) && Objects.equals(this.incomingQos2Interceptor, mqttClientInterceptors.incomingQos2Interceptor) && Objects.equals(this.outgoingQos2Interceptor, mqttClientInterceptors.outgoingQos2Interceptor);
    }

    public int hashCode() {
        return (((((Objects.hashCode(this.incomingQos1Interceptor) * 31) + Objects.hashCode(this.outgoingQos1Interceptor)) * 31) + Objects.hashCode(this.incomingQos2Interceptor)) * 31) + Objects.hashCode(this.outgoingQos2Interceptor);
    }
}
