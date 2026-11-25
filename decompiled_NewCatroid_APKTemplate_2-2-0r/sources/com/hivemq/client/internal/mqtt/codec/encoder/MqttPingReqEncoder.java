package com.hivemq.client.internal.mqtt.codec.encoder;

import com.hivemq.client.internal.mqtt.message.ping.MqttPingReq;
import com.hivemq.client.mqtt.mqtt5.message.Mqtt5MessageType;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.UnpooledByteBufAllocator;
import javax.inject.Inject;
import javax.inject.Singleton;

/* JADX INFO: loaded from: classes10.dex */
@Singleton
public class MqttPingReqEncoder extends MqttMessageEncoder<MqttPingReq> {
    private static final int ENCODED_LENGTH = 2;
    private static final ByteBuf PACKET = UnpooledByteBufAllocator.DEFAULT.ioBuffer(2).writeByte(Mqtt5MessageType.PINGREQ.getCode() << 4).writeByte(0);

    @Inject
    MqttPingReqEncoder() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.hivemq.client.internal.mqtt.codec.encoder.MqttMessageEncoder
    public ByteBuf encode(MqttPingReq mqttPingReq, MqttEncoderContext mqttEncoderContext) {
        return PACKET.retainedDuplicate();
    }
}
