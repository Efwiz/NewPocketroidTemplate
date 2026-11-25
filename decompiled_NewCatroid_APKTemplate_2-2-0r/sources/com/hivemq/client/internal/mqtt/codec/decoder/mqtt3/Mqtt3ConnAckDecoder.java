package com.hivemq.client.internal.mqtt.codec.decoder.mqtt3;

import com.hivemq.client.internal.mqtt.codec.decoder.MqttDecoderContext;
import com.hivemq.client.internal.mqtt.codec.decoder.MqttDecoderException;
import com.hivemq.client.internal.mqtt.codec.decoder.MqttMessageDecoder;
import com.hivemq.client.internal.mqtt.codec.decoder.MqttMessageDecoderUtil;
import com.hivemq.client.internal.mqtt.message.connect.connack.MqttConnAck;
import com.hivemq.client.internal.mqtt.message.connect.connack.mqtt3.Mqtt3ConnAckView;
import com.hivemq.client.mqtt.mqtt3.message.connect.connack.Mqtt3ConnAckReturnCode;
import io.netty.buffer.ByteBuf;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.billthefarmer.mididriver.MidiConstants;

/* JADX INFO: loaded from: classes10.dex */
@Singleton
public class Mqtt3ConnAckDecoder implements MqttMessageDecoder {
    private static final int FLAGS = 0;
    private static final int REMAINING_LENGTH = 2;

    @Inject
    Mqtt3ConnAckDecoder() {
    }

    @Override // com.hivemq.client.internal.mqtt.codec.decoder.MqttMessageDecoder
    public MqttConnAck decode(int i, ByteBuf byteBuf, MqttDecoderContext mqttDecoderContext) throws MqttDecoderException {
        MqttMessageDecoderUtil.checkFixedHeaderFlags(0, i);
        MqttMessageDecoderUtil.checkRemainingLength(2, byteBuf.readableBytes());
        byte b = byteBuf.readByte();
        if ((b & MidiConstants.ACTIVE_SENSING) != 0) {
            throw new MqttDecoderException("wrong CONNACK flags, bits 7-1 must be 0");
        }
        boolean z = (b & 1) == 1;
        Mqtt3ConnAckReturnCode mqtt3ConnAckReturnCodeFromCode = Mqtt3ConnAckReturnCode.fromCode(byteBuf.readUnsignedByte());
        if (mqtt3ConnAckReturnCodeFromCode == null) {
            throw Mqtt3MessageDecoderUtil.wrongReturnCode();
        }
        if (mqtt3ConnAckReturnCodeFromCode != Mqtt3ConnAckReturnCode.SUCCESS && z) {
            throw new MqttDecoderException("session present must be 0 if return code is not SUCCESS");
        }
        return Mqtt3ConnAckView.delegate(mqtt3ConnAckReturnCodeFromCode, z);
    }
}
