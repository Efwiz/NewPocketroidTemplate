package com.hivemq.client.internal.mqtt.codec.encoder.mqtt3;

import com.hivemq.client.internal.mqtt.datatypes.MqttTopicFilterImpl;
import com.hivemq.client.internal.mqtt.datatypes.MqttVariableByteInteger;
import com.hivemq.client.internal.mqtt.message.unsubscribe.MqttStatefulUnsubscribe;
import com.hivemq.client.internal.mqtt.message.unsubscribe.MqttUnsubscribe;
import com.hivemq.client.internal.util.collections.ImmutableList;
import com.hivemq.client.mqtt.mqtt3.message.Mqtt3MessageType;
import io.netty.buffer.ByteBuf;
import javax.inject.Inject;
import javax.inject.Singleton;

/* JADX INFO: loaded from: classes10.dex */
@Singleton
public class Mqtt3UnsubscribeEncoder extends Mqtt3MessageEncoder<MqttStatefulUnsubscribe> {
    private static final int FIXED_HEADER = (Mqtt3MessageType.UNSUBSCRIBE.getCode() << 4) | 2;
    private static final int VARIABLE_HEADER_FIXED_LENGTH = 2;

    @Inject
    Mqtt3UnsubscribeEncoder() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.hivemq.client.internal.mqtt.codec.encoder.mqtt3.Mqtt3MessageEncoder
    public int remainingLength(MqttStatefulUnsubscribe mqttStatefulUnsubscribe) {
        ImmutableList<MqttTopicFilterImpl> topicFilters = ((MqttUnsubscribe) mqttStatefulUnsubscribe.stateless()).getTopicFilters();
        int iEncodedLength = 2;
        for (int i = 0; i < topicFilters.size(); i++) {
            iEncodedLength += topicFilters.get(i).encodedLength();
        }
        return iEncodedLength;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.hivemq.client.internal.mqtt.codec.encoder.mqtt3.Mqtt3MessageEncoder
    public void encode(MqttStatefulUnsubscribe mqttStatefulUnsubscribe, ByteBuf byteBuf, int i) {
        encodeFixedHeader(byteBuf, i);
        encodeVariableHeader(mqttStatefulUnsubscribe, byteBuf);
        encodePayload(mqttStatefulUnsubscribe, byteBuf);
    }

    private void encodeFixedHeader(ByteBuf byteBuf, int i) {
        byteBuf.writeByte(FIXED_HEADER);
        MqttVariableByteInteger.encode(i, byteBuf);
    }

    private void encodeVariableHeader(MqttStatefulUnsubscribe mqttStatefulUnsubscribe, ByteBuf byteBuf) {
        byteBuf.writeShort(mqttStatefulUnsubscribe.getPacketIdentifier());
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void encodePayload(MqttStatefulUnsubscribe mqttStatefulUnsubscribe, ByteBuf byteBuf) {
        ImmutableList<MqttTopicFilterImpl> topicFilters = ((MqttUnsubscribe) mqttStatefulUnsubscribe.stateless()).getTopicFilters();
        for (int i = 0; i < topicFilters.size(); i++) {
            topicFilters.get(i).encode(byteBuf);
        }
    }
}
