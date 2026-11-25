package com.hivemq.client.internal.mqtt.message.publish.pubrel;

import com.hivemq.client.internal.mqtt.datatypes.MqttUserPropertiesImpl;
import com.hivemq.client.internal.mqtt.datatypes.MqttUserPropertiesImplBuilder;
import com.hivemq.client.internal.mqtt.datatypes.MqttUtf8StringImpl;
import com.hivemq.client.internal.mqtt.message.publish.pubrec.MqttPubRec;
import com.hivemq.client.internal.mqtt.util.MqttChecks;
import com.hivemq.client.mqtt.datatypes.MqttUtf8String;
import com.hivemq.client.mqtt.mqtt5.datatypes.Mqtt5UserProperties;
import com.hivemq.client.mqtt.mqtt5.message.publish.pubrel.Mqtt5PubRelBuilder;
import com.hivemq.client.mqtt.mqtt5.message.publish.pubrel.Mqtt5PubRelReasonCode;
import java.util.function.Function;

/* JADX INFO: loaded from: classes10.dex */
public class MqttPubRelBuilder implements Mqtt5PubRelBuilder {
    private final int packetIdentifier;
    private MqttUtf8StringImpl reasonString;
    private Mqtt5PubRelReasonCode reasonCode = MqttPubRel.DEFAULT_REASON_CODE;
    private MqttUserPropertiesImpl userProperties = MqttUserPropertiesImpl.NO_USER_PROPERTIES;

    public MqttPubRelBuilder(MqttPubRec mqttPubRec) {
        this.packetIdentifier = mqttPubRec.getPacketIdentifier();
    }

    public MqttPubRelBuilder reasonCode(Mqtt5PubRelReasonCode mqtt5PubRelReasonCode) {
        this.reasonCode = mqtt5PubRelReasonCode;
        return this;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.publish.pubrel.Mqtt5PubRelBuilder
    public MqttPubRelBuilder reasonString(String str) {
        this.reasonString = MqttChecks.reasonString(str);
        return this;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.publish.pubrel.Mqtt5PubRelBuilder
    public MqttPubRelBuilder reasonString(MqttUtf8String mqttUtf8String) {
        this.reasonString = MqttChecks.reasonString(mqttUtf8String);
        return this;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.publish.pubrel.Mqtt5PubRelBuilder
    public MqttPubRelBuilder userProperties(Mqtt5UserProperties mqtt5UserProperties) {
        this.userProperties = MqttChecks.userProperties(mqtt5UserProperties);
        return this;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.publish.pubrel.Mqtt5PubRelBuilder
    public MqttUserPropertiesImplBuilder.Nested<MqttPubRelBuilder> userProperties() {
        return new MqttUserPropertiesImplBuilder.Nested<>(this.userProperties, new Function() { // from class: com.hivemq.client.internal.mqtt.message.publish.pubrel.MqttPubRelBuilder$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.userProperties((Mqtt5UserProperties) obj);
            }
        });
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.publish.pubrel.Mqtt5PubRelBuilder
    public Mqtt5PubRelReasonCode getReasonCode() {
        return this.reasonCode;
    }

    public MqttPubRel build() {
        return new MqttPubRel(this.packetIdentifier, this.reasonCode, this.reasonString, this.userProperties);
    }
}
