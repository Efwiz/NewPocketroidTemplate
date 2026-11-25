package com.hivemq.client.internal.mqtt.codec.decoder.mqtt5;

import com.hivemq.client.internal.mqtt.codec.decoder.MqttDecoderContext;
import com.hivemq.client.internal.mqtt.codec.decoder.MqttDecoderException;
import com.hivemq.client.internal.mqtt.codec.decoder.MqttMessageDecoder;
import com.hivemq.client.internal.mqtt.codec.decoder.MqttMessageDecoderUtil;
import com.hivemq.client.internal.mqtt.datatypes.MqttUserPropertiesImpl;
import com.hivemq.client.internal.mqtt.datatypes.MqttUserPropertyImpl;
import com.hivemq.client.internal.mqtt.datatypes.MqttUtf8StringImpl;
import com.hivemq.client.internal.mqtt.message.disconnect.MqttDisconnect;
import com.hivemq.client.internal.util.collections.ImmutableList;
import com.hivemq.client.mqtt.mqtt5.message.disconnect.Mqtt5DisconnectReasonCode;
import io.netty.buffer.ByteBuf;
import javax.inject.Inject;
import javax.inject.Singleton;

/* JADX INFO: loaded from: classes10.dex */
@Singleton
public class Mqtt5DisconnectDecoder implements MqttMessageDecoder {
    private static final int FLAGS = 0;

    @Inject
    Mqtt5DisconnectDecoder() {
    }

    /* JADX WARN: Code duplicated, block: B:28:0x0060 A[PHI: r10
      0x0060: PHI (r10v2 com.hivemq.client.mqtt.mqtt5.message.disconnect.Mqtt5DisconnectReasonCode) = 
      (r10v1 com.hivemq.client.mqtt.mqtt5.message.disconnect.Mqtt5DisconnectReasonCode)
      (r10v6 com.hivemq.client.mqtt.mqtt5.message.disconnect.Mqtt5DisconnectReasonCode)
     binds: [B:3:0x000d, B:7:0x001d] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // com.hivemq.client.internal.mqtt.codec.decoder.MqttMessageDecoder
    public MqttDisconnect decode(int i, ByteBuf byteBuf, MqttDecoderContext mqttDecoderContext) throws MqttDecoderException {
        MqttUtf8StringImpl mqttUtf8StringImpl;
        MqttUtf8StringImpl mqttUtf8StringImpl2;
        MqttMessageDecoderUtil.checkFixedHeaderFlags(0, i);
        Mqtt5DisconnectReasonCode mqtt5DisconnectReasonCodeFromCode = MqttDisconnect.DEFAULT_REASON_CODE;
        long jDecodeSessionExpiryInterval = -1;
        ImmutableList.Builder<MqttUserPropertyImpl> builder = null;
        MqttUtf8StringImpl mqttUtf8StringImplDecodeServerReference = null;
        if (byteBuf.isReadable()) {
            mqtt5DisconnectReasonCodeFromCode = Mqtt5DisconnectReasonCode.fromCode(byteBuf.readUnsignedByte());
            if (mqtt5DisconnectReasonCodeFromCode == null) {
                throw Mqtt5MessageDecoderUtil.wrongReasonCode();
            }
            if (byteBuf.isReadable()) {
                Mqtt5MessageDecoderUtil.checkPropertyLengthNoPayload(byteBuf);
                MqttUtf8StringImpl mqttUtf8StringImplDecodeReasonString = null;
                ImmutableList.Builder<MqttUserPropertyImpl> builderDecodeUserProperty = null;
                while (byteBuf.isReadable()) {
                    int iDecodePropertyIdentifier = Mqtt5MessageDecoderUtil.decodePropertyIdentifier(byteBuf);
                    if (iDecodePropertyIdentifier == 17) {
                        jDecodeSessionExpiryInterval = Mqtt5MessageDecoderUtil.decodeSessionExpiryInterval(jDecodeSessionExpiryInterval, byteBuf);
                    } else if (iDecodePropertyIdentifier == 28) {
                        mqttUtf8StringImplDecodeServerReference = Mqtt5MessageDecoderUtil.decodeServerReference(mqttUtf8StringImplDecodeServerReference, byteBuf);
                    } else if (iDecodePropertyIdentifier == 31) {
                        mqttUtf8StringImplDecodeReasonString = Mqtt5MessageDecoderUtil.decodeReasonString(mqttUtf8StringImplDecodeReasonString, byteBuf);
                    } else if (iDecodePropertyIdentifier == 38) {
                        builderDecodeUserProperty = Mqtt5MessageDecoderUtil.decodeUserProperty(builderDecodeUserProperty, byteBuf);
                    } else {
                        throw Mqtt5MessageDecoderUtil.wrongProperty(iDecodePropertyIdentifier);
                    }
                }
                mqttUtf8StringImpl2 = mqttUtf8StringImplDecodeReasonString;
                mqttUtf8StringImpl = mqttUtf8StringImplDecodeServerReference;
                builder = builderDecodeUserProperty;
            } else {
                mqttUtf8StringImpl = null;
                mqttUtf8StringImpl2 = null;
            }
        } else {
            mqttUtf8StringImpl = null;
            mqttUtf8StringImpl2 = null;
        }
        return new MqttDisconnect(mqtt5DisconnectReasonCodeFromCode, jDecodeSessionExpiryInterval, mqttUtf8StringImpl, mqttUtf8StringImpl2, MqttUserPropertiesImpl.build(builder));
    }
}
