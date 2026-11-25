package com.hivemq.client.internal.mqtt.datatypes;

import com.hivemq.client.internal.mqtt.datatypes.MqttTopicImplBuilder;
import com.hivemq.client.internal.util.Checks;
import com.hivemq.client.mqtt.datatypes.MqttTopic;
import com.hivemq.client.mqtt.datatypes.MqttTopicBuilder;
import com.hivemq.client.mqtt.datatypes.MqttTopicBuilderBase;
import java.util.function.Function;

/* JADX INFO: loaded from: classes10.dex */
public abstract class MqttTopicImplBuilder<B extends MqttTopicImplBuilder<B>> {
    StringBuilder stringBuilder;

    abstract B self();

    MqttTopicImplBuilder() {
    }

    MqttTopicImplBuilder(MqttTopicImpl mqttTopicImpl) {
        this.stringBuilder = new StringBuilder(mqttTopicImpl.toString());
    }

    public B addLevel(String str) {
        Checks.notEmpty(str, "Topic level");
        StringBuilder sb = this.stringBuilder;
        if (sb == null) {
            this.stringBuilder = new StringBuilder(str);
        } else {
            sb.append('/').append(str);
        }
        return (B) self();
    }

    public MqttTopicImpl build() {
        Checks.state(this.stringBuilder != null, "At least one topic level must be added.");
        String string = this.stringBuilder.toString();
        Checks.state(true ^ string.isEmpty(), "Topic must be at least one character long.");
        return MqttTopicImpl.of(string);
    }

    public static class Default extends MqttTopicImplBuilder<Default> implements MqttTopicBuilder.Complete {
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.hivemq.client.internal.mqtt.datatypes.MqttTopicImplBuilder
        public Default self() {
            return this;
        }

        @Override // com.hivemq.client.mqtt.datatypes.MqttTopicBuilderBase
        public /* bridge */ /* synthetic */ MqttTopicBuilderBase addLevel(String str) {
            return (MqttTopicBuilderBase) super.addLevel(str);
        }

        @Override // com.hivemq.client.mqtt.datatypes.MqttTopicBuilder.Complete
        public /* bridge */ /* synthetic */ MqttTopic build() {
            return super.build();
        }

        public Default() {
        }

        Default(MqttTopicImpl mqttTopicImpl) {
            super(mqttTopicImpl);
        }

        @Override // com.hivemq.client.mqtt.datatypes.MqttTopicBuilder
        public MqttTopicFilterImplBuilder.Default filter() {
            if (this.stringBuilder == null) {
                return new MqttTopicFilterImplBuilder.Default();
            }
            return new MqttTopicFilterImplBuilder.Default(this.stringBuilder.toString());
        }

        @Override // com.hivemq.client.mqtt.datatypes.MqttTopicBuilder
        public MqttTopicFilterImplBuilder.SharedDefault share(String str) {
            if (this.stringBuilder == null) {
                return new MqttTopicFilterImplBuilder.SharedDefault(str);
            }
            return new MqttTopicFilterImplBuilder.SharedDefault(str, this.stringBuilder.toString());
        }
    }

    public static class Nested<P> extends MqttTopicImplBuilder<Nested<P>> implements MqttTopicBuilder.Nested.Complete<P> {
        private final Function<? super MqttTopicImpl, P> parentConsumer;

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.hivemq.client.internal.mqtt.datatypes.MqttTopicImplBuilder
        public Nested<P> self() {
            return this;
        }

        @Override // com.hivemq.client.mqtt.datatypes.MqttTopicBuilderBase
        public /* bridge */ /* synthetic */ MqttTopicBuilderBase addLevel(String str) {
            return (MqttTopicBuilderBase) super.addLevel(str);
        }

        public Nested(Function<? super MqttTopicImpl, P> function) {
            this.parentConsumer = function;
        }

        @Override // com.hivemq.client.mqtt.datatypes.MqttTopicBuilder.Nested.Complete
        public P applyTopic() {
            return this.parentConsumer.apply(build());
        }
    }
}
