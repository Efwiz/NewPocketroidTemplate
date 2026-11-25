package com.hivemq.client.internal.mqtt.codec.decoder.mqtt5;

import com.hivemq.client.internal.mqtt.codec.decoder.MqttDecoderContext;
import com.hivemq.client.internal.mqtt.codec.decoder.MqttDecoderException;
import com.hivemq.client.internal.mqtt.codec.decoder.MqttMessageDecoder;
import com.hivemq.client.internal.mqtt.codec.decoder.MqttMessageDecoderUtil;
import com.hivemq.client.internal.mqtt.datatypes.MqttUserPropertiesImpl;
import com.hivemq.client.internal.mqtt.datatypes.MqttUserPropertyImpl;
import com.hivemq.client.internal.mqtt.datatypes.MqttUtf8StringImpl;
import com.hivemq.client.internal.mqtt.message.auth.MqttAuth;
import com.hivemq.client.internal.util.collections.ImmutableList;
import com.hivemq.client.mqtt.mqtt5.message.auth.Mqtt5AuthReasonCode;
import com.hivemq.client.mqtt.mqtt5.message.disconnect.Mqtt5DisconnectReasonCode;
import io.netty.buffer.ByteBuf;
import java.nio.ByteBuffer;
import javax.inject.Inject;
import javax.inject.Singleton;

/* JADX INFO: loaded from: classes10.dex */
@Singleton
public class Mqtt5AuthDecoder implements MqttMessageDecoder {
    private static final int FLAGS = 0;
    private static final int MIN_REMAINING_LENGTH = 2;

    @Inject
    Mqtt5AuthDecoder() {
    }

    @Override // com.hivemq.client.internal.mqtt.codec.decoder.MqttMessageDecoder
    public MqttAuth decode(int i, ByteBuf byteBuf, MqttDecoderContext mqttDecoderContext) throws MqttDecoderException {
        MqttMessageDecoderUtil.checkFixedHeaderFlags(0, i);
        if (byteBuf.readableBytes() < 2) {
            throw MqttMessageDecoderUtil.remainingLengthTooShort();
        }
        Mqtt5AuthReasonCode mqtt5AuthReasonCodeFromCode = Mqtt5AuthReasonCode.fromCode(byteBuf.readUnsignedByte());
        if (mqtt5AuthReasonCodeFromCode == null) {
            throw Mqtt5MessageDecoderUtil.wrongReasonCode();
        }
        Mqtt5MessageDecoderUtil.checkPropertyLengthNoPayload(byteBuf);
        ImmutableList.Builder<MqttUserPropertyImpl> builderDecodeUserPropertyIfRequested = null;
        MqttUtf8StringImpl mqttUtf8StringImplDecodeAuthMethod = null;
        ByteBuffer byteBufferDecodeAuthData = null;
        MqttUtf8StringImpl mqttUtf8StringImplDecodeReasonStringIfRequested = null;
        while (byteBuf.isReadable()) {
            int iDecodePropertyIdentifier = Mqtt5MessageDecoderUtil.decodePropertyIdentifier(byteBuf);
            if (iDecodePropertyIdentifier == 21) {
                mqttUtf8StringImplDecodeAuthMethod = Mqtt5MessageDecoderUtil.decodeAuthMethod(mqttUtf8StringImplDecodeAuthMethod, byteBuf);
            } else if (iDecodePropertyIdentifier == 22) {
                byteBufferDecodeAuthData = Mqtt5MessageDecoderUtil.decodeAuthData(byteBufferDecodeAuthData, byteBuf, mqttDecoderContext);
            } else if (iDecodePropertyIdentifier == 31) {
                mqttUtf8StringImplDecodeReasonStringIfRequested = Mqtt5MessageDecoderUtil.decodeReasonStringIfRequested(mqttUtf8StringImplDecodeReasonStringIfRequested, byteBuf, mqttDecoderContext);
            } else if (iDecodePropertyIdentifier == 38) {
                builderDecodeUserPropertyIfRequested = Mqtt5MessageDecoderUtil.decodeUserPropertyIfRequested(builderDecodeUserPropertyIfRequested, byteBuf, mqttDecoderContext);
            } else {
                throw Mqtt5MessageDecoderUtil.wrongProperty(iDecodePropertyIdentifier);
            }
        }
        if (mqttUtf8StringImplDecodeAuthMethod == null) {
            throw new MqttDecoderException(Mqtt5DisconnectReasonCode.PROTOCOL_ERROR, "must not omit authentication method");
        }
        return new MqttAuth(mqtt5AuthReasonCodeFromCode, mqttUtf8StringImplDecodeAuthMethod, byteBufferDecodeAuthData, mqttUtf8StringImplDecodeReasonStringIfRequested, MqttUserPropertiesImpl.build(builderDecodeUserPropertyIfRequested));
    }
}
