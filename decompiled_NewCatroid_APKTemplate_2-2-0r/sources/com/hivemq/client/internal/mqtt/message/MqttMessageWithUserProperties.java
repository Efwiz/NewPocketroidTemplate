package com.hivemq.client.internal.mqtt.message;

import com.hivemq.client.internal.mqtt.datatypes.MqttUserPropertiesImpl;
import com.hivemq.client.internal.mqtt.datatypes.MqttUtf8StringImpl;
import com.hivemq.client.internal.util.StringUtil;
import com.hivemq.client.internal.util.collections.ImmutableList;
import com.hivemq.client.mqtt.datatypes.MqttUtf8String;
import com.hivemq.client.mqtt.mqtt5.message.Mqtt5ReasonCode;
import java.util.Objects;
import java.util.Optional;

/* JADX INFO: loaded from: classes10.dex */
public abstract class MqttMessageWithUserProperties implements MqttMessage.WithUserProperties {
    private final MqttUserPropertiesImpl userProperties;

    protected MqttMessageWithUserProperties(MqttUserPropertiesImpl mqttUserPropertiesImpl) {
        this.userProperties = mqttUserPropertiesImpl;
    }

    @Override // com.hivemq.client.internal.mqtt.message.MqttMessage.WithUserProperties
    public MqttUserPropertiesImpl getUserProperties() {
        return this.userProperties;
    }

    protected String toAttributeString() {
        return this.userProperties.asList().isEmpty() ? "" : "userProperties=" + this.userProperties;
    }

    protected boolean partialEquals(MqttMessageWithUserProperties mqttMessageWithUserProperties) {
        return this.userProperties.equals(mqttMessageWithUserProperties.userProperties);
    }

    protected int partialHashCode() {
        return this.userProperties.hashCode();
    }

    public static abstract class WithReason extends MqttMessageWithUserProperties {
        private final MqttUtf8StringImpl reasonString;

        WithReason(MqttUtf8StringImpl mqttUtf8StringImpl, MqttUserPropertiesImpl mqttUserPropertiesImpl) {
            super(mqttUserPropertiesImpl);
            this.reasonString = mqttUtf8StringImpl;
        }

        public Optional<MqttUtf8String> getReasonString() {
            return Optional.ofNullable(this.reasonString);
        }

        public MqttUtf8StringImpl getRawReasonString() {
            return this.reasonString;
        }

        @Override // com.hivemq.client.internal.mqtt.message.MqttMessageWithUserProperties
        protected String toAttributeString() {
            return this.reasonString == null ? super.toAttributeString() : "reasonString=" + this.reasonString + StringUtil.prepend(", ", super.toAttributeString());
        }

        protected boolean partialEquals(WithReason withReason) {
            return super.partialEquals((MqttMessageWithUserProperties) withReason) && Objects.equals(this.reasonString, withReason.reasonString);
        }

        @Override // com.hivemq.client.internal.mqtt.message.MqttMessageWithUserProperties
        protected int partialHashCode() {
            return (super.partialHashCode() * 31) + Objects.hashCode(this.reasonString);
        }

        public static abstract class WithCode<R extends Mqtt5ReasonCode> extends WithReason {
            private final R reasonCode;

            protected WithCode(R r, MqttUtf8StringImpl mqttUtf8StringImpl, MqttUserPropertiesImpl mqttUserPropertiesImpl) {
                super(mqttUtf8StringImpl, mqttUserPropertiesImpl);
                this.reasonCode = r;
            }

            public R getReasonCode() {
                return this.reasonCode;
            }

            protected boolean partialEquals(WithCode<R> withCode) {
                return super.partialEquals((WithReason) withCode) && this.reasonCode.equals(withCode.reasonCode);
            }

            @Override // com.hivemq.client.internal.mqtt.message.MqttMessageWithUserProperties.WithReason, com.hivemq.client.internal.mqtt.message.MqttMessageWithUserProperties
            protected int partialHashCode() {
                return (super.partialHashCode() * 31) + this.reasonCode.hashCode();
            }

            public static abstract class WithId<R extends Mqtt5ReasonCode> extends WithCode<R> implements MqttMessage.WithId {
                private final int packetIdentifier;

                protected WithId(int i, R r, MqttUtf8StringImpl mqttUtf8StringImpl, MqttUserPropertiesImpl mqttUserPropertiesImpl) {
                    super(r, mqttUtf8StringImpl, mqttUserPropertiesImpl);
                    this.packetIdentifier = i;
                }

                @Override // com.hivemq.client.internal.mqtt.message.MqttMessage.WithId
                public int getPacketIdentifier() {
                    return this.packetIdentifier;
                }

                @Override // com.hivemq.client.internal.mqtt.message.MqttMessageWithUserProperties.WithReason, com.hivemq.client.internal.mqtt.message.MqttMessageWithUserProperties
                protected String toAttributeString() {
                    return "packetIdentifier=" + this.packetIdentifier + StringUtil.prepend(", ", super.toAttributeString());
                }
            }
        }

        public static abstract class WithCodesAndId<R extends Mqtt5ReasonCode> extends WithReason implements MqttMessage.WithId {
            private final int packetIdentifier;
            private final ImmutableList<R> reasonCodes;

            protected WithCodesAndId(int i, ImmutableList<R> immutableList, MqttUtf8StringImpl mqttUtf8StringImpl, MqttUserPropertiesImpl mqttUserPropertiesImpl) {
                super(mqttUtf8StringImpl, mqttUserPropertiesImpl);
                this.packetIdentifier = i;
                this.reasonCodes = immutableList;
            }

            @Override // com.hivemq.client.internal.mqtt.message.MqttMessage.WithId
            public int getPacketIdentifier() {
                return this.packetIdentifier;
            }

            public ImmutableList<R> getReasonCodes() {
                return this.reasonCodes;
            }

            @Override // com.hivemq.client.internal.mqtt.message.MqttMessageWithUserProperties.WithReason, com.hivemq.client.internal.mqtt.message.MqttMessageWithUserProperties
            protected String toAttributeString() {
                return "packetIdentifier=" + this.packetIdentifier + StringUtil.prepend(", ", super.toAttributeString());
            }

            protected boolean partialEquals(WithCodesAndId<R> withCodesAndId) {
                return super.partialEquals((WithReason) withCodesAndId) && this.reasonCodes.equals(withCodesAndId.reasonCodes);
            }

            @Override // com.hivemq.client.internal.mqtt.message.MqttMessageWithUserProperties.WithReason, com.hivemq.client.internal.mqtt.message.MqttMessageWithUserProperties
            protected int partialHashCode() {
                return (super.partialHashCode() * 31) + this.reasonCodes.hashCode();
            }
        }
    }
}
