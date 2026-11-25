package com.hivemq.client.internal.mqtt.message.publish.puback;

import com.hivemq.client.internal.mqtt.datatypes.MqttUserPropertiesImpl;
import com.hivemq.client.internal.mqtt.datatypes.MqttUserPropertiesImplBuilder;
import com.hivemq.client.internal.mqtt.datatypes.MqttUtf8StringImpl;
import com.hivemq.client.internal.mqtt.message.publish.MqttStatefulPublish;
import com.hivemq.client.internal.mqtt.util.MqttChecks;
import com.hivemq.client.internal.util.Checks;
import com.hivemq.client.mqtt.datatypes.MqttUtf8String;
import com.hivemq.client.mqtt.mqtt5.datatypes.Mqtt5UserProperties;
import com.hivemq.client.mqtt.mqtt5.message.publish.puback.Mqtt5PubAckBuilder;
import com.hivemq.client.mqtt.mqtt5.message.publish.puback.Mqtt5PubAckReasonCode;
import java.util.function.Function;

/* JADX INFO: loaded from: classes10.dex */
public class MqttPubAckBuilder implements Mqtt5PubAckBuilder {
    private final MqttStatefulPublish publish;
    private MqttUtf8StringImpl reasonString;
    private Mqtt5PubAckReasonCode reasonCode = MqttPubAck.DEFAULT_REASON_CODE;
    private MqttUserPropertiesImpl userProperties = MqttUserPropertiesImpl.NO_USER_PROPERTIES;

    public MqttPubAckBuilder(MqttStatefulPublish mqttStatefulPublish) {
        this.publish = mqttStatefulPublish;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.publish.puback.Mqtt5PubAckBuilder
    public MqttPubAckBuilder reasonCode(Mqtt5PubAckReasonCode mqtt5PubAckReasonCode) {
        this.reasonCode = (Mqtt5PubAckReasonCode) Checks.notNull(mqtt5PubAckReasonCode, "Reason code");
        return this;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.publish.puback.Mqtt5PubAckBuilder
    public MqttPubAckBuilder reasonString(String str) {
        this.reasonString = MqttChecks.reasonString(str);
        return this;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.publish.puback.Mqtt5PubAckBuilder
    public MqttPubAckBuilder reasonString(MqttUtf8String mqttUtf8String) {
        this.reasonString = MqttChecks.reasonString(mqttUtf8String);
        return this;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.publish.puback.Mqtt5PubAckBuilder
    public MqttPubAckBuilder userProperties(Mqtt5UserProperties mqtt5UserProperties) {
        this.userProperties = MqttChecks.userProperties(mqtt5UserProperties);
        return this;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.publish.puback.Mqtt5PubAckBuilder
    public MqttUserPropertiesImplBuilder.Nested<MqttPubAckBuilder> userProperties() {
        return new MqttUserPropertiesImplBuilder.Nested<>(this.userProperties, new Function() { // from class: com.hivemq.client.internal.mqtt.message.publish.puback.MqttPubAckBuilder$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.userProperties((Mqtt5UserProperties) obj);
            }
        });
    }

    public MqttStatefulPublish getPublish() {
        return this.publish;
    }

    public MqttPubAck build() {
        return new MqttPubAck(this.publish.getPacketIdentifier(), this.reasonCode, this.reasonString, this.userProperties);
    }
}
