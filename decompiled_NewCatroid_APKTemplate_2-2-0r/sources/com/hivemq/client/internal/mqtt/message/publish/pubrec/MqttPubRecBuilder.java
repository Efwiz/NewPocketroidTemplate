package com.hivemq.client.internal.mqtt.message.publish.pubrec;

import com.hivemq.client.internal.mqtt.datatypes.MqttUserPropertiesImpl;
import com.hivemq.client.internal.mqtt.datatypes.MqttUserPropertiesImplBuilder;
import com.hivemq.client.internal.mqtt.datatypes.MqttUtf8StringImpl;
import com.hivemq.client.internal.mqtt.message.publish.MqttStatefulPublish;
import com.hivemq.client.internal.mqtt.util.MqttChecks;
import com.hivemq.client.internal.util.Checks;
import com.hivemq.client.mqtt.datatypes.MqttUtf8String;
import com.hivemq.client.mqtt.mqtt5.datatypes.Mqtt5UserProperties;
import com.hivemq.client.mqtt.mqtt5.message.publish.pubrec.Mqtt5PubRecBuilder;
import com.hivemq.client.mqtt.mqtt5.message.publish.pubrec.Mqtt5PubRecReasonCode;
import java.util.function.Function;

/* JADX INFO: loaded from: classes10.dex */
public class MqttPubRecBuilder implements Mqtt5PubRecBuilder {
    private final MqttStatefulPublish publish;
    private MqttUtf8StringImpl reasonString;
    private Mqtt5PubRecReasonCode reasonCode = MqttPubRec.DEFAULT_REASON_CODE;
    private MqttUserPropertiesImpl userProperties = MqttUserPropertiesImpl.NO_USER_PROPERTIES;

    public MqttPubRecBuilder(MqttStatefulPublish mqttStatefulPublish) {
        this.publish = mqttStatefulPublish;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.publish.pubrec.Mqtt5PubRecBuilder
    public MqttPubRecBuilder reasonCode(Mqtt5PubRecReasonCode mqtt5PubRecReasonCode) {
        this.reasonCode = (Mqtt5PubRecReasonCode) Checks.notNull(mqtt5PubRecReasonCode, "Reason code");
        return this;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.publish.pubrec.Mqtt5PubRecBuilder
    public MqttPubRecBuilder reasonString(String str) {
        this.reasonString = MqttChecks.reasonString(str);
        return this;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.publish.pubrec.Mqtt5PubRecBuilder
    public MqttPubRecBuilder reasonString(MqttUtf8String mqttUtf8String) {
        this.reasonString = MqttChecks.reasonString(mqttUtf8String);
        return this;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.publish.pubrec.Mqtt5PubRecBuilder
    public MqttPubRecBuilder userProperties(Mqtt5UserProperties mqtt5UserProperties) {
        this.userProperties = MqttChecks.userProperties(mqtt5UserProperties);
        return this;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.publish.pubrec.Mqtt5PubRecBuilder
    public MqttUserPropertiesImplBuilder.Nested<MqttPubRecBuilder> userProperties() {
        return new MqttUserPropertiesImplBuilder.Nested<>(this.userProperties, new Function() { // from class: com.hivemq.client.internal.mqtt.message.publish.pubrec.MqttPubRecBuilder$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.userProperties((Mqtt5UserProperties) obj);
            }
        });
    }

    public MqttStatefulPublish getPublish() {
        return this.publish;
    }

    public MqttPubRec build() {
        return new MqttPubRec(this.publish.getPacketIdentifier(), this.reasonCode, this.reasonString, this.userProperties);
    }
}
