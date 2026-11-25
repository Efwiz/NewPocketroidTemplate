package com.hivemq.client.internal.mqtt.message.publish.pubcomp;

import com.hivemq.client.internal.mqtt.datatypes.MqttUserPropertiesImpl;
import com.hivemq.client.internal.mqtt.datatypes.MqttUserPropertiesImplBuilder;
import com.hivemq.client.internal.mqtt.datatypes.MqttUtf8StringImpl;
import com.hivemq.client.internal.mqtt.message.publish.pubrel.MqttPubRel;
import com.hivemq.client.internal.mqtt.util.MqttChecks;
import com.hivemq.client.mqtt.datatypes.MqttUtf8String;
import com.hivemq.client.mqtt.mqtt5.datatypes.Mqtt5UserProperties;
import com.hivemq.client.mqtt.mqtt5.message.publish.pubcomp.Mqtt5PubCompBuilder;
import com.hivemq.client.mqtt.mqtt5.message.publish.pubcomp.Mqtt5PubCompReasonCode;
import java.util.function.Function;

/* JADX INFO: loaded from: classes10.dex */
public class MqttPubCompBuilder implements Mqtt5PubCompBuilder {
    private final MqttPubRel pubRel;
    private MqttUtf8StringImpl reasonString;
    private Mqtt5PubCompReasonCode reasonCode = MqttPubComp.DEFAULT_REASON_CODE;
    private MqttUserPropertiesImpl userProperties = MqttUserPropertiesImpl.NO_USER_PROPERTIES;

    public MqttPubCompBuilder(MqttPubRel mqttPubRel) {
        this.pubRel = mqttPubRel;
    }

    public MqttPubCompBuilder reasonCode(Mqtt5PubCompReasonCode mqtt5PubCompReasonCode) {
        this.reasonCode = mqtt5PubCompReasonCode;
        return this;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.publish.pubcomp.Mqtt5PubCompBuilder
    public MqttPubCompBuilder reasonString(String str) {
        this.reasonString = MqttChecks.reasonString(str);
        return this;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.publish.pubcomp.Mqtt5PubCompBuilder
    public MqttPubCompBuilder reasonString(MqttUtf8String mqttUtf8String) {
        this.reasonString = MqttChecks.reasonString(mqttUtf8String);
        return this;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.publish.pubcomp.Mqtt5PubCompBuilder
    public MqttPubCompBuilder userProperties(Mqtt5UserProperties mqtt5UserProperties) {
        this.userProperties = MqttChecks.userProperties(mqtt5UserProperties);
        return this;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.publish.pubcomp.Mqtt5PubCompBuilder
    public MqttUserPropertiesImplBuilder.Nested<MqttPubCompBuilder> userProperties() {
        return new MqttUserPropertiesImplBuilder.Nested<>(this.userProperties, new Function() { // from class: com.hivemq.client.internal.mqtt.message.publish.pubcomp.MqttPubCompBuilder$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.userProperties((Mqtt5UserProperties) obj);
            }
        });
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.publish.pubcomp.Mqtt5PubCompBuilder
    public Mqtt5PubCompReasonCode getReasonCode() {
        return this.reasonCode;
    }

    public MqttPubRel getPubRel() {
        return this.pubRel;
    }

    public MqttPubComp build() {
        return new MqttPubComp(this.pubRel.getPacketIdentifier(), this.reasonCode, this.reasonString, this.userProperties);
    }
}
