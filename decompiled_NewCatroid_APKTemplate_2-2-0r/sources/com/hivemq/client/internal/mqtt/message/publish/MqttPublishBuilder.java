package com.hivemq.client.internal.mqtt.message.publish;

import com.hivemq.client.internal.mqtt.datatypes.MqttTopicImpl;
import com.hivemq.client.internal.mqtt.datatypes.MqttTopicImplBuilder;
import com.hivemq.client.internal.mqtt.datatypes.MqttUserPropertiesImpl;
import com.hivemq.client.internal.mqtt.datatypes.MqttUserPropertiesImplBuilder;
import com.hivemq.client.internal.mqtt.datatypes.MqttUtf8StringImpl;
import com.hivemq.client.internal.mqtt.message.publish.MqttPublishBuilder;
import com.hivemq.client.internal.mqtt.util.MqttChecks;
import com.hivemq.client.internal.util.ByteBufferUtil;
import com.hivemq.client.internal.util.Checks;
import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.datatypes.MqttTopic;
import com.hivemq.client.mqtt.datatypes.MqttTopicBuilder;
import com.hivemq.client.mqtt.datatypes.MqttUtf8String;
import com.hivemq.client.mqtt.mqtt5.datatypes.Mqtt5UserProperties;
import com.hivemq.client.mqtt.mqtt5.datatypes.Mqtt5UserPropertiesBuilder;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PayloadFormatIndicator;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilder;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5WillPublish;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5WillPublishBuilder;
import java.nio.ByteBuffer;
import java.util.function.Function;

/* JADX INFO: loaded from: classes10.dex */
public abstract class MqttPublishBuilder<B extends MqttPublishBuilder<B>> {
    MqttUtf8StringImpl contentType;
    ByteBuffer correlationData;
    long messageExpiryInterval;
    ByteBuffer payload;
    Mqtt5PayloadFormatIndicator payloadFormatIndicator;
    MqttQos qos;
    MqttTopicImpl responseTopic;
    boolean retain;
    MqttTopicImpl topic;
    MqttUserPropertiesImpl userProperties;

    abstract B self();

    MqttPublishBuilder() {
        this.qos = MqttPublish.DEFAULT_QOS;
        this.messageExpiryInterval = Long.MAX_VALUE;
        this.userProperties = MqttUserPropertiesImpl.NO_USER_PROPERTIES;
    }

    MqttPublishBuilder(MqttPublish mqttPublish) {
        this.qos = MqttPublish.DEFAULT_QOS;
        this.messageExpiryInterval = Long.MAX_VALUE;
        this.userProperties = MqttUserPropertiesImpl.NO_USER_PROPERTIES;
        this.topic = mqttPublish.getTopic();
        this.payload = mqttPublish.getRawPayload();
        this.qos = mqttPublish.getQos();
        this.retain = mqttPublish.isRetain();
        this.messageExpiryInterval = mqttPublish.getRawMessageExpiryInterval();
        this.payloadFormatIndicator = mqttPublish.getRawPayloadFormatIndicator();
        this.contentType = mqttPublish.getRawContentType();
        this.responseTopic = mqttPublish.getRawResponseTopic();
        this.correlationData = mqttPublish.getRawCorrelationData();
        this.userProperties = mqttPublish.getUserProperties();
    }

    MqttPublishBuilder(MqttPublishBuilder<?> mqttPublishBuilder) {
        this.qos = MqttPublish.DEFAULT_QOS;
        this.messageExpiryInterval = Long.MAX_VALUE;
        this.userProperties = MqttUserPropertiesImpl.NO_USER_PROPERTIES;
        this.topic = mqttPublishBuilder.topic;
        this.payload = mqttPublishBuilder.payload;
        this.qos = mqttPublishBuilder.qos;
        this.retain = mqttPublishBuilder.retain;
        this.messageExpiryInterval = mqttPublishBuilder.messageExpiryInterval;
        this.payloadFormatIndicator = mqttPublishBuilder.payloadFormatIndicator;
        this.contentType = mqttPublishBuilder.contentType;
        this.responseTopic = mqttPublishBuilder.responseTopic;
        this.correlationData = mqttPublishBuilder.correlationData;
        this.userProperties = mqttPublishBuilder.userProperties;
    }

    public B topic(String str) {
        this.topic = MqttTopicImpl.of(str);
        return (B) self();
    }

    public B topic(MqttTopic mqttTopic) {
        this.topic = MqttChecks.topic(mqttTopic);
        return (B) self();
    }

    public MqttTopicImplBuilder.Nested<B> topic() {
        return new MqttTopicImplBuilder.Nested<>(new Function() { // from class: com.hivemq.client.internal.mqtt.message.publish.MqttPublishBuilder$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.topic((MqttTopicImpl) obj);
            }
        });
    }

    public B qos(MqttQos mqttQos) {
        this.qos = (MqttQos) Checks.notNull(mqttQos, "QoS");
        return (B) self();
    }

    public B retain(boolean z) {
        this.retain = z;
        return (B) self();
    }

    public B messageExpiryInterval(long j) {
        this.messageExpiryInterval = Checks.unsignedInt(j, "Message expiry interval");
        return (B) self();
    }

    public B noMessageExpiry() {
        this.messageExpiryInterval = Long.MAX_VALUE;
        return (B) self();
    }

    public B payloadFormatIndicator(Mqtt5PayloadFormatIndicator mqtt5PayloadFormatIndicator) {
        this.payloadFormatIndicator = mqtt5PayloadFormatIndicator;
        return (B) self();
    }

    public B contentType(String str) {
        this.contentType = MqttChecks.stringOrNull(str, "Content type");
        return (B) self();
    }

    public B contentType(MqttUtf8String mqttUtf8String) {
        this.contentType = MqttChecks.stringOrNull(mqttUtf8String, "Content type");
        return (B) self();
    }

    public B responseTopic(String str) {
        this.responseTopic = str == null ? null : MqttTopicImpl.of(str, "Response topic");
        return (B) self();
    }

    public B responseTopic(MqttTopic mqttTopic) {
        this.responseTopic = (MqttTopicImpl) Checks.notImplementedOrNull(mqttTopic, MqttTopicImpl.class, "Response topic");
        return (B) self();
    }

    public MqttTopicImplBuilder.Nested<B> responseTopic() {
        return new MqttTopicImplBuilder.Nested<>(new Function() { // from class: com.hivemq.client.internal.mqtt.message.publish.MqttPublishBuilder$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.responseTopic((MqttTopicImpl) obj);
            }
        });
    }

    public B correlationData(byte[] bArr) {
        this.correlationData = MqttChecks.binaryDataOrNull(bArr, "Correlation data");
        return (B) self();
    }

    public B correlationData(ByteBuffer byteBuffer) {
        this.correlationData = MqttChecks.binaryDataOrNull(byteBuffer, "Correlation data");
        return (B) self();
    }

    public B userProperties(Mqtt5UserProperties mqtt5UserProperties) {
        this.userProperties = MqttChecks.userProperties(mqtt5UserProperties);
        return (B) self();
    }

    public MqttUserPropertiesImplBuilder.Nested<B> userProperties() {
        return new MqttUserPropertiesImplBuilder.Nested<>(this.userProperties, new Function() { // from class: com.hivemq.client.internal.mqtt.message.publish.MqttPublishBuilder$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.userProperties((MqttUserPropertiesImpl) obj);
            }
        });
    }

    private static abstract class Base<B extends Base<B>> extends MqttPublishBuilder<B> {
        Base() {
        }

        Base(MqttPublish mqttPublish) {
            super(mqttPublish);
        }

        public B payload(byte[] bArr) {
            this.payload = ByteBufferUtil.wrap(bArr);
            return self();
        }

        public B payload(ByteBuffer byteBuffer) {
            this.payload = ByteBufferUtil.slice(byteBuffer);
            return self();
        }

        public WillDefault asWill() {
            return new WillDefault(this);
        }

        public MqttPublish build() {
            Checks.notNull(this.topic, "Topic");
            return new MqttPublish(this.topic, this.payload, this.qos, this.retain, this.messageExpiryInterval, this.payloadFormatIndicator, this.contentType, this.responseTopic, this.correlationData, this.userProperties, null);
        }
    }

    public static class Default extends Base<Default> implements Mqtt5PublishBuilder.Complete {
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.hivemq.client.internal.mqtt.message.publish.MqttPublishBuilder
        public Default self() {
            return this;
        }

        @Override // com.hivemq.client.internal.mqtt.message.publish.MqttPublishBuilder.Base, com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilder.Complete, com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilder
        public /* bridge */ /* synthetic */ WillDefault asWill() {
            return super.asWill();
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilder.Complete, com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilder
        public /* bridge */ /* synthetic */ Mqtt5WillPublishBuilder.Complete asWill() {
            return super.asWill();
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilder.Complete, com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilder
        public /* bridge */ /* synthetic */ Mqtt5WillPublishBuilder asWill() {
            return super.asWill();
        }

        @Override // com.hivemq.client.internal.mqtt.message.publish.MqttPublishBuilder.Base, com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilder.Complete
        public /* bridge */ /* synthetic */ MqttPublish build() {
            return super.build();
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilder.Complete
        public /* bridge */ /* synthetic */ Mqtt5Publish build() {
            return super.build();
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5PublishBuilderBase.Complete contentType(MqttUtf8String mqttUtf8String) {
            return (Mqtt5PublishBuilderBase.Complete) super.contentType(mqttUtf8String);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5PublishBuilderBase.Complete contentType(String str) {
            return (Mqtt5PublishBuilderBase.Complete) super.contentType(str);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5PublishBuilderBase.Complete correlationData(ByteBuffer byteBuffer) {
            return (Mqtt5PublishBuilderBase.Complete) super.correlationData(byteBuffer);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5PublishBuilderBase.Complete correlationData(byte[] bArr) {
            return (Mqtt5PublishBuilderBase.Complete) super.correlationData(bArr);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5PublishBuilderBase.Complete messageExpiryInterval(long j) {
            return (Mqtt5PublishBuilderBase.Complete) super.messageExpiryInterval(j);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5PublishBuilderBase.Complete noMessageExpiry() {
            return (Mqtt5PublishBuilderBase.Complete) super.noMessageExpiry();
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5PublishBuilderBase.Complete payload(ByteBuffer byteBuffer) {
            return (Mqtt5PublishBuilderBase.Complete) super.payload(byteBuffer);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5PublishBuilderBase.Complete payload(byte[] bArr) {
            return (Mqtt5PublishBuilderBase.Complete) super.payload(bArr);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5PublishBuilderBase.Complete payloadFormatIndicator(Mqtt5PayloadFormatIndicator mqtt5PayloadFormatIndicator) {
            return (Mqtt5PublishBuilderBase.Complete) super.payloadFormatIndicator(mqtt5PayloadFormatIndicator);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5PublishBuilderBase.Complete qos(MqttQos mqttQos) {
            return (Mqtt5PublishBuilderBase.Complete) super.qos(mqttQos);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ MqttTopicBuilder.Nested responseTopic() {
            return super.responseTopic();
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5PublishBuilderBase.Complete responseTopic(MqttTopic mqttTopic) {
            return (Mqtt5PublishBuilderBase.Complete) super.responseTopic(mqttTopic);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5PublishBuilderBase.Complete responseTopic(String str) {
            return (Mqtt5PublishBuilderBase.Complete) super.responseTopic(str);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5PublishBuilderBase.Complete retain(boolean z) {
            return (Mqtt5PublishBuilderBase.Complete) super.retain(z);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase
        public /* bridge */ /* synthetic */ MqttTopicBuilder.Nested topic() {
            return super.topic();
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5PublishBuilderBase.Complete topic(MqttTopic mqttTopic) {
            return (Mqtt5PublishBuilderBase.Complete) super.topic(mqttTopic);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5PublishBuilderBase.Complete topic(String str) {
            return (Mqtt5PublishBuilderBase.Complete) super.topic(str);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5UserPropertiesBuilder.Nested userProperties() {
            return super.userProperties();
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5PublishBuilderBase.Complete userProperties(Mqtt5UserProperties mqtt5UserProperties) {
            return (Mqtt5PublishBuilderBase.Complete) super.userProperties(mqtt5UserProperties);
        }

        public Default() {
        }

        Default(MqttPublish mqttPublish) {
            super(mqttPublish);
        }
    }

    public static class Nested<P> extends Base<Nested<P>> implements Mqtt5PublishBuilder.Nested.Complete<P> {
        private final Function<? super MqttPublish, P> parentConsumer;

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.hivemq.client.internal.mqtt.message.publish.MqttPublishBuilder
        public Nested<P> self() {
            return this;
        }

        @Override // com.hivemq.client.internal.mqtt.message.publish.MqttPublishBuilder.Base, com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilder.Complete, com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilder
        public /* bridge */ /* synthetic */ WillDefault asWill() {
            return super.asWill();
        }

        @Override // com.hivemq.client.internal.mqtt.message.publish.MqttPublishBuilder.Base, com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilder.Complete
        public /* bridge */ /* synthetic */ MqttPublish build() {
            return super.build();
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5PublishBuilderBase.Complete contentType(MqttUtf8String mqttUtf8String) {
            return (Mqtt5PublishBuilderBase.Complete) super.contentType(mqttUtf8String);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5PublishBuilderBase.Complete contentType(String str) {
            return (Mqtt5PublishBuilderBase.Complete) super.contentType(str);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5PublishBuilderBase.Complete correlationData(ByteBuffer byteBuffer) {
            return (Mqtt5PublishBuilderBase.Complete) super.correlationData(byteBuffer);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5PublishBuilderBase.Complete correlationData(byte[] bArr) {
            return (Mqtt5PublishBuilderBase.Complete) super.correlationData(bArr);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5PublishBuilderBase.Complete messageExpiryInterval(long j) {
            return (Mqtt5PublishBuilderBase.Complete) super.messageExpiryInterval(j);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5PublishBuilderBase.Complete noMessageExpiry() {
            return (Mqtt5PublishBuilderBase.Complete) super.noMessageExpiry();
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5PublishBuilderBase.Complete payload(ByteBuffer byteBuffer) {
            return (Mqtt5PublishBuilderBase.Complete) super.payload(byteBuffer);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5PublishBuilderBase.Complete payload(byte[] bArr) {
            return (Mqtt5PublishBuilderBase.Complete) super.payload(bArr);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5PublishBuilderBase.Complete payloadFormatIndicator(Mqtt5PayloadFormatIndicator mqtt5PayloadFormatIndicator) {
            return (Mqtt5PublishBuilderBase.Complete) super.payloadFormatIndicator(mqtt5PayloadFormatIndicator);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5PublishBuilderBase.Complete qos(MqttQos mqttQos) {
            return (Mqtt5PublishBuilderBase.Complete) super.qos(mqttQos);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ MqttTopicBuilder.Nested responseTopic() {
            return super.responseTopic();
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5PublishBuilderBase.Complete responseTopic(MqttTopic mqttTopic) {
            return (Mqtt5PublishBuilderBase.Complete) super.responseTopic(mqttTopic);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5PublishBuilderBase.Complete responseTopic(String str) {
            return (Mqtt5PublishBuilderBase.Complete) super.responseTopic(str);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5PublishBuilderBase.Complete retain(boolean z) {
            return (Mqtt5PublishBuilderBase.Complete) super.retain(z);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase
        public /* bridge */ /* synthetic */ MqttTopicBuilder.Nested topic() {
            return super.topic();
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5PublishBuilderBase.Complete topic(MqttTopic mqttTopic) {
            return (Mqtt5PublishBuilderBase.Complete) super.topic(mqttTopic);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5PublishBuilderBase.Complete topic(String str) {
            return (Mqtt5PublishBuilderBase.Complete) super.topic(str);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5UserPropertiesBuilder.Nested userProperties() {
            return super.userProperties();
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5PublishBuilderBase.Complete userProperties(Mqtt5UserProperties mqtt5UserProperties) {
            return (Mqtt5PublishBuilderBase.Complete) super.userProperties(mqtt5UserProperties);
        }

        public Nested(Function<? super MqttPublish, P> function) {
            this.parentConsumer = function;
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilder.Nested.Complete
        public P applyPublish() {
            return this.parentConsumer.apply(build());
        }
    }

    public static class Send<P> extends Base<Send<P>> implements Mqtt5PublishBuilder.Send.Complete<P> {
        private final Function<? super MqttPublish, P> parentConsumer;

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.hivemq.client.internal.mqtt.message.publish.MqttPublishBuilder
        public Send<P> self() {
            return this;
        }

        @Override // com.hivemq.client.internal.mqtt.message.publish.MqttPublishBuilder.Base, com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilder.Complete, com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilder
        public /* bridge */ /* synthetic */ WillDefault asWill() {
            return super.asWill();
        }

        @Override // com.hivemq.client.internal.mqtt.message.publish.MqttPublishBuilder.Base, com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilder.Complete
        public /* bridge */ /* synthetic */ MqttPublish build() {
            return super.build();
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5PublishBuilderBase.Complete contentType(MqttUtf8String mqttUtf8String) {
            return (Mqtt5PublishBuilderBase.Complete) super.contentType(mqttUtf8String);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5PublishBuilderBase.Complete contentType(String str) {
            return (Mqtt5PublishBuilderBase.Complete) super.contentType(str);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5PublishBuilderBase.Complete correlationData(ByteBuffer byteBuffer) {
            return (Mqtt5PublishBuilderBase.Complete) super.correlationData(byteBuffer);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5PublishBuilderBase.Complete correlationData(byte[] bArr) {
            return (Mqtt5PublishBuilderBase.Complete) super.correlationData(bArr);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5PublishBuilderBase.Complete messageExpiryInterval(long j) {
            return (Mqtt5PublishBuilderBase.Complete) super.messageExpiryInterval(j);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5PublishBuilderBase.Complete noMessageExpiry() {
            return (Mqtt5PublishBuilderBase.Complete) super.noMessageExpiry();
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5PublishBuilderBase.Complete payload(ByteBuffer byteBuffer) {
            return (Mqtt5PublishBuilderBase.Complete) super.payload(byteBuffer);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5PublishBuilderBase.Complete payload(byte[] bArr) {
            return (Mqtt5PublishBuilderBase.Complete) super.payload(bArr);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5PublishBuilderBase.Complete payloadFormatIndicator(Mqtt5PayloadFormatIndicator mqtt5PayloadFormatIndicator) {
            return (Mqtt5PublishBuilderBase.Complete) super.payloadFormatIndicator(mqtt5PayloadFormatIndicator);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5PublishBuilderBase.Complete qos(MqttQos mqttQos) {
            return (Mqtt5PublishBuilderBase.Complete) super.qos(mqttQos);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ MqttTopicBuilder.Nested responseTopic() {
            return super.responseTopic();
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5PublishBuilderBase.Complete responseTopic(MqttTopic mqttTopic) {
            return (Mqtt5PublishBuilderBase.Complete) super.responseTopic(mqttTopic);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5PublishBuilderBase.Complete responseTopic(String str) {
            return (Mqtt5PublishBuilderBase.Complete) super.responseTopic(str);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5PublishBuilderBase.Complete retain(boolean z) {
            return (Mqtt5PublishBuilderBase.Complete) super.retain(z);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase
        public /* bridge */ /* synthetic */ MqttTopicBuilder.Nested topic() {
            return super.topic();
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5PublishBuilderBase.Complete topic(MqttTopic mqttTopic) {
            return (Mqtt5PublishBuilderBase.Complete) super.topic(mqttTopic);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5PublishBuilderBase.Complete topic(String str) {
            return (Mqtt5PublishBuilderBase.Complete) super.topic(str);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5UserPropertiesBuilder.Nested userProperties() {
            return super.userProperties();
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5PublishBuilderBase.Complete userProperties(Mqtt5UserProperties mqtt5UserProperties) {
            return (Mqtt5PublishBuilderBase.Complete) super.userProperties(mqtt5UserProperties);
        }

        public Send(Function<? super MqttPublish, P> function) {
            this.parentConsumer = function;
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilder.Send.Complete
        public P send() {
            return this.parentConsumer.apply(build());
        }
    }

    private static abstract class WillBase<B extends WillBase<B>> extends MqttPublishBuilder<B> {
        private long delayInterval;

        WillBase() {
            this.delayInterval = 0L;
        }

        WillBase(MqttPublish mqttPublish) {
            super(mqttPublish);
            this.delayInterval = 0L;
            if (mqttPublish instanceof MqttWillPublish) {
                this.delayInterval = ((MqttWillPublish) mqttPublish).getDelayInterval();
            } else {
                payload(this.payload);
            }
        }

        WillBase(MqttPublishBuilder<?> mqttPublishBuilder) {
            super(mqttPublishBuilder);
            this.delayInterval = 0L;
            if (mqttPublishBuilder instanceof WillBase) {
                this.delayInterval = ((WillBase) mqttPublishBuilder).delayInterval;
            } else {
                payload(this.payload);
            }
        }

        public B payload(byte[] bArr) {
            this.payload = MqttChecks.binaryDataOrNull(bArr, "Payload");
            return self();
        }

        public B payload(ByteBuffer byteBuffer) {
            this.payload = MqttChecks.binaryDataOrNull(byteBuffer, "Payload");
            return self();
        }

        public B delayInterval(long j) {
            this.delayInterval = Checks.unsignedInt(j, "Will delay interval");
            return self();
        }

        public MqttWillPublish build() {
            Checks.notNull(this.topic, "Topic");
            return new MqttWillPublish(this.topic, this.payload, this.qos, this.retain, this.messageExpiryInterval, this.payloadFormatIndicator, this.contentType, this.responseTopic, this.correlationData, this.userProperties, this.delayInterval);
        }
    }

    public static class WillDefault extends WillBase<WillDefault> implements Mqtt5WillPublishBuilder.Complete {
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.hivemq.client.internal.mqtt.message.publish.MqttPublishBuilder
        public WillDefault self() {
            return this;
        }

        @Override // com.hivemq.client.internal.mqtt.message.publish.MqttPublishBuilder.WillBase, com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5WillPublishBuilder.Complete
        public /* bridge */ /* synthetic */ MqttWillPublish build() {
            return super.build();
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5WillPublishBuilder.Complete
        public /* bridge */ /* synthetic */ Mqtt5WillPublish build() {
            return super.build();
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5PublishBuilderBase.Complete contentType(MqttUtf8String mqttUtf8String) {
            return (Mqtt5PublishBuilderBase.Complete) super.contentType(mqttUtf8String);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5PublishBuilderBase.Complete contentType(String str) {
            return (Mqtt5PublishBuilderBase.Complete) super.contentType(str);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5PublishBuilderBase.Complete correlationData(ByteBuffer byteBuffer) {
            return (Mqtt5PublishBuilderBase.Complete) super.correlationData(byteBuffer);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5PublishBuilderBase.Complete correlationData(byte[] bArr) {
            return (Mqtt5PublishBuilderBase.Complete) super.correlationData(bArr);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase.WillBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5PublishBuilderBase.WillBase.Complete delayInterval(long j) {
            return (Mqtt5PublishBuilderBase.WillBase.Complete) super.delayInterval(j);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5PublishBuilderBase.Complete messageExpiryInterval(long j) {
            return (Mqtt5PublishBuilderBase.Complete) super.messageExpiryInterval(j);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5PublishBuilderBase.Complete noMessageExpiry() {
            return (Mqtt5PublishBuilderBase.Complete) super.noMessageExpiry();
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5PublishBuilderBase.Complete payload(ByteBuffer byteBuffer) {
            return (Mqtt5PublishBuilderBase.Complete) super.payload(byteBuffer);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5PublishBuilderBase.Complete payload(byte[] bArr) {
            return (Mqtt5PublishBuilderBase.Complete) super.payload(bArr);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5PublishBuilderBase.Complete payloadFormatIndicator(Mqtt5PayloadFormatIndicator mqtt5PayloadFormatIndicator) {
            return (Mqtt5PublishBuilderBase.Complete) super.payloadFormatIndicator(mqtt5PayloadFormatIndicator);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5PublishBuilderBase.Complete qos(MqttQos mqttQos) {
            return (Mqtt5PublishBuilderBase.Complete) super.qos(mqttQos);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ MqttTopicBuilder.Nested responseTopic() {
            return super.responseTopic();
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5PublishBuilderBase.Complete responseTopic(MqttTopic mqttTopic) {
            return (Mqtt5PublishBuilderBase.Complete) super.responseTopic(mqttTopic);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5PublishBuilderBase.Complete responseTopic(String str) {
            return (Mqtt5PublishBuilderBase.Complete) super.responseTopic(str);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5PublishBuilderBase.Complete retain(boolean z) {
            return (Mqtt5PublishBuilderBase.Complete) super.retain(z);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase
        public /* bridge */ /* synthetic */ MqttTopicBuilder.Nested topic() {
            return super.topic();
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5PublishBuilderBase.Complete topic(MqttTopic mqttTopic) {
            return (Mqtt5PublishBuilderBase.Complete) super.topic(mqttTopic);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5PublishBuilderBase.Complete topic(String str) {
            return (Mqtt5PublishBuilderBase.Complete) super.topic(str);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5UserPropertiesBuilder.Nested userProperties() {
            return super.userProperties();
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5PublishBuilderBase.Complete userProperties(Mqtt5UserProperties mqtt5UserProperties) {
            return (Mqtt5PublishBuilderBase.Complete) super.userProperties(mqtt5UserProperties);
        }

        public WillDefault() {
        }

        WillDefault(MqttPublish mqttPublish) {
            super(mqttPublish);
        }

        WillDefault(MqttPublishBuilder<?> mqttPublishBuilder) {
            super(mqttPublishBuilder);
        }
    }

    public static class WillNested<P> extends WillBase<WillNested<P>> implements Mqtt5WillPublishBuilder.Nested.Complete<P> {
        private final Function<? super MqttWillPublish, P> parentConsumer;

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.hivemq.client.internal.mqtt.message.publish.MqttPublishBuilder
        public WillNested<P> self() {
            return this;
        }

        @Override // com.hivemq.client.internal.mqtt.message.publish.MqttPublishBuilder.WillBase, com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5WillPublishBuilder.Complete
        public /* bridge */ /* synthetic */ MqttWillPublish build() {
            return super.build();
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5PublishBuilderBase.Complete contentType(MqttUtf8String mqttUtf8String) {
            return (Mqtt5PublishBuilderBase.Complete) super.contentType(mqttUtf8String);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5PublishBuilderBase.Complete contentType(String str) {
            return (Mqtt5PublishBuilderBase.Complete) super.contentType(str);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5PublishBuilderBase.Complete correlationData(ByteBuffer byteBuffer) {
            return (Mqtt5PublishBuilderBase.Complete) super.correlationData(byteBuffer);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5PublishBuilderBase.Complete correlationData(byte[] bArr) {
            return (Mqtt5PublishBuilderBase.Complete) super.correlationData(bArr);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase.WillBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5PublishBuilderBase.WillBase.Complete delayInterval(long j) {
            return (Mqtt5PublishBuilderBase.WillBase.Complete) super.delayInterval(j);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5PublishBuilderBase.Complete messageExpiryInterval(long j) {
            return (Mqtt5PublishBuilderBase.Complete) super.messageExpiryInterval(j);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5PublishBuilderBase.Complete noMessageExpiry() {
            return (Mqtt5PublishBuilderBase.Complete) super.noMessageExpiry();
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5PublishBuilderBase.Complete payload(ByteBuffer byteBuffer) {
            return (Mqtt5PublishBuilderBase.Complete) super.payload(byteBuffer);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5PublishBuilderBase.Complete payload(byte[] bArr) {
            return (Mqtt5PublishBuilderBase.Complete) super.payload(bArr);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5PublishBuilderBase.Complete payloadFormatIndicator(Mqtt5PayloadFormatIndicator mqtt5PayloadFormatIndicator) {
            return (Mqtt5PublishBuilderBase.Complete) super.payloadFormatIndicator(mqtt5PayloadFormatIndicator);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5PublishBuilderBase.Complete qos(MqttQos mqttQos) {
            return (Mqtt5PublishBuilderBase.Complete) super.qos(mqttQos);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ MqttTopicBuilder.Nested responseTopic() {
            return super.responseTopic();
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5PublishBuilderBase.Complete responseTopic(MqttTopic mqttTopic) {
            return (Mqtt5PublishBuilderBase.Complete) super.responseTopic(mqttTopic);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5PublishBuilderBase.Complete responseTopic(String str) {
            return (Mqtt5PublishBuilderBase.Complete) super.responseTopic(str);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5PublishBuilderBase.Complete retain(boolean z) {
            return (Mqtt5PublishBuilderBase.Complete) super.retain(z);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase
        public /* bridge */ /* synthetic */ MqttTopicBuilder.Nested topic() {
            return super.topic();
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5PublishBuilderBase.Complete topic(MqttTopic mqttTopic) {
            return (Mqtt5PublishBuilderBase.Complete) super.topic(mqttTopic);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5PublishBuilderBase.Complete topic(String str) {
            return (Mqtt5PublishBuilderBase.Complete) super.topic(str);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5UserPropertiesBuilder.Nested userProperties() {
            return super.userProperties();
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5PublishBuilderBase.Complete userProperties(Mqtt5UserProperties mqtt5UserProperties) {
            return (Mqtt5PublishBuilderBase.Complete) super.userProperties(mqtt5UserProperties);
        }

        public WillNested(Function<? super MqttWillPublish, P> function) {
            this.parentConsumer = function;
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5WillPublishBuilder.Nested.Complete
        public P applyWillPublish() {
            return this.parentConsumer.apply(build());
        }
    }
}
