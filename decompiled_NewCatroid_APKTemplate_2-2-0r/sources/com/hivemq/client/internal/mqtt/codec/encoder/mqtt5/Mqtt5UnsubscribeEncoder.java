package com.hivemq.client.internal.mqtt.codec.encoder.mqtt5;

import com.hivemq.client.internal.mqtt.datatypes.MqttTopicFilterImpl;
import com.hivemq.client.internal.mqtt.datatypes.MqttVariableByteInteger;
import com.hivemq.client.internal.mqtt.message.unsubscribe.MqttStatefulUnsubscribe;
import com.hivemq.client.internal.mqtt.message.unsubscribe.MqttUnsubscribe;
import com.hivemq.client.internal.util.collections.ImmutableList;
import com.hivemq.client.mqtt.mqtt5.message.Mqtt5MessageType;
import io.netty.buffer.ByteBuf;
import javax.inject.Inject;
import javax.inject.Singleton;

/* JADX INFO: loaded from: classes10.dex */
@Singleton
public class Mqtt5UnsubscribeEncoder extends Mqtt5MessageWithUserPropertiesEncoder<MqttStatefulUnsubscribe> {
    private static final int FIXED_HEADER = (Mqtt5MessageType.UNSUBSCRIBE.getCode() << 4) | 2;
    private static final int VARIABLE_HEADER_FIXED_LENGTH = 2;

    @Inject
    Mqtt5UnsubscribeEncoder() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.hivemq.client.internal.mqtt.codec.encoder.mqtt5.Mqtt5MessageWithUserPropertiesEncoder
    public int remainingLengthWithoutProperties(MqttStatefulUnsubscribe mqttStatefulUnsubscribe) {
        ImmutableList<MqttTopicFilterImpl> topicFilters = ((MqttUnsubscribe) mqttStatefulUnsubscribe.stateless()).getTopicFilters();
        int iEncodedLength = 2;
        for (int i = 0; i < topicFilters.size(); i++) {
            iEncodedLength += topicFilters.get(i).encodedLength();
        }
        return iEncodedLength;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.hivemq.client.internal.mqtt.codec.encoder.mqtt5.Mqtt5MessageWithUserPropertiesEncoder
    public int propertyLength(MqttStatefulUnsubscribe mqttStatefulUnsubscribe) {
        return omissiblePropertyLength(mqttStatefulUnsubscribe);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.hivemq.client.internal.mqtt.codec.encoder.mqtt5.Mqtt5MessageWithUserPropertiesEncoder
    public void encode(MqttStatefulUnsubscribe mqttStatefulUnsubscribe, ByteBuf byteBuf, int i, int i2, int i3) {
        encodeFixedHeader(byteBuf, i);
        encodeVariableHeader(mqttStatefulUnsubscribe, byteBuf, i2, i3);
        encodePayload(mqttStatefulUnsubscribe, byteBuf);
    }

    private void encodeFixedHeader(ByteBuf byteBuf, int i) {
        byteBuf.writeByte(FIXED_HEADER);
        MqttVariableByteInteger.encode(i, byteBuf);
    }

    private void encodeVariableHeader(MqttStatefulUnsubscribe mqttStatefulUnsubscribe, ByteBuf byteBuf, int i, int i2) {
        byteBuf.writeShort(mqttStatefulUnsubscribe.getPacketIdentifier());
        encodeProperties(mqttStatefulUnsubscribe, byteBuf, i, i2);
    }

    private void encodeProperties(MqttStatefulUnsubscribe mqttStatefulUnsubscribe, ByteBuf byteBuf, int i, int i2) {
        MqttVariableByteInteger.encode(i, byteBuf);
        encodeOmissibleProperties(mqttStatefulUnsubscribe, byteBuf, i2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void encodePayload(MqttStatefulUnsubscribe mqttStatefulUnsubscribe, ByteBuf byteBuf) {
        ImmutableList<MqttTopicFilterImpl> topicFilters = ((MqttUnsubscribe) mqttStatefulUnsubscribe.stateless()).getTopicFilters();
        for (int i = 0; i < topicFilters.size(); i++) {
            topicFilters.get(i).encode(byteBuf);
        }
    }
}
