package com.hivemq.client.internal.mqtt.codec.encoder.mqtt5;

import com.hivemq.client.internal.mqtt.codec.encoder.MqttEncoderContext;
import com.hivemq.client.internal.mqtt.codec.encoder.MqttMessageEncoder;
import com.hivemq.client.internal.mqtt.codec.encoder.MqttMessageEncoderUtil;
import com.hivemq.client.internal.mqtt.datatypes.MqttVariableByteInteger;
import com.hivemq.client.internal.mqtt.message.MqttMessage;
import com.hivemq.client.internal.mqtt.message.MqttMessage.WithUserProperties;
import com.hivemq.client.internal.mqtt.message.MqttMessageWithUserProperties;
import com.hivemq.client.mqtt.mqtt5.message.Mqtt5ReasonCode;
import io.netty.buffer.ByteBuf;

/* JADX INFO: loaded from: classes10.dex */
abstract class Mqtt5MessageWithUserPropertiesEncoder<M extends MqttMessage.WithUserProperties> extends MqttMessageEncoder<M> {
    abstract void encode(M m, ByteBuf byteBuf, int i, int i2, int i3);

    abstract int propertyLength(M m);

    abstract int remainingLengthWithoutProperties(M m);

    Mqtt5MessageWithUserPropertiesEncoder() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.hivemq.client.internal.mqtt.codec.encoder.MqttMessageEncoder
    public ByteBuf encode(M m, MqttEncoderContext mqttEncoderContext) {
        int iPropertyLength = propertyLength(m);
        int iRemainingLengthWithoutProperties = remainingLengthWithoutProperties(m);
        int iRemainingLength = remainingLength(m, iRemainingLengthWithoutProperties, iPropertyLength);
        int iPropertyLength2 = iPropertyLength;
        int iRemainingLength2 = iRemainingLength;
        int iEncodedPacketLength = MqttMessageEncoderUtil.encodedPacketLength(iRemainingLength);
        int i = 0;
        while (iEncodedPacketLength > mqttEncoderContext.getMaximumPacketSize()) {
            i++;
            iPropertyLength2 = propertyLength(m, iPropertyLength2, i);
            if (iPropertyLength2 < 0) {
                throw MqttMessageEncoderUtil.maximumPacketSizeExceeded(m, iEncodedPacketLength, mqttEncoderContext.getMaximumPacketSize());
            }
            iRemainingLength2 = remainingLength(m, iRemainingLengthWithoutProperties, iPropertyLength2);
            iEncodedPacketLength = MqttMessageEncoderUtil.encodedPacketLength(iRemainingLength2);
        }
        return encode(m, mqttEncoderContext, iEncodedPacketLength, iRemainingLength2, iPropertyLength2, i);
    }

    ByteBuf encode(M m, MqttEncoderContext mqttEncoderContext, int i, int i2, int i3, int i4) {
        ByteBuf byteBufIoBuffer = mqttEncoderContext.getAllocator().ioBuffer(i, i);
        encode(m, byteBufIoBuffer, i2, i3, i4);
        return byteBufIoBuffer;
    }

    private int remainingLength(M m, int i, int i2) {
        return i + propertyLengthWithHeader(m, i2);
    }

    int propertyLength(M m, int i, int i2) {
        if (i2 == 0) {
            return i;
        }
        if (i2 != 1) {
            return -1;
        }
        return i - m.getUserProperties().encodedLength();
    }

    int propertyLengthWithHeader(M m, int i) {
        return MqttMessageEncoderUtil.encodedLengthWithHeader(i);
    }

    int omissiblePropertyLength(M m) {
        return m.getUserProperties().encodedLength();
    }

    void encodeOmissibleProperties(M m, ByteBuf byteBuf, int i) {
        if (i == 0) {
            m.getUserProperties().encode(byteBuf);
        }
    }

    static abstract class WithReason<M extends MqttMessageWithUserProperties.WithReason> extends Mqtt5MessageWithUserPropertiesEncoder<M> {
        WithReason() {
        }

        @Override // com.hivemq.client.internal.mqtt.codec.encoder.mqtt5.Mqtt5MessageWithUserPropertiesEncoder, com.hivemq.client.internal.mqtt.codec.encoder.MqttMessageEncoder
        protected /* bridge */ /* synthetic */ ByteBuf encode(MqttMessage mqttMessage, MqttEncoderContext mqttEncoderContext) {
            return super.encode((MqttMessageWithUserProperties.WithReason) mqttMessage, mqttEncoderContext);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.hivemq.client.internal.mqtt.codec.encoder.mqtt5.Mqtt5MessageWithUserPropertiesEncoder
        public int propertyLength(M m, int i, int i2) {
            int iReasonStringLength;
            if (i2 == 0) {
                return i;
            }
            if (i2 == 1) {
                iReasonStringLength = reasonStringLength(m);
            } else {
                if (i2 != 2) {
                    return -1;
                }
                iReasonStringLength = m.getUserProperties().encodedLength();
            }
            return i - iReasonStringLength;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.hivemq.client.internal.mqtt.codec.encoder.mqtt5.Mqtt5MessageWithUserPropertiesEncoder
        public final int omissiblePropertyLength(M m) {
            return reasonStringLength(m) + m.getUserProperties().encodedLength();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.hivemq.client.internal.mqtt.codec.encoder.mqtt5.Mqtt5MessageWithUserPropertiesEncoder
        public void encodeOmissibleProperties(M m, ByteBuf byteBuf, int i) {
            if (i == 0) {
                Mqtt5MessageEncoderUtil.encodeNullableProperty(31, m.getRawReasonString(), byteBuf);
            }
            if (i <= 1) {
                m.getUserProperties().encode(byteBuf);
            }
        }

        private int reasonStringLength(M m) {
            return Mqtt5MessageEncoderUtil.nullablePropertyEncodedLength(m.getRawReasonString());
        }

        static abstract class WithOmissibleCode<M extends MqttMessageWithUserProperties.WithReason.WithCode<R>, R extends Mqtt5ReasonCode> extends WithReason<M> {
            int additionalPropertyLength(M m) {
                return 0;
            }

            int additionalRemainingLength(M m) {
                return 0;
            }

            void encodeAdditionalProperties(M m, ByteBuf byteBuf) {
            }

            void encodeAdditionalVariableHeader(M m, ByteBuf byteBuf) {
            }

            abstract R getDefaultReasonCode();

            abstract int getFixedHeader();

            WithOmissibleCode() {
            }

            @Override // com.hivemq.client.internal.mqtt.codec.encoder.mqtt5.Mqtt5MessageWithUserPropertiesEncoder.WithReason, com.hivemq.client.internal.mqtt.codec.encoder.mqtt5.Mqtt5MessageWithUserPropertiesEncoder, com.hivemq.client.internal.mqtt.codec.encoder.MqttMessageEncoder
            protected /* bridge */ /* synthetic */ ByteBuf encode(MqttMessage mqttMessage, MqttEncoderContext mqttEncoderContext) {
                return super.encode((MqttMessageWithUserProperties.WithReason.WithCode) mqttMessage, mqttEncoderContext);
            }

            @Override // com.hivemq.client.internal.mqtt.codec.encoder.mqtt5.Mqtt5MessageWithUserPropertiesEncoder.WithReason, com.hivemq.client.internal.mqtt.codec.encoder.mqtt5.Mqtt5MessageWithUserPropertiesEncoder
            /* bridge */ /* synthetic */ void encodeOmissibleProperties(MqttMessage.WithUserProperties withUserProperties, ByteBuf byteBuf, int i) {
                super.encodeOmissibleProperties((MqttMessageWithUserProperties.WithReason.WithCode) withUserProperties, byteBuf, i);
            }

            @Override // com.hivemq.client.internal.mqtt.codec.encoder.mqtt5.Mqtt5MessageWithUserPropertiesEncoder.WithReason, com.hivemq.client.internal.mqtt.codec.encoder.mqtt5.Mqtt5MessageWithUserPropertiesEncoder
            /* bridge */ /* synthetic */ int omissiblePropertyLength(MqttMessage.WithUserProperties withUserProperties) {
                return super.omissiblePropertyLength((MqttMessageWithUserProperties.WithReason.WithCode) withUserProperties);
            }

            @Override // com.hivemq.client.internal.mqtt.codec.encoder.mqtt5.Mqtt5MessageWithUserPropertiesEncoder.WithReason, com.hivemq.client.internal.mqtt.codec.encoder.mqtt5.Mqtt5MessageWithUserPropertiesEncoder
            /* bridge */ /* synthetic */ int propertyLength(MqttMessage.WithUserProperties withUserProperties, int i, int i2) {
                return super.propertyLength((MqttMessageWithUserProperties.WithReason.WithCode) withUserProperties, i, i2);
            }

            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // com.hivemq.client.internal.mqtt.codec.encoder.mqtt5.Mqtt5MessageWithUserPropertiesEncoder
            public final int remainingLengthWithoutProperties(M m) {
                return additionalRemainingLength(m) + 1;
            }

            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // com.hivemq.client.internal.mqtt.codec.encoder.mqtt5.Mqtt5MessageWithUserPropertiesEncoder
            public final int propertyLength(M m) {
                return omissiblePropertyLength(m) + additionalPropertyLength(m);
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.hivemq.client.internal.mqtt.codec.encoder.mqtt5.Mqtt5MessageWithUserPropertiesEncoder
            public void encode(M m, ByteBuf byteBuf, int i, int i2, int i3) {
                encodeFixedHeader(byteBuf, i);
                encodeVariableHeader(m, byteBuf, i2, i3);
            }

            private void encodeFixedHeader(ByteBuf byteBuf, int i) {
                byteBuf.writeByte(getFixedHeader());
                MqttVariableByteInteger.encode(i, byteBuf);
            }

            private void encodeVariableHeader(M m, ByteBuf byteBuf, int i, int i2) {
                encodeAdditionalVariableHeader(m, byteBuf);
                Mqtt5ReasonCode reasonCode = m.getReasonCode();
                if (i == 0) {
                    if (reasonCode != getDefaultReasonCode()) {
                        byteBuf.writeByte(reasonCode.getCode());
                    }
                } else {
                    byteBuf.writeByte(reasonCode.getCode());
                    MqttVariableByteInteger.encode(i, byteBuf);
                    encodeAdditionalProperties(m, byteBuf);
                    encodeOmissibleProperties(m, byteBuf, i2);
                }
            }

            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // com.hivemq.client.internal.mqtt.codec.encoder.mqtt5.Mqtt5MessageWithUserPropertiesEncoder
            public final int propertyLengthWithHeader(M m, int i) {
                if (i == 0) {
                    return m.getReasonCode() == getDefaultReasonCode() ? -1 : 0;
                }
                return super.propertyLengthWithHeader(m, i);
            }

            static abstract class WithId<M extends MqttMessageWithUserProperties.WithReason.WithCode.WithId<R>, R extends Mqtt5ReasonCode> extends WithOmissibleCode<M, R> {
                /* JADX INFO: Access modifiers changed from: package-private */
                @Override // com.hivemq.client.internal.mqtt.codec.encoder.mqtt5.Mqtt5MessageWithUserPropertiesEncoder.WithReason.WithOmissibleCode
                public int additionalRemainingLength(M m) {
                    return 2;
                }

                WithId() {
                }

                @Override // com.hivemq.client.internal.mqtt.codec.encoder.mqtt5.Mqtt5MessageWithUserPropertiesEncoder.WithReason.WithOmissibleCode, com.hivemq.client.internal.mqtt.codec.encoder.mqtt5.Mqtt5MessageWithUserPropertiesEncoder.WithReason, com.hivemq.client.internal.mqtt.codec.encoder.mqtt5.Mqtt5MessageWithUserPropertiesEncoder, com.hivemq.client.internal.mqtt.codec.encoder.MqttMessageEncoder
                protected /* bridge */ /* synthetic */ ByteBuf encode(MqttMessage mqttMessage, MqttEncoderContext mqttEncoderContext) {
                    return super.encode((MqttMessageWithUserProperties.WithReason.WithCode.WithId) mqttMessage, mqttEncoderContext);
                }

                @Override // com.hivemq.client.internal.mqtt.codec.encoder.mqtt5.Mqtt5MessageWithUserPropertiesEncoder.WithReason.WithOmissibleCode, com.hivemq.client.internal.mqtt.codec.encoder.mqtt5.Mqtt5MessageWithUserPropertiesEncoder
                protected /* bridge */ /* synthetic */ void encode(MqttMessage.WithUserProperties withUserProperties, ByteBuf byteBuf, int i, int i2, int i3) {
                    super.encode((MqttMessageWithUserProperties.WithReason.WithCode.WithId) withUserProperties, byteBuf, i, i2, i3);
                }

                @Override // com.hivemq.client.internal.mqtt.codec.encoder.mqtt5.Mqtt5MessageWithUserPropertiesEncoder.WithReason.WithOmissibleCode, com.hivemq.client.internal.mqtt.codec.encoder.mqtt5.Mqtt5MessageWithUserPropertiesEncoder.WithReason, com.hivemq.client.internal.mqtt.codec.encoder.mqtt5.Mqtt5MessageWithUserPropertiesEncoder
                /* bridge */ /* synthetic */ void encodeOmissibleProperties(MqttMessage.WithUserProperties withUserProperties, ByteBuf byteBuf, int i) {
                    super.encodeOmissibleProperties((MqttMessageWithUserProperties.WithReason.WithCode.WithId) withUserProperties, byteBuf, i);
                }

                @Override // com.hivemq.client.internal.mqtt.codec.encoder.mqtt5.Mqtt5MessageWithUserPropertiesEncoder.WithReason.WithOmissibleCode, com.hivemq.client.internal.mqtt.codec.encoder.mqtt5.Mqtt5MessageWithUserPropertiesEncoder.WithReason, com.hivemq.client.internal.mqtt.codec.encoder.mqtt5.Mqtt5MessageWithUserPropertiesEncoder
                /* bridge */ /* synthetic */ int omissiblePropertyLength(MqttMessage.WithUserProperties withUserProperties) {
                    return super.omissiblePropertyLength((MqttMessageWithUserProperties.WithReason.WithCode.WithId) withUserProperties);
                }

                @Override // com.hivemq.client.internal.mqtt.codec.encoder.mqtt5.Mqtt5MessageWithUserPropertiesEncoder.WithReason.WithOmissibleCode, com.hivemq.client.internal.mqtt.codec.encoder.mqtt5.Mqtt5MessageWithUserPropertiesEncoder
                /* bridge */ /* synthetic */ int propertyLength(MqttMessage.WithUserProperties withUserProperties) {
                    return super.propertyLength((MqttMessageWithUserProperties.WithReason.WithCode.WithId) withUserProperties);
                }

                @Override // com.hivemq.client.internal.mqtt.codec.encoder.mqtt5.Mqtt5MessageWithUserPropertiesEncoder.WithReason.WithOmissibleCode, com.hivemq.client.internal.mqtt.codec.encoder.mqtt5.Mqtt5MessageWithUserPropertiesEncoder.WithReason, com.hivemq.client.internal.mqtt.codec.encoder.mqtt5.Mqtt5MessageWithUserPropertiesEncoder
                /* bridge */ /* synthetic */ int propertyLength(MqttMessage.WithUserProperties withUserProperties, int i, int i2) {
                    return super.propertyLength((MqttMessageWithUserProperties.WithReason.WithCode.WithId) withUserProperties, i, i2);
                }

                @Override // com.hivemq.client.internal.mqtt.codec.encoder.mqtt5.Mqtt5MessageWithUserPropertiesEncoder.WithReason.WithOmissibleCode, com.hivemq.client.internal.mqtt.codec.encoder.mqtt5.Mqtt5MessageWithUserPropertiesEncoder
                /* bridge */ /* synthetic */ int propertyLengthWithHeader(MqttMessage.WithUserProperties withUserProperties, int i) {
                    return super.propertyLengthWithHeader((MqttMessageWithUserProperties.WithReason.WithCode.WithId) withUserProperties, i);
                }

                @Override // com.hivemq.client.internal.mqtt.codec.encoder.mqtt5.Mqtt5MessageWithUserPropertiesEncoder.WithReason.WithOmissibleCode, com.hivemq.client.internal.mqtt.codec.encoder.mqtt5.Mqtt5MessageWithUserPropertiesEncoder
                /* bridge */ /* synthetic */ int remainingLengthWithoutProperties(MqttMessage.WithUserProperties withUserProperties) {
                    return super.remainingLengthWithoutProperties((MqttMessageWithUserProperties.WithReason.WithCode.WithId) withUserProperties);
                }

                /* JADX INFO: Access modifiers changed from: package-private */
                @Override // com.hivemq.client.internal.mqtt.codec.encoder.mqtt5.Mqtt5MessageWithUserPropertiesEncoder.WithReason.WithOmissibleCode
                public void encodeAdditionalVariableHeader(M m, ByteBuf byteBuf) {
                    byteBuf.writeShort(m.getPacketIdentifier());
                }
            }
        }
    }
}
