package com.hivemq.client.internal.mqtt.message.publish.mqtt3;

import com.hivemq.client.internal.mqtt.datatypes.MqttTopicImpl;
import com.hivemq.client.internal.mqtt.datatypes.MqttTopicImplBuilder;
import com.hivemq.client.internal.mqtt.message.publish.MqttPublish;
import com.hivemq.client.internal.mqtt.message.publish.MqttWillPublish;
import com.hivemq.client.internal.mqtt.message.publish.mqtt3.Mqtt3PublishViewBuilder;
import com.hivemq.client.internal.mqtt.util.MqttChecks;
import com.hivemq.client.internal.util.ByteBufferUtil;
import com.hivemq.client.internal.util.Checks;
import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.datatypes.MqttTopic;
import com.hivemq.client.mqtt.datatypes.MqttTopicBuilder;
import com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3Publish;
import com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3PublishBuilder;
import com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3PublishBuilderBase;
import com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3WillPublishBuilder;
import java.nio.ByteBuffer;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: loaded from: classes10.dex */
public abstract class Mqtt3PublishViewBuilder<B extends Mqtt3PublishViewBuilder<B>> {
    ByteBuffer payload;
    MqttQos qos;
    boolean retain;
    MqttTopicImpl topic;

    protected abstract B self();

    Mqtt3PublishViewBuilder() {
        this.qos = Mqtt3PublishView.DEFAULT_QOS;
    }

    Mqtt3PublishViewBuilder(Mqtt3PublishView mqtt3PublishView) {
        this.qos = Mqtt3PublishView.DEFAULT_QOS;
        MqttPublish delegate = mqtt3PublishView.getDelegate();
        this.topic = delegate.getTopic();
        this.payload = delegate.getRawPayload();
        this.qos = delegate.getQos();
        this.retain = delegate.isRetain();
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
        return new MqttTopicImplBuilder.Nested<>(new Function() { // from class: com.hivemq.client.internal.mqtt.message.publish.mqtt3.Mqtt3PublishViewBuilder$$ExternalSyntheticLambda0
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

    private static abstract class Base<B extends Base<B>> extends Mqtt3PublishViewBuilder<B> {
        Base() {
        }

        Base(Mqtt3PublishView mqtt3PublishView) {
            super(mqtt3PublishView);
        }

        public B payload(byte[] bArr) {
            this.payload = ByteBufferUtil.wrap(bArr);
            return self();
        }

        public B payload(ByteBuffer byteBuffer) {
            this.payload = ByteBufferUtil.slice(byteBuffer);
            return self();
        }

        public Mqtt3PublishView build() {
            Checks.notNull(this.topic, "Topic");
            return Mqtt3PublishView.of(this.topic, this.payload, this.qos, this.retain);
        }
    }

    public static class Default extends Base<Default> implements Mqtt3PublishBuilder.Complete {
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.hivemq.client.internal.mqtt.message.publish.mqtt3.Mqtt3PublishViewBuilder
        public Default self() {
            return this;
        }

        @Override // com.hivemq.client.internal.mqtt.message.publish.mqtt3.Mqtt3PublishViewBuilder.Base, com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3PublishBuilder.Complete
        public /* bridge */ /* synthetic */ Mqtt3PublishView build() {
            return super.build();
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3PublishBuilder.Complete
        public /* bridge */ /* synthetic */ Mqtt3Publish build() {
            return super.build();
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt3PublishBuilderBase.Complete payload(ByteBuffer byteBuffer) {
            return (Mqtt3PublishBuilderBase.Complete) super.payload(byteBuffer);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt3PublishBuilderBase.Complete payload(byte[] bArr) {
            return (Mqtt3PublishBuilderBase.Complete) super.payload(bArr);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt3PublishBuilderBase.Complete qos(MqttQos mqttQos) {
            return (Mqtt3PublishBuilderBase.Complete) super.qos(mqttQos);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt3PublishBuilderBase.Complete retain(boolean z) {
            return (Mqtt3PublishBuilderBase.Complete) super.retain(z);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3PublishBuilderBase
        public /* bridge */ /* synthetic */ MqttTopicBuilder.Nested topic() {
            return super.topic();
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3PublishBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3PublishBuilderBase.Complete topic(MqttTopic mqttTopic) {
            return (Mqtt3PublishBuilderBase.Complete) super.topic(mqttTopic);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3PublishBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3PublishBuilderBase.Complete topic(String str) {
            return (Mqtt3PublishBuilderBase.Complete) super.topic(str);
        }

        public Default() {
        }

        Default(Mqtt3PublishView mqtt3PublishView) {
            super(mqtt3PublishView);
        }
    }

    public static class Nested<P> extends Base<Nested<P>> implements Mqtt3PublishBuilder.Nested.Complete<P> {
        private final Function<? super Mqtt3PublishView, P> parentConsumer;

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.hivemq.client.internal.mqtt.message.publish.mqtt3.Mqtt3PublishViewBuilder
        public Nested<P> self() {
            return this;
        }

        @Override // com.hivemq.client.internal.mqtt.message.publish.mqtt3.Mqtt3PublishViewBuilder.Base, com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3PublishBuilder.Complete
        public /* bridge */ /* synthetic */ Mqtt3PublishView build() {
            return super.build();
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt3PublishBuilderBase.Complete payload(ByteBuffer byteBuffer) {
            return (Mqtt3PublishBuilderBase.Complete) super.payload(byteBuffer);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt3PublishBuilderBase.Complete payload(byte[] bArr) {
            return (Mqtt3PublishBuilderBase.Complete) super.payload(bArr);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt3PublishBuilderBase.Complete qos(MqttQos mqttQos) {
            return (Mqtt3PublishBuilderBase.Complete) super.qos(mqttQos);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt3PublishBuilderBase.Complete retain(boolean z) {
            return (Mqtt3PublishBuilderBase.Complete) super.retain(z);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3PublishBuilderBase
        public /* bridge */ /* synthetic */ MqttTopicBuilder.Nested topic() {
            return super.topic();
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3PublishBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3PublishBuilderBase.Complete topic(MqttTopic mqttTopic) {
            return (Mqtt3PublishBuilderBase.Complete) super.topic(mqttTopic);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3PublishBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3PublishBuilderBase.Complete topic(String str) {
            return (Mqtt3PublishBuilderBase.Complete) super.topic(str);
        }

        public Nested(Function<? super Mqtt3PublishView, P> function) {
            this.parentConsumer = function;
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3PublishBuilder.Nested.Complete
        public P applyPublish() {
            return this.parentConsumer.apply(build());
        }
    }

    public static class Send<P> extends Base<Send<P>> implements Mqtt3PublishBuilder.Send.Complete<P> {
        private final Function<? super Mqtt3PublishView, P> parentConsumer;

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.hivemq.client.internal.mqtt.message.publish.mqtt3.Mqtt3PublishViewBuilder
        public Send<P> self() {
            return this;
        }

        @Override // com.hivemq.client.internal.mqtt.message.publish.mqtt3.Mqtt3PublishViewBuilder.Base, com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3PublishBuilder.Complete
        public /* bridge */ /* synthetic */ Mqtt3PublishView build() {
            return super.build();
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt3PublishBuilderBase.Complete payload(ByteBuffer byteBuffer) {
            return (Mqtt3PublishBuilderBase.Complete) super.payload(byteBuffer);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt3PublishBuilderBase.Complete payload(byte[] bArr) {
            return (Mqtt3PublishBuilderBase.Complete) super.payload(bArr);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt3PublishBuilderBase.Complete qos(MqttQos mqttQos) {
            return (Mqtt3PublishBuilderBase.Complete) super.qos(mqttQos);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt3PublishBuilderBase.Complete retain(boolean z) {
            return (Mqtt3PublishBuilderBase.Complete) super.retain(z);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3PublishBuilderBase
        public /* bridge */ /* synthetic */ MqttTopicBuilder.Nested topic() {
            return super.topic();
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3PublishBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3PublishBuilderBase.Complete topic(MqttTopic mqttTopic) {
            return (Mqtt3PublishBuilderBase.Complete) super.topic(mqttTopic);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3PublishBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3PublishBuilderBase.Complete topic(String str) {
            return (Mqtt3PublishBuilderBase.Complete) super.topic(str);
        }

        public Send(Function<? super Mqtt3PublishView, P> function) {
            this.parentConsumer = function;
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3PublishBuilder.Send.Complete
        public P send() {
            return this.parentConsumer.apply(build());
        }
    }

    public static class SendVoid extends Base<SendVoid> implements Mqtt3PublishBuilder.SendVoid.Complete {
        private final Consumer<? super Mqtt3PublishView> parentConsumer;

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.hivemq.client.internal.mqtt.message.publish.mqtt3.Mqtt3PublishViewBuilder
        public SendVoid self() {
            return this;
        }

        @Override // com.hivemq.client.internal.mqtt.message.publish.mqtt3.Mqtt3PublishViewBuilder.Base, com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3PublishBuilder.Complete
        public /* bridge */ /* synthetic */ Mqtt3PublishView build() {
            return super.build();
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt3PublishBuilderBase.Complete payload(ByteBuffer byteBuffer) {
            return (Mqtt3PublishBuilderBase.Complete) super.payload(byteBuffer);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt3PublishBuilderBase.Complete payload(byte[] bArr) {
            return (Mqtt3PublishBuilderBase.Complete) super.payload(bArr);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt3PublishBuilderBase.Complete qos(MqttQos mqttQos) {
            return (Mqtt3PublishBuilderBase.Complete) super.qos(mqttQos);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt3PublishBuilderBase.Complete retain(boolean z) {
            return (Mqtt3PublishBuilderBase.Complete) super.retain(z);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3PublishBuilderBase
        public /* bridge */ /* synthetic */ MqttTopicBuilder.Nested topic() {
            return super.topic();
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3PublishBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3PublishBuilderBase.Complete topic(MqttTopic mqttTopic) {
            return (Mqtt3PublishBuilderBase.Complete) super.topic(mqttTopic);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3PublishBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3PublishBuilderBase.Complete topic(String str) {
            return (Mqtt3PublishBuilderBase.Complete) super.topic(str);
        }

        public SendVoid(Consumer<? super Mqtt3PublishView> consumer) {
            this.parentConsumer = consumer;
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3PublishBuilder.SendVoid.Complete
        public void send() {
            this.parentConsumer.accept(build());
        }
    }

    private static abstract class WillBase<B extends WillBase<B>> extends Mqtt3PublishViewBuilder<B> {
        WillBase() {
        }

        WillBase(Mqtt3PublishView mqtt3PublishView) {
            super(mqtt3PublishView);
            if (mqtt3PublishView.getDelegate() instanceof MqttWillPublish) {
                return;
            }
            payload(this.payload);
        }

        public B payload(byte[] bArr) {
            this.payload = MqttChecks.binaryDataOrNull(bArr, "Payload");
            return self();
        }

        public B payload(ByteBuffer byteBuffer) {
            this.payload = MqttChecks.binaryDataOrNull(byteBuffer, "Payload");
            return self();
        }

        public Mqtt3PublishView build() {
            Checks.notNull(this.topic, "Topic");
            return Mqtt3PublishView.willOf(this.topic, this.payload, this.qos, this.retain);
        }
    }

    public static class WillDefault extends WillBase<WillDefault> implements Mqtt3WillPublishBuilder.Complete {
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.hivemq.client.internal.mqtt.message.publish.mqtt3.Mqtt3PublishViewBuilder
        public WillDefault self() {
            return this;
        }

        @Override // com.hivemq.client.internal.mqtt.message.publish.mqtt3.Mqtt3PublishViewBuilder.WillBase, com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3WillPublishBuilder.Complete
        public /* bridge */ /* synthetic */ Mqtt3PublishView build() {
            return super.build();
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3WillPublishBuilder.Complete
        public /* bridge */ /* synthetic */ Mqtt3Publish build() {
            return super.build();
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt3PublishBuilderBase.Complete payload(ByteBuffer byteBuffer) {
            return (Mqtt3PublishBuilderBase.Complete) super.payload(byteBuffer);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt3PublishBuilderBase.Complete payload(byte[] bArr) {
            return (Mqtt3PublishBuilderBase.Complete) super.payload(bArr);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt3PublishBuilderBase.Complete qos(MqttQos mqttQos) {
            return (Mqtt3PublishBuilderBase.Complete) super.qos(mqttQos);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt3PublishBuilderBase.Complete retain(boolean z) {
            return (Mqtt3PublishBuilderBase.Complete) super.retain(z);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3PublishBuilderBase
        public /* bridge */ /* synthetic */ MqttTopicBuilder.Nested topic() {
            return super.topic();
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3PublishBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3PublishBuilderBase.Complete topic(MqttTopic mqttTopic) {
            return (Mqtt3PublishBuilderBase.Complete) super.topic(mqttTopic);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3PublishBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3PublishBuilderBase.Complete topic(String str) {
            return (Mqtt3PublishBuilderBase.Complete) super.topic(str);
        }

        public WillDefault() {
        }

        WillDefault(Mqtt3PublishView mqtt3PublishView) {
            super(mqtt3PublishView);
        }
    }

    public static class WillNested<P> extends WillBase<WillNested<P>> implements Mqtt3WillPublishBuilder.Nested.Complete<P> {
        private final Function<? super Mqtt3PublishView, P> parentConsumer;

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.hivemq.client.internal.mqtt.message.publish.mqtt3.Mqtt3PublishViewBuilder
        public WillNested<P> self() {
            return this;
        }

        @Override // com.hivemq.client.internal.mqtt.message.publish.mqtt3.Mqtt3PublishViewBuilder.WillBase, com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3WillPublishBuilder.Complete
        public /* bridge */ /* synthetic */ Mqtt3PublishView build() {
            return super.build();
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt3PublishBuilderBase.Complete payload(ByteBuffer byteBuffer) {
            return (Mqtt3PublishBuilderBase.Complete) super.payload(byteBuffer);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt3PublishBuilderBase.Complete payload(byte[] bArr) {
            return (Mqtt3PublishBuilderBase.Complete) super.payload(bArr);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt3PublishBuilderBase.Complete qos(MqttQos mqttQos) {
            return (Mqtt3PublishBuilderBase.Complete) super.qos(mqttQos);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3PublishBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt3PublishBuilderBase.Complete retain(boolean z) {
            return (Mqtt3PublishBuilderBase.Complete) super.retain(z);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3PublishBuilderBase
        public /* bridge */ /* synthetic */ MqttTopicBuilder.Nested topic() {
            return super.topic();
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3PublishBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3PublishBuilderBase.Complete topic(MqttTopic mqttTopic) {
            return (Mqtt3PublishBuilderBase.Complete) super.topic(mqttTopic);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3PublishBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3PublishBuilderBase.Complete topic(String str) {
            return (Mqtt3PublishBuilderBase.Complete) super.topic(str);
        }

        public WillNested(Function<? super Mqtt3PublishView, P> function) {
            this.parentConsumer = function;
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3WillPublishBuilder.Nested.Complete
        public P applyWillPublish() {
            return this.parentConsumer.apply(build());
        }
    }
}
