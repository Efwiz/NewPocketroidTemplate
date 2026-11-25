package com.hivemq.client.internal.mqtt.codec.encoder.mqtt3;

import com.hivemq.client.internal.mqtt.codec.encoder.MqttMessageEncoderUtil;
import com.hivemq.client.internal.mqtt.datatypes.MqttUtf8StringImpl;
import com.hivemq.client.internal.mqtt.datatypes.MqttVariableByteInteger;
import com.hivemq.client.internal.mqtt.message.auth.MqttSimpleAuth;
import com.hivemq.client.internal.mqtt.message.connect.MqttConnect;
import com.hivemq.client.internal.mqtt.message.connect.MqttStatefulConnect;
import com.hivemq.client.internal.mqtt.message.publish.MqttWillPublish;
import com.hivemq.client.mqtt.mqtt3.message.Mqtt3MessageType;
import io.netty.buffer.ByteBuf;
import javax.inject.Inject;
import javax.inject.Singleton;

/* JADX INFO: loaded from: classes10.dex */
@Singleton
public class Mqtt3ConnectEncoder extends Mqtt3MessageEncoder<MqttStatefulConnect> {
    private static final int FIXED_HEADER = Mqtt3MessageType.CONNECT.getCode() << 4;
    private static final byte PROTOCOL_VERSION = 4;
    private static final int VARIABLE_HEADER_FIXED_LENGTH = 10;

    @Inject
    Mqtt3ConnectEncoder() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.hivemq.client.internal.mqtt.codec.encoder.mqtt3.Mqtt3MessageEncoder
    public int remainingLength(MqttStatefulConnect mqttStatefulConnect) {
        MqttConnect mqttConnectStateless = mqttStatefulConnect.stateless();
        int iEncodedLength = mqttStatefulConnect.getClientIdentifier().encodedLength() + 10;
        MqttSimpleAuth rawSimpleAuth = mqttConnectStateless.getRawSimpleAuth();
        if (rawSimpleAuth != null) {
            iEncodedLength = iEncodedLength + MqttMessageEncoderUtil.nullableEncodedLength(rawSimpleAuth.getRawUsername()) + MqttMessageEncoderUtil.nullableEncodedLength(rawSimpleAuth.getRawPassword());
        }
        MqttWillPublish rawWillPublish = mqttConnectStateless.getRawWillPublish();
        return rawWillPublish != null ? iEncodedLength + rawWillPublish.getTopic().encodedLength() + MqttMessageEncoderUtil.encodedOrEmptyLength(rawWillPublish.getRawPayload()) : iEncodedLength;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.hivemq.client.internal.mqtt.codec.encoder.mqtt3.Mqtt3MessageEncoder
    public void encode(MqttStatefulConnect mqttStatefulConnect, ByteBuf byteBuf, int i) {
        encodeFixedHeader(byteBuf, i);
        encodeVariableHeader(mqttStatefulConnect, byteBuf);
        encodePayload(mqttStatefulConnect, byteBuf);
    }

    private void encodeFixedHeader(ByteBuf byteBuf, int i) {
        byteBuf.writeByte(FIXED_HEADER);
        MqttVariableByteInteger.encode(i, byteBuf);
    }

    private void encodeVariableHeader(MqttStatefulConnect mqttStatefulConnect, ByteBuf byteBuf) {
        MqttConnect mqttConnectStateless = mqttStatefulConnect.stateless();
        MqttUtf8StringImpl.PROTOCOL_NAME.encode(byteBuf);
        byteBuf.writeByte(4);
        MqttSimpleAuth rawSimpleAuth = mqttConnectStateless.getRawSimpleAuth();
        int code = 0;
        if (rawSimpleAuth != null) {
            code = rawSimpleAuth.getRawUsername() != null ? 128 : 0;
            if (rawSimpleAuth.getRawPassword() != null) {
                code |= 64;
            }
        }
        MqttWillPublish rawWillPublish = mqttConnectStateless.getRawWillPublish();
        if (rawWillPublish != null) {
            code = code | 4 | (rawWillPublish.getQos().getCode() << 3);
            if (rawWillPublish.isRetain()) {
                code |= 32;
            }
        }
        if (mqttConnectStateless.isCleanStart()) {
            code |= 2;
        }
        byteBuf.writeByte(code);
        byteBuf.writeShort(mqttConnectStateless.getKeepAlive());
    }

    private void encodePayload(MqttStatefulConnect mqttStatefulConnect, ByteBuf byteBuf) {
        mqttStatefulConnect.getClientIdentifier().encode(byteBuf);
        encodeWillPublish(mqttStatefulConnect, byteBuf);
        MqttSimpleAuth rawSimpleAuth = mqttStatefulConnect.stateless().getRawSimpleAuth();
        if (rawSimpleAuth != null) {
            MqttMessageEncoderUtil.encodeNullable(rawSimpleAuth.getRawUsername(), byteBuf);
            MqttMessageEncoderUtil.encodeNullable(rawSimpleAuth.getRawPassword(), byteBuf);
        }
    }

    private void encodeWillPublish(MqttStatefulConnect mqttStatefulConnect, ByteBuf byteBuf) {
        MqttWillPublish rawWillPublish = mqttStatefulConnect.stateless().getRawWillPublish();
        if (rawWillPublish != null) {
            rawWillPublish.getTopic().encode(byteBuf);
            MqttMessageEncoderUtil.encodeNullable(rawWillPublish.getRawPayload(), byteBuf);
        }
    }
}
