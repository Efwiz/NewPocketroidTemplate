package com.hivemq.client.internal.mqtt.codec.decoder.mqtt5;

import com.hivemq.client.internal.mqtt.codec.decoder.MqttDecoderContext;
import com.hivemq.client.internal.mqtt.codec.decoder.MqttDecoderException;
import com.hivemq.client.internal.mqtt.codec.decoder.MqttMessageDecoder;
import com.hivemq.client.internal.mqtt.codec.decoder.MqttMessageDecoderUtil;
import com.hivemq.client.internal.mqtt.datatypes.MqttUserPropertiesImpl;
import com.hivemq.client.internal.mqtt.datatypes.MqttUserPropertyImpl;
import com.hivemq.client.internal.mqtt.datatypes.MqttUtf8StringImpl;
import com.hivemq.client.internal.mqtt.message.unsubscribe.unsuback.MqttUnsubAck;
import com.hivemq.client.internal.util.collections.ImmutableList;
import com.hivemq.client.mqtt.mqtt5.message.unsubscribe.unsuback.Mqtt5UnsubAckReasonCode;
import io.netty.buffer.ByteBuf;
import javax.inject.Inject;
import javax.inject.Singleton;

/* JADX INFO: loaded from: classes10.dex */
@Singleton
public class Mqtt5UnsubAckDecoder implements MqttMessageDecoder {
    private static final int FLAGS = 0;
    private static final int MIN_REMAINING_LENGTH = 3;

    @Inject
    Mqtt5UnsubAckDecoder() {
    }

    @Override // com.hivemq.client.internal.mqtt.codec.decoder.MqttMessageDecoder
    public MqttUnsubAck decode(int i, ByteBuf byteBuf, MqttDecoderContext mqttDecoderContext) throws MqttDecoderException {
        MqttMessageDecoderUtil.checkFixedHeaderFlags(0, i);
        if (byteBuf.readableBytes() < 3) {
            throw MqttMessageDecoderUtil.remainingLengthTooShort();
        }
        int unsignedShort = byteBuf.readUnsignedShort();
        int iDecodePropertyLength = Mqtt5MessageDecoderUtil.decodePropertyLength(byteBuf);
        int i2 = byteBuf.readerIndex();
        MqttUtf8StringImpl mqttUtf8StringImplDecodeReasonStringIfRequested = null;
        ImmutableList.Builder<MqttUserPropertyImpl> builderDecodeUserPropertyIfRequested = null;
        while (true) {
            int i3 = byteBuf.readerIndex() - i2;
            if (i3 >= iDecodePropertyLength) {
                if (i3 != iDecodePropertyLength) {
                    throw Mqtt5MessageDecoderUtil.malformedPropertyLength();
                }
                int i4 = byteBuf.readableBytes();
                if (i4 == 0) {
                    throw Mqtt5MessageDecoderUtil.noReasonCodes();
                }
                ImmutableList.Builder builder = ImmutableList.builder(i4);
                for (int i5 = 0; i5 < i4; i5++) {
                    Mqtt5UnsubAckReasonCode mqtt5UnsubAckReasonCodeFromCode = Mqtt5UnsubAckReasonCode.fromCode(byteBuf.readUnsignedByte());
                    if (mqtt5UnsubAckReasonCodeFromCode == null) {
                        throw Mqtt5MessageDecoderUtil.wrongReasonCode();
                    }
                    builder.add(mqtt5UnsubAckReasonCodeFromCode);
                }
                return new MqttUnsubAck(unsignedShort, builder.build(), mqttUtf8StringImplDecodeReasonStringIfRequested, MqttUserPropertiesImpl.build(builderDecodeUserPropertyIfRequested));
            }
            int iDecodePropertyIdentifier = Mqtt5MessageDecoderUtil.decodePropertyIdentifier(byteBuf);
            if (iDecodePropertyIdentifier == 31) {
                mqttUtf8StringImplDecodeReasonStringIfRequested = Mqtt5MessageDecoderUtil.decodeReasonStringIfRequested(mqttUtf8StringImplDecodeReasonStringIfRequested, byteBuf, mqttDecoderContext);
            } else if (iDecodePropertyIdentifier == 38) {
                builderDecodeUserPropertyIfRequested = Mqtt5MessageDecoderUtil.decodeUserPropertyIfRequested(builderDecodeUserPropertyIfRequested, byteBuf, mqttDecoderContext);
            } else {
                throw Mqtt5MessageDecoderUtil.wrongProperty(iDecodePropertyIdentifier);
            }
        }
    }
}
