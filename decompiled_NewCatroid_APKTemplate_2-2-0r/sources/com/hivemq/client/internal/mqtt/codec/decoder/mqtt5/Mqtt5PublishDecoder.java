package com.hivemq.client.internal.mqtt.codec.decoder.mqtt5;

import com.hivemq.client.internal.mqtt.codec.decoder.MqttDecoderContext;
import com.hivemq.client.internal.mqtt.codec.decoder.MqttDecoderException;
import com.hivemq.client.internal.mqtt.codec.decoder.MqttMessageDecoder;
import com.hivemq.client.internal.mqtt.codec.decoder.MqttMessageDecoderUtil;
import com.hivemq.client.internal.mqtt.datatypes.MqttBinaryData;
import com.hivemq.client.internal.mqtt.datatypes.MqttTopicImpl;
import com.hivemq.client.internal.mqtt.datatypes.MqttUserPropertiesImpl;
import com.hivemq.client.internal.mqtt.datatypes.MqttUserPropertyImpl;
import com.hivemq.client.internal.mqtt.datatypes.MqttUtf8StringImpl;
import com.hivemq.client.internal.mqtt.datatypes.MqttVariableByteInteger;
import com.hivemq.client.internal.mqtt.message.publish.MqttPublish;
import com.hivemq.client.internal.mqtt.message.publish.MqttStatefulPublish;
import com.hivemq.client.internal.util.ByteBufferUtil;
import com.hivemq.client.internal.util.Utf8Util;
import com.hivemq.client.internal.util.collections.ImmutableIntList;
import com.hivemq.client.internal.util.collections.ImmutableList;
import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.mqtt5.message.disconnect.Mqtt5DisconnectReasonCode;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PayloadFormatIndicator;
import io.netty.buffer.ByteBuf;
import java.nio.ByteBuffer;
import javax.inject.Inject;
import javax.inject.Singleton;

/* JADX INFO: loaded from: classes10.dex */
@Singleton
public class Mqtt5PublishDecoder implements MqttMessageDecoder {
    private static final int MIN_REMAINING_LENGTH = 3;

    @Inject
    Mqtt5PublishDecoder() {
    }

    @Override // com.hivemq.client.internal.mqtt.codec.decoder.MqttMessageDecoder
    public MqttStatefulPublish decode(int i, ByteBuf byteBuf, MqttDecoderContext mqttDecoderContext) throws MqttDecoderException {
        MqttTopicImpl mqttTopicImpl;
        ByteBuffer byteBuffer;
        ImmutableIntList immutableIntListBuild;
        int i2 = 1;
        boolean z = (i & 8) != 0;
        MqttQos mqttQosDecodePublishQos = MqttMessageDecoderUtil.decodePublishQos(i, z);
        boolean z2 = (i & 1) != 0;
        if (byteBuf.readableBytes() < 3) {
            throw MqttMessageDecoderUtil.remainingLengthTooShort();
        }
        byte[] bArrDecode = MqttBinaryData.decode(byteBuf);
        if (bArrDecode == null) {
            throw MqttMessageDecoderUtil.malformedTopic();
        }
        if (bArrDecode.length != 0) {
            MqttTopicImpl mqttTopicImplOf = MqttTopicImpl.of(bArrDecode);
            if (mqttTopicImplOf == null) {
                throw MqttMessageDecoderUtil.malformedTopic();
            }
            mqttTopicImpl = mqttTopicImplOf;
        } else {
            mqttTopicImpl = null;
        }
        int iDecodePublishPacketIdentifier = MqttMessageDecoderUtil.decodePublishPacketIdentifier(mqttQosDecodePublishQos, byteBuf);
        int iDecodePropertyLength = Mqtt5MessageDecoderUtil.decodePropertyLength(byteBuf);
        int i3 = byteBuf.readerIndex();
        long jUnsignedIntOnlyOnce = Long.MAX_VALUE;
        MqttUtf8StringImpl mqttUtf8StringImplDecodeUTF8StringOnlyOnce = null;
        ByteBuffer byteBufferDecodeBinaryDataOnlyOnce = null;
        Mqtt5PayloadFormatIndicator mqtt5PayloadFormatIndicatorFromCode = null;
        ImmutableList.Builder<MqttUserPropertyImpl> builderDecodeUserProperty = null;
        int iUnsignedShortOnlyOnce = 0;
        MqttTopicImpl mqttTopicImplDecode = null;
        ImmutableIntList.Builder builder = null;
        while (true) {
            int i4 = byteBuf.readerIndex() - i3;
            if (i4 >= iDecodePropertyLength) {
                MqttUtf8StringImpl mqttUtf8StringImpl = mqttUtf8StringImplDecodeUTF8StringOnlyOnce;
                ByteBuffer byteBuffer2 = byteBufferDecodeBinaryDataOnlyOnce;
                if (i4 != iDecodePropertyLength) {
                    throw Mqtt5MessageDecoderUtil.malformedPropertyLength();
                }
                if (iUnsignedShortOnlyOnce != 0) {
                    MqttTopicImpl[] topicAliasMapping = mqttDecoderContext.getTopicAliasMapping();
                    if (topicAliasMapping == null || iUnsignedShortOnlyOnce > topicAliasMapping.length) {
                        throw new MqttDecoderException(Mqtt5DisconnectReasonCode.TOPIC_ALIAS_INVALID, "topic alias must not exceed topic alias maximum");
                    }
                    if (mqttTopicImpl == null) {
                        mqttTopicImpl = topicAliasMapping[iUnsignedShortOnlyOnce - 1];
                        if (mqttTopicImpl == null) {
                            throw new MqttDecoderException(Mqtt5DisconnectReasonCode.TOPIC_ALIAS_INVALID, "topic alias has no mapping");
                        }
                    } else {
                        topicAliasMapping[iUnsignedShortOnlyOnce - 1] = mqttTopicImpl;
                        iUnsignedShortOnlyOnce |= 65536;
                    }
                } else if (mqttTopicImpl == null) {
                    throw new MqttDecoderException(Mqtt5DisconnectReasonCode.PROTOCOL_ERROR, "topic alias must be present if topic name is zero length");
                }
                int i5 = iUnsignedShortOnlyOnce;
                MqttTopicImpl mqttTopicImpl2 = mqttTopicImpl;
                int i6 = byteBuf.readableBytes();
                if (i6 > 0) {
                    ByteBuffer byteBufferAllocate = ByteBufferUtil.allocate(i6, mqttDecoderContext.useDirectBufferPayload());
                    byteBuf.readBytes(byteBufferAllocate);
                    byteBufferAllocate.position(0);
                    if (mqtt5PayloadFormatIndicatorFromCode == Mqtt5PayloadFormatIndicator.UTF_8 && mqttDecoderContext.validatePayloadFormat() && Utf8Util.isWellFormed(ByteBufferUtil.getBytes(byteBufferAllocate)) != 0) {
                        throw new MqttDecoderException(Mqtt5DisconnectReasonCode.PAYLOAD_FORMAT_INVALID, "payload is not valid UTF-8");
                    }
                    byteBuffer = byteBufferAllocate;
                } else {
                    byteBuffer = null;
                }
                MqttPublish mqttPublish = new MqttPublish(mqttTopicImpl2, byteBuffer, mqttQosDecodePublishQos, z2, jUnsignedIntOnlyOnce, mqtt5PayloadFormatIndicatorFromCode, mqttUtf8StringImpl, mqttTopicImplDecode, byteBuffer2, MqttUserPropertiesImpl.build(builderDecodeUserProperty), null);
                if (builder == null) {
                    immutableIntListBuild = MqttStatefulPublish.DEFAULT_NO_SUBSCRIPTION_IDENTIFIERS;
                } else {
                    immutableIntListBuild = builder.build();
                }
                return mqttPublish.createStateful(iDecodePublishPacketIdentifier, z, i5, immutableIntListBuild);
            }
            int iDecodePropertyIdentifier = Mqtt5MessageDecoderUtil.decodePropertyIdentifier(byteBuf);
            if (iDecodePropertyIdentifier != i2) {
                if (iDecodePropertyIdentifier != 2) {
                    if (iDecodePropertyIdentifier == 3) {
                        mqttUtf8StringImplDecodeUTF8StringOnlyOnce = Mqtt5MessageDecoderUtil.decodeUTF8StringOnlyOnce(mqttUtf8StringImplDecodeUTF8StringOnlyOnce, "content type", byteBuf);
                    } else if (iDecodePropertyIdentifier != 8) {
                        if (iDecodePropertyIdentifier == 9) {
                            byteBufferDecodeBinaryDataOnlyOnce = Mqtt5MessageDecoderUtil.decodeBinaryDataOnlyOnce(byteBufferDecodeBinaryDataOnlyOnce, "correlation data", byteBuf, mqttDecoderContext.useDirectBufferCorrelationData());
                        } else if (iDecodePropertyIdentifier == 11) {
                            if (builder == null) {
                                builder = ImmutableIntList.builder();
                            }
                            ImmutableIntList.Builder builder2 = builder;
                            int iDecode = MqttVariableByteInteger.decode(byteBuf);
                            if (iDecode < 0) {
                                throw new MqttDecoderException("malformed subscription identifier");
                            }
                            if (iDecode == 0) {
                                throw new MqttDecoderException(Mqtt5DisconnectReasonCode.PROTOCOL_ERROR, "subscription identifier must not be 0");
                            }
                            builder2.add(iDecode);
                            builder = builder2;
                        } else if (iDecodePropertyIdentifier == 35) {
                            iUnsignedShortOnlyOnce = Mqtt5MessageDecoderUtil.unsignedShortOnlyOnce(iUnsignedShortOnlyOnce, 0, "topic alias", byteBuf);
                            if (iUnsignedShortOnlyOnce == 0) {
                                throw new MqttDecoderException(Mqtt5DisconnectReasonCode.TOPIC_ALIAS_INVALID, "topic alias must not be 0");
                            }
                        } else if (iDecodePropertyIdentifier == 38) {
                            builderDecodeUserProperty = Mqtt5MessageDecoderUtil.decodeUserProperty(builderDecodeUserProperty, byteBuf);
                        } else {
                            throw Mqtt5MessageDecoderUtil.wrongProperty(iDecodePropertyIdentifier);
                        }
                    } else {
                        if (mqttTopicImplDecode != null) {
                            throw Mqtt5MessageDecoderUtil.moreThanOnce("response topic");
                        }
                        mqttTopicImplDecode = MqttTopicImpl.decode(byteBuf);
                        if (mqttTopicImplDecode == null) {
                            throw new MqttDecoderException(Mqtt5DisconnectReasonCode.TOPIC_NAME_INVALID, "malformed response topic");
                        }
                    }
                    iDecodePropertyLength = iDecodePropertyLength;
                } else {
                    jUnsignedIntOnlyOnce = Mqtt5MessageDecoderUtil.unsignedIntOnlyOnce(jUnsignedIntOnlyOnce, Long.MAX_VALUE, "message expiry interval", byteBuf);
                    mqtt5PayloadFormatIndicatorFromCode = mqtt5PayloadFormatIndicatorFromCode;
                }
                iDecodePropertyLength = iDecodePropertyLength;
                i2 = 1;
            } else {
                short sUnsignedByteOnlyOnce = Mqtt5MessageDecoderUtil.unsignedByteOnlyOnce(mqtt5PayloadFormatIndicatorFromCode != null, "payload format indicator", byteBuf);
                mqtt5PayloadFormatIndicatorFromCode = Mqtt5PayloadFormatIndicator.fromCode(sUnsignedByteOnlyOnce);
                if (mqtt5PayloadFormatIndicatorFromCode == null) {
                    throw new MqttDecoderException("wrong payload format indicator: " + ((int) sUnsignedByteOnlyOnce));
                }
            }
            mqttUtf8StringImplDecodeUTF8StringOnlyOnce = mqttUtf8StringImplDecodeUTF8StringOnlyOnce;
            byteBufferDecodeBinaryDataOnlyOnce = byteBufferDecodeBinaryDataOnlyOnce;
            iDecodePropertyLength = iDecodePropertyLength;
            i2 = 1;
        }
    }
}
