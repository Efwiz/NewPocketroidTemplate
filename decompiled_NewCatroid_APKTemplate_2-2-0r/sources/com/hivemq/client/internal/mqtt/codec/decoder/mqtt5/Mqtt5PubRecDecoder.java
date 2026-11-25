package com.hivemq.client.internal.mqtt.codec.decoder.mqtt5;

import com.hivemq.client.internal.mqtt.codec.decoder.MqttDecoderContext;
import com.hivemq.client.internal.mqtt.codec.decoder.MqttDecoderException;
import com.hivemq.client.internal.mqtt.codec.decoder.MqttMessageDecoder;
import com.hivemq.client.internal.mqtt.codec.decoder.MqttMessageDecoderUtil;
import com.hivemq.client.internal.mqtt.datatypes.MqttUserPropertiesImpl;
import com.hivemq.client.internal.mqtt.datatypes.MqttUserPropertyImpl;
import com.hivemq.client.internal.mqtt.datatypes.MqttUtf8StringImpl;
import com.hivemq.client.internal.mqtt.message.publish.pubrec.MqttPubRec;
import com.hivemq.client.internal.util.collections.ImmutableList;
import com.hivemq.client.mqtt.mqtt5.message.publish.pubrec.Mqtt5PubRecReasonCode;
import io.netty.buffer.ByteBuf;
import javax.inject.Inject;
import javax.inject.Singleton;

/* JADX INFO: loaded from: classes10.dex */
@Singleton
public class Mqtt5PubRecDecoder implements MqttMessageDecoder {
    private static final int FLAGS = 0;
    private static final int MIN_REMAINING_LENGTH = 2;

    @Inject
    Mqtt5PubRecDecoder() {
    }

    /* JADX WARN: Code duplicated, block: B:24:0x0055 A[PHI: r0
      0x0055: PHI (r0v3 com.hivemq.client.mqtt.mqtt5.message.publish.pubrec.Mqtt5PubRecReasonCode) = 
      (r0v2 com.hivemq.client.mqtt.mqtt5.message.publish.pubrec.Mqtt5PubRecReasonCode)
      (r0v6 com.hivemq.client.mqtt.mqtt5.message.publish.pubrec.Mqtt5PubRecReasonCode)
     binds: [B:5:0x0016, B:9:0x0026] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // com.hivemq.client.internal.mqtt.codec.decoder.MqttMessageDecoder
    public MqttPubRec decode(int i, ByteBuf byteBuf, MqttDecoderContext mqttDecoderContext) throws MqttDecoderException {
        MqttUtf8StringImpl mqttUtf8StringImpl;
        MqttMessageDecoderUtil.checkFixedHeaderFlags(0, i);
        if (byteBuf.readableBytes() < 2) {
            throw MqttMessageDecoderUtil.remainingLengthTooShort();
        }
        int unsignedShort = byteBuf.readUnsignedShort();
        Mqtt5PubRecReasonCode mqtt5PubRecReasonCodeFromCode = MqttPubRec.DEFAULT_REASON_CODE;
        ImmutableList.Builder<MqttUserPropertyImpl> builder = null;
        MqttUtf8StringImpl mqttUtf8StringImplDecodeReasonStringIfRequested = null;
        if (byteBuf.isReadable()) {
            mqtt5PubRecReasonCodeFromCode = Mqtt5PubRecReasonCode.fromCode(byteBuf.readUnsignedByte());
            if (mqtt5PubRecReasonCodeFromCode == null) {
                throw Mqtt5MessageDecoderUtil.wrongReasonCode();
            }
            if (byteBuf.isReadable()) {
                Mqtt5MessageDecoderUtil.checkPropertyLengthNoPayload(byteBuf);
                ImmutableList.Builder<MqttUserPropertyImpl> builderDecodeUserPropertyIfRequested = null;
                while (byteBuf.isReadable()) {
                    int iDecodePropertyIdentifier = Mqtt5MessageDecoderUtil.decodePropertyIdentifier(byteBuf);
                    if (iDecodePropertyIdentifier == 31) {
                        mqttUtf8StringImplDecodeReasonStringIfRequested = Mqtt5MessageDecoderUtil.decodeReasonStringIfRequested(mqttUtf8StringImplDecodeReasonStringIfRequested, byteBuf, mqttDecoderContext);
                    } else if (iDecodePropertyIdentifier == 38) {
                        builderDecodeUserPropertyIfRequested = Mqtt5MessageDecoderUtil.decodeUserPropertyIfRequested(builderDecodeUserPropertyIfRequested, byteBuf, mqttDecoderContext);
                    } else {
                        throw Mqtt5MessageDecoderUtil.wrongProperty(iDecodePropertyIdentifier);
                    }
                }
                mqttUtf8StringImpl = mqttUtf8StringImplDecodeReasonStringIfRequested;
                builder = builderDecodeUserPropertyIfRequested;
            } else {
                mqttUtf8StringImpl = null;
            }
        } else {
            mqttUtf8StringImpl = null;
        }
        return new MqttPubRec(unsignedShort, mqtt5PubRecReasonCodeFromCode, mqttUtf8StringImpl, MqttUserPropertiesImpl.build(builder));
    }
}
