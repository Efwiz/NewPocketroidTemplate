package com.hivemq.client.internal.mqtt.codec.encoder.mqtt3;

import com.hivemq.client.internal.mqtt.datatypes.MqttVariableByteInteger;
import com.hivemq.client.internal.mqtt.message.subscribe.MqttStatefulSubscribe;
import com.hivemq.client.internal.mqtt.message.subscribe.MqttSubscribe;
import com.hivemq.client.internal.mqtt.message.subscribe.MqttSubscription;
import com.hivemq.client.internal.util.collections.ImmutableList;
import com.hivemq.client.mqtt.mqtt3.message.Mqtt3MessageType;
import io.netty.buffer.ByteBuf;
import javax.inject.Inject;
import javax.inject.Singleton;

/* JADX INFO: loaded from: classes10.dex */
@Singleton
public class Mqtt3SubscribeEncoder extends Mqtt3MessageEncoder<MqttStatefulSubscribe> {
    private static final int FIXED_HEADER = (Mqtt3MessageType.SUBSCRIBE.getCode() << 4) | 2;
    private static final int VARIABLE_HEADER_FIXED_LENGTH = 2;

    @Inject
    Mqtt3SubscribeEncoder() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.hivemq.client.internal.mqtt.codec.encoder.mqtt3.Mqtt3MessageEncoder
    public int remainingLength(MqttStatefulSubscribe mqttStatefulSubscribe) {
        ImmutableList<MqttSubscription> subscriptions = ((MqttSubscribe) mqttStatefulSubscribe.stateless()).getSubscriptions();
        int iEncodedLength = 2;
        for (int i = 0; i < subscriptions.size(); i++) {
            iEncodedLength += subscriptions.get(i).getTopicFilter().encodedLength() + 1;
        }
        return iEncodedLength;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.hivemq.client.internal.mqtt.codec.encoder.mqtt3.Mqtt3MessageEncoder
    public void encode(MqttStatefulSubscribe mqttStatefulSubscribe, ByteBuf byteBuf, int i) {
        encodeFixedHeader(byteBuf, i);
        encodeVariableHeader(mqttStatefulSubscribe, byteBuf);
        encodePayload(mqttStatefulSubscribe, byteBuf);
    }

    private void encodeFixedHeader(ByteBuf byteBuf, int i) {
        byteBuf.writeByte(FIXED_HEADER);
        MqttVariableByteInteger.encode(i, byteBuf);
    }

    private void encodeVariableHeader(MqttStatefulSubscribe mqttStatefulSubscribe, ByteBuf byteBuf) {
        byteBuf.writeShort(mqttStatefulSubscribe.getPacketIdentifier());
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void encodePayload(MqttStatefulSubscribe mqttStatefulSubscribe, ByteBuf byteBuf) {
        ImmutableList<MqttSubscription> subscriptions = ((MqttSubscribe) mqttStatefulSubscribe.stateless()).getSubscriptions();
        for (int i = 0; i < subscriptions.size(); i++) {
            MqttSubscription mqttSubscription = subscriptions.get(i);
            mqttSubscription.getTopicFilter().encode(byteBuf);
            byteBuf.writeByte(mqttSubscription.getQos().getCode());
        }
    }
}
