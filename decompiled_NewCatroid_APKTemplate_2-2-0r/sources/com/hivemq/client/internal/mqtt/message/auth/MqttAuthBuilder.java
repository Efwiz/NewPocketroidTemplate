package com.hivemq.client.internal.mqtt.message.auth;

import com.hivemq.client.internal.mqtt.datatypes.MqttUserPropertiesImpl;
import com.hivemq.client.internal.mqtt.datatypes.MqttUserPropertiesImplBuilder;
import com.hivemq.client.internal.mqtt.datatypes.MqttUtf8StringImpl;
import com.hivemq.client.internal.mqtt.util.MqttChecks;
import com.hivemq.client.internal.util.Checks;
import com.hivemq.client.mqtt.datatypes.MqttUtf8String;
import com.hivemq.client.mqtt.mqtt5.datatypes.Mqtt5UserProperties;
import com.hivemq.client.mqtt.mqtt5.message.auth.Mqtt5AuthBuilder;
import com.hivemq.client.mqtt.mqtt5.message.auth.Mqtt5AuthReasonCode;
import java.nio.ByteBuffer;
import java.util.function.Function;

/* JADX INFO: loaded from: classes10.dex */
public class MqttAuthBuilder implements Mqtt5AuthBuilder {
    private ByteBuffer data;
    private final MqttUtf8StringImpl method;
    private final Mqtt5AuthReasonCode reasonCode;
    private MqttUtf8StringImpl reasonString;
    private MqttUserPropertiesImpl userProperties = MqttUserPropertiesImpl.NO_USER_PROPERTIES;

    public MqttAuthBuilder(Mqtt5AuthReasonCode mqtt5AuthReasonCode, MqttUtf8StringImpl mqttUtf8StringImpl) {
        Checks.notNull(mqtt5AuthReasonCode, "Reason code");
        Checks.notNull(mqttUtf8StringImpl, "Method");
        this.reasonCode = mqtt5AuthReasonCode;
        this.method = mqttUtf8StringImpl;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.auth.Mqtt5AuthBuilder, com.hivemq.client.mqtt.mqtt5.message.auth.Mqtt5EnhancedAuthBuilder
    public MqttAuthBuilder data(byte[] bArr) {
        this.data = MqttChecks.binaryDataOrNull(bArr, "Auth data");
        return this;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.auth.Mqtt5AuthBuilder, com.hivemq.client.mqtt.mqtt5.message.auth.Mqtt5EnhancedAuthBuilder
    public MqttAuthBuilder data(ByteBuffer byteBuffer) {
        this.data = MqttChecks.binaryDataOrNull(byteBuffer, "Auth data");
        return this;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.auth.Mqtt5AuthBuilder
    public MqttAuthBuilder reasonString(String str) {
        this.reasonString = MqttChecks.reasonString(str);
        return this;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.auth.Mqtt5AuthBuilder
    public MqttAuthBuilder reasonString(MqttUtf8String mqttUtf8String) {
        this.reasonString = MqttChecks.reasonString(mqttUtf8String);
        return this;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.auth.Mqtt5AuthBuilder
    public MqttAuthBuilder userProperties(Mqtt5UserProperties mqtt5UserProperties) {
        this.userProperties = MqttChecks.userProperties(mqtt5UserProperties);
        return this;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.auth.Mqtt5AuthBuilder
    public MqttUserPropertiesImplBuilder.Nested<MqttAuthBuilder> userProperties() {
        return new MqttUserPropertiesImplBuilder.Nested<>(this.userProperties, new Function() { // from class: com.hivemq.client.internal.mqtt.message.auth.MqttAuthBuilder$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.userProperties((Mqtt5UserProperties) obj);
            }
        });
    }

    public MqttAuth build() {
        return new MqttAuth(this.reasonCode, this.method, this.data, this.reasonString, this.userProperties);
    }
}
