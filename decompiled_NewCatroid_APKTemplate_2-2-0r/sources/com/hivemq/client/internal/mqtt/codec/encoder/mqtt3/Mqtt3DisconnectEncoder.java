package com.hivemq.client.internal.mqtt.codec.encoder.mqtt3;

import com.hivemq.client.internal.mqtt.codec.encoder.MqttEncoderContext;
import com.hivemq.client.internal.mqtt.codec.encoder.MqttMessageEncoder;
import com.hivemq.client.internal.mqtt.message.disconnect.MqttDisconnect;
import com.hivemq.client.mqtt.mqtt3.message.Mqtt3MessageType;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.UnpooledByteBufAllocator;
import javax.inject.Inject;
import javax.inject.Singleton;

/* JADX INFO: loaded from: classes10.dex */
@Singleton
public class Mqtt3DisconnectEncoder extends MqttMessageEncoder<MqttDisconnect> {
    private static final int ENCODED_LENGTH = 2;
    private static final ByteBuf PACKET = UnpooledByteBufAllocator.DEFAULT.ioBuffer(2).writeByte(Mqtt3MessageType.DISCONNECT.getCode() << 4).writeByte(0);

    @Inject
    Mqtt3DisconnectEncoder() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.hivemq.client.internal.mqtt.codec.encoder.MqttMessageEncoder
    public ByteBuf encode(MqttDisconnect mqttDisconnect, MqttEncoderContext mqttEncoderContext) {
        return PACKET.retainedDuplicate();
    }
}
