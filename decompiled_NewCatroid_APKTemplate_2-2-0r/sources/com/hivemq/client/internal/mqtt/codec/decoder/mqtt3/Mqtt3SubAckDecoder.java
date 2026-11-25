package com.hivemq.client.internal.mqtt.codec.decoder.mqtt3;

import com.hivemq.client.internal.mqtt.codec.decoder.MqttDecoderContext;
import com.hivemq.client.internal.mqtt.codec.decoder.MqttDecoderException;
import com.hivemq.client.internal.mqtt.codec.decoder.MqttMessageDecoder;
import com.hivemq.client.internal.mqtt.codec.decoder.MqttMessageDecoderUtil;
import com.hivemq.client.internal.mqtt.message.subscribe.suback.MqttSubAck;
import com.hivemq.client.internal.mqtt.message.subscribe.suback.mqtt3.Mqtt3SubAckView;
import com.hivemq.client.internal.util.collections.ImmutableList;
import com.hivemq.client.mqtt.mqtt3.message.subscribe.suback.Mqtt3SubAckReturnCode;
import io.netty.buffer.ByteBuf;
import javax.inject.Inject;
import javax.inject.Singleton;

/* JADX INFO: loaded from: classes10.dex */
@Singleton
public class Mqtt3SubAckDecoder implements MqttMessageDecoder {
    private static final int FLAGS = 0;
    private static final int MIN_REMAINING_LENGTH = 3;

    @Inject
    Mqtt3SubAckDecoder() {
    }

    @Override // com.hivemq.client.internal.mqtt.codec.decoder.MqttMessageDecoder
    public MqttSubAck decode(int i, ByteBuf byteBuf, MqttDecoderContext mqttDecoderContext) throws MqttDecoderException {
        MqttMessageDecoderUtil.checkFixedHeaderFlags(0, i);
        if (byteBuf.readableBytes() < 3) {
            throw MqttMessageDecoderUtil.remainingLengthTooShort();
        }
        int unsignedShort = byteBuf.readUnsignedShort();
        int i2 = byteBuf.readableBytes();
        ImmutableList.Builder builder = ImmutableList.builder(i2);
        for (int i3 = 0; i3 < i2; i3++) {
            Mqtt3SubAckReturnCode mqtt3SubAckReturnCodeFromCode = Mqtt3SubAckReturnCode.fromCode(byteBuf.readUnsignedByte());
            if (mqtt3SubAckReturnCodeFromCode == null) {
                throw Mqtt3MessageDecoderUtil.wrongReturnCode();
            }
            builder.add(mqtt3SubAckReturnCodeFromCode);
        }
        return Mqtt3SubAckView.delegate(unsignedShort, builder.build());
    }
}
