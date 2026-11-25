package com.hivemq.client.internal.mqtt.advanced.interceptor;

import com.hivemq.client.internal.mqtt.advanced.interceptor.MqttClientInterceptorsBuilder;
import com.hivemq.client.mqtt.mqtt5.advanced.interceptor.Mqtt5ClientInterceptors;
import com.hivemq.client.mqtt.mqtt5.advanced.interceptor.Mqtt5ClientInterceptorsBuilder;
import com.hivemq.client.mqtt.mqtt5.advanced.interceptor.Mqtt5ClientInterceptorsBuilderBase;
import com.hivemq.client.mqtt.mqtt5.advanced.interceptor.qos1.Mqtt5IncomingQos1Interceptor;
import com.hivemq.client.mqtt.mqtt5.advanced.interceptor.qos1.Mqtt5OutgoingQos1Interceptor;
import com.hivemq.client.mqtt.mqtt5.advanced.interceptor.qos2.Mqtt5IncomingQos2Interceptor;
import com.hivemq.client.mqtt.mqtt5.advanced.interceptor.qos2.Mqtt5OutgoingQos2Interceptor;
import java.util.function.Function;

/* JADX INFO: loaded from: classes10.dex */
public abstract class MqttClientInterceptorsBuilder<B extends MqttClientInterceptorsBuilder<B>> {
    private Mqtt5IncomingQos1Interceptor incomingQos1Interceptor;
    private Mqtt5IncomingQos2Interceptor incomingQos2Interceptor;
    private Mqtt5OutgoingQos1Interceptor outgoingQos1Interceptor;
    private Mqtt5OutgoingQos2Interceptor outgoingQos2Interceptor;

    abstract B self();

    MqttClientInterceptorsBuilder() {
    }

    MqttClientInterceptorsBuilder(MqttClientInterceptors mqttClientInterceptors) {
        if (mqttClientInterceptors != null) {
            this.incomingQos1Interceptor = mqttClientInterceptors.getIncomingQos1Interceptor();
            this.outgoingQos1Interceptor = mqttClientInterceptors.getOutgoingQos1Interceptor();
            this.incomingQos2Interceptor = mqttClientInterceptors.getIncomingQos2Interceptor();
            this.outgoingQos2Interceptor = mqttClientInterceptors.getOutgoingQos2Interceptor();
        }
    }

    public B incomingQos1Interceptor(Mqtt5IncomingQos1Interceptor mqtt5IncomingQos1Interceptor) {
        this.incomingQos1Interceptor = mqtt5IncomingQos1Interceptor;
        return (B) self();
    }

    public B outgoingQos1Interceptor(Mqtt5OutgoingQos1Interceptor mqtt5OutgoingQos1Interceptor) {
        this.outgoingQos1Interceptor = mqtt5OutgoingQos1Interceptor;
        return (B) self();
    }

    public B incomingQos2Interceptor(Mqtt5IncomingQos2Interceptor mqtt5IncomingQos2Interceptor) {
        this.incomingQos2Interceptor = mqtt5IncomingQos2Interceptor;
        return (B) self();
    }

    public B outgoingQos2Interceptor(Mqtt5OutgoingQos2Interceptor mqtt5OutgoingQos2Interceptor) {
        this.outgoingQos2Interceptor = mqtt5OutgoingQos2Interceptor;
        return (B) self();
    }

    public MqttClientInterceptors build() {
        return new MqttClientInterceptors(this.incomingQos1Interceptor, this.outgoingQos1Interceptor, this.incomingQos2Interceptor, this.outgoingQos2Interceptor);
    }

    public static class Default extends MqttClientInterceptorsBuilder<Default> implements Mqtt5ClientInterceptorsBuilder {
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.hivemq.client.internal.mqtt.advanced.interceptor.MqttClientInterceptorsBuilder
        public Default self() {
            return this;
        }

        @Override // com.hivemq.client.mqtt.mqtt5.advanced.interceptor.Mqtt5ClientInterceptorsBuilder
        public /* bridge */ /* synthetic */ Mqtt5ClientInterceptors build() {
            return super.build();
        }

        @Override // com.hivemq.client.mqtt.mqtt5.advanced.interceptor.Mqtt5ClientInterceptorsBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5ClientInterceptorsBuilderBase incomingQos1Interceptor(Mqtt5IncomingQos1Interceptor mqtt5IncomingQos1Interceptor) {
            return (Mqtt5ClientInterceptorsBuilderBase) super.incomingQos1Interceptor(mqtt5IncomingQos1Interceptor);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.advanced.interceptor.Mqtt5ClientInterceptorsBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5ClientInterceptorsBuilderBase incomingQos2Interceptor(Mqtt5IncomingQos2Interceptor mqtt5IncomingQos2Interceptor) {
            return (Mqtt5ClientInterceptorsBuilderBase) super.incomingQos2Interceptor(mqtt5IncomingQos2Interceptor);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.advanced.interceptor.Mqtt5ClientInterceptorsBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5ClientInterceptorsBuilderBase outgoingQos1Interceptor(Mqtt5OutgoingQos1Interceptor mqtt5OutgoingQos1Interceptor) {
            return (Mqtt5ClientInterceptorsBuilderBase) super.outgoingQos1Interceptor(mqtt5OutgoingQos1Interceptor);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.advanced.interceptor.Mqtt5ClientInterceptorsBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5ClientInterceptorsBuilderBase outgoingQos2Interceptor(Mqtt5OutgoingQos2Interceptor mqtt5OutgoingQos2Interceptor) {
            return (Mqtt5ClientInterceptorsBuilderBase) super.outgoingQos2Interceptor(mqtt5OutgoingQos2Interceptor);
        }

        public Default() {
        }

        Default(MqttClientInterceptors mqttClientInterceptors) {
            super(mqttClientInterceptors);
        }
    }

    public static class Nested<P> extends MqttClientInterceptorsBuilder<Nested<P>> implements Mqtt5ClientInterceptorsBuilder.Nested<P> {
        private final Function<? super MqttClientInterceptors, P> parentConsumer;

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.hivemq.client.internal.mqtt.advanced.interceptor.MqttClientInterceptorsBuilder
        public Nested<P> self() {
            return this;
        }

        @Override // com.hivemq.client.mqtt.mqtt5.advanced.interceptor.Mqtt5ClientInterceptorsBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5ClientInterceptorsBuilderBase incomingQos1Interceptor(Mqtt5IncomingQos1Interceptor mqtt5IncomingQos1Interceptor) {
            return (Mqtt5ClientInterceptorsBuilderBase) super.incomingQos1Interceptor(mqtt5IncomingQos1Interceptor);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.advanced.interceptor.Mqtt5ClientInterceptorsBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5ClientInterceptorsBuilderBase incomingQos2Interceptor(Mqtt5IncomingQos2Interceptor mqtt5IncomingQos2Interceptor) {
            return (Mqtt5ClientInterceptorsBuilderBase) super.incomingQos2Interceptor(mqtt5IncomingQos2Interceptor);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.advanced.interceptor.Mqtt5ClientInterceptorsBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5ClientInterceptorsBuilderBase outgoingQos1Interceptor(Mqtt5OutgoingQos1Interceptor mqtt5OutgoingQos1Interceptor) {
            return (Mqtt5ClientInterceptorsBuilderBase) super.outgoingQos1Interceptor(mqtt5OutgoingQos1Interceptor);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.advanced.interceptor.Mqtt5ClientInterceptorsBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5ClientInterceptorsBuilderBase outgoingQos2Interceptor(Mqtt5OutgoingQos2Interceptor mqtt5OutgoingQos2Interceptor) {
            return (Mqtt5ClientInterceptorsBuilderBase) super.outgoingQos2Interceptor(mqtt5OutgoingQos2Interceptor);
        }

        public Nested(MqttClientInterceptors mqttClientInterceptors, Function<? super MqttClientInterceptors, P> function) {
            super(mqttClientInterceptors);
            this.parentConsumer = function;
        }

        @Override // com.hivemq.client.mqtt.mqtt5.advanced.interceptor.Mqtt5ClientInterceptorsBuilder.Nested
        public P applyInterceptors() {
            return this.parentConsumer.apply(build());
        }
    }
}
