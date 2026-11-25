package com.hivemq.client.internal.mqtt.codec.encoder.mqtt5;

import com.hivemq.client.internal.mqtt.datatypes.MqttVariableByteInteger;
import com.hivemq.client.internal.mqtt.message.subscribe.MqttStatefulSubscribe;
import com.hivemq.client.internal.mqtt.message.subscribe.MqttSubscribe;
import com.hivemq.client.internal.mqtt.message.subscribe.MqttSubscription;
import com.hivemq.client.internal.util.collections.ImmutableList;
import com.hivemq.client.mqtt.mqtt5.message.Mqtt5MessageType;
import io.netty.buffer.ByteBuf;
import javax.inject.Inject;
import javax.inject.Singleton;

/* JADX INFO: loaded from: classes10.dex */
@Singleton
public class Mqtt5SubscribeEncoder extends Mqtt5MessageWithUserPropertiesEncoder<MqttStatefulSubscribe> {
    private static final int FIXED_HEADER = (Mqtt5MessageType.SUBSCRIBE.getCode() << 4) | 2;
    private static final int VARIABLE_HEADER_FIXED_LENGTH = 2;

    @Inject
    Mqtt5SubscribeEncoder() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.hivemq.client.internal.mqtt.codec.encoder.mqtt5.Mqtt5MessageWithUserPropertiesEncoder
    public int remainingLengthWithoutProperties(MqttStatefulSubscribe mqttStatefulSubscribe) {
        ImmutableList<MqttSubscription> subscriptions = ((MqttSubscribe) mqttStatefulSubscribe.stateless()).getSubscriptions();
        int iEncodedLength = 2;
        for (int i = 0; i < subscriptions.size(); i++) {
            iEncodedLength += subscriptions.get(i).getTopicFilter().encodedLength() + 1;
        }
        return iEncodedLength;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.hivemq.client.internal.mqtt.codec.encoder.mqtt5.Mqtt5MessageWithUserPropertiesEncoder
    public int propertyLength(MqttStatefulSubscribe mqttStatefulSubscribe) {
        return omissiblePropertyLength(mqttStatefulSubscribe) + Mqtt5MessageEncoderUtil.variableByteIntegerPropertyEncodedLength(mqttStatefulSubscribe.getSubscriptionIdentifier(), -1);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.hivemq.client.internal.mqtt.codec.encoder.mqtt5.Mqtt5MessageWithUserPropertiesEncoder
    public void encode(MqttStatefulSubscribe mqttStatefulSubscribe, ByteBuf byteBuf, int i, int i2, int i3) {
        encodeFixedHeader(byteBuf, i);
        encodeVariableHeader(mqttStatefulSubscribe, byteBuf, i2, i3);
        encodePayload(mqttStatefulSubscribe, byteBuf);
    }

    private void encodeFixedHeader(ByteBuf byteBuf, int i) {
        byteBuf.writeByte(FIXED_HEADER);
        MqttVariableByteInteger.encode(i, byteBuf);
    }

    private void encodeVariableHeader(MqttStatefulSubscribe mqttStatefulSubscribe, ByteBuf byteBuf, int i, int i2) {
        byteBuf.writeShort(mqttStatefulSubscribe.getPacketIdentifier());
        encodeProperties(mqttStatefulSubscribe, byteBuf, i, i2);
    }

    private void encodeProperties(MqttStatefulSubscribe mqttStatefulSubscribe, ByteBuf byteBuf, int i, int i2) {
        MqttVariableByteInteger.encode(i, byteBuf);
        Mqtt5MessageEncoderUtil.encodeVariableByteIntegerProperty(11, mqttStatefulSubscribe.getSubscriptionIdentifier(), -1L, byteBuf);
        encodeOmissibleProperties(mqttStatefulSubscribe, byteBuf, i2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void encodePayload(MqttStatefulSubscribe mqttStatefulSubscribe, ByteBuf byteBuf) {
        ImmutableList<MqttSubscription> subscriptions = ((MqttSubscribe) mqttStatefulSubscribe.stateless()).getSubscriptions();
        for (int i = 0; i < subscriptions.size(); i++) {
            MqttSubscription mqttSubscription = subscriptions.get(i);
            mqttSubscription.getTopicFilter().encode(byteBuf);
            byteBuf.writeByte(mqttSubscription.encodeSubscriptionOptions());
        }
    }
}
