package com.hivemq.client.internal.mqtt.datatypes;

import com.hivemq.client.internal.mqtt.datatypes.MqttTopicFilterImplBuilder;
import com.hivemq.client.internal.util.Checks;
import com.hivemq.client.mqtt.datatypes.MqttSharedTopicFilter;
import com.hivemq.client.mqtt.datatypes.MqttSharedTopicFilterBuilder;
import com.hivemq.client.mqtt.datatypes.MqttTopicFilter;
import com.hivemq.client.mqtt.datatypes.MqttTopicFilterBuilder;
import com.hivemq.client.mqtt.datatypes.MqttTopicFilterBuilderBase;
import java.util.function.Function;

/* JADX INFO: loaded from: classes10.dex */
public abstract class MqttTopicFilterImplBuilder<B extends MqttTopicFilterImplBuilder<B>> {
    StringBuilder stringBuilder;

    abstract B self();

    MqttTopicFilterImplBuilder() {
    }

    MqttTopicFilterImplBuilder(String str) {
        this.stringBuilder = new StringBuilder(str);
    }

    MqttTopicFilterImplBuilder(MqttTopicFilterImpl mqttTopicFilterImpl) {
        this.stringBuilder = new StringBuilder(mqttTopicFilterImpl.getTopicFilterString());
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

    public B singleLevelWildcard() {
        StringBuilder sb = this.stringBuilder;
        if (sb == null) {
            this.stringBuilder = new StringBuilder();
        } else {
            sb.append('/');
        }
        this.stringBuilder.append('+');
        return (B) self();
    }

    public B multiLevelWildcard() {
        StringBuilder sb = this.stringBuilder;
        if (sb == null) {
            this.stringBuilder = new StringBuilder(1);
        } else {
            sb.append('/');
        }
        this.stringBuilder.append('#');
        return (B) self();
    }

    public static abstract class Base<B extends Base<B>> extends MqttTopicFilterImplBuilder<B> {
        Base() {
        }

        Base(String str) {
            super(str);
        }

        Base(MqttTopicFilterImpl mqttTopicFilterImpl) {
            super(mqttTopicFilterImpl);
        }

        public MqttTopicFilterImpl build() {
            Checks.state(this.stringBuilder != null, "At least one topic level must be added.");
            String string = this.stringBuilder.toString();
            Checks.state(true ^ string.isEmpty(), "Topic must be at least one character long.");
            return MqttTopicFilterImpl.of(string);
        }
    }

    public static class Default extends Base<Default> implements MqttTopicFilterBuilder.Complete {
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.hivemq.client.internal.mqtt.datatypes.MqttTopicFilterImplBuilder
        public Default self() {
            return this;
        }

        @Override // com.hivemq.client.mqtt.datatypes.MqttTopicFilterBuilderBase
        public /* bridge */ /* synthetic */ MqttTopicFilterBuilderBase addLevel(String str) {
            return (MqttTopicFilterBuilderBase) super.addLevel(str);
        }

        @Override // com.hivemq.client.mqtt.datatypes.MqttTopicFilterBuilder.End
        public /* bridge */ /* synthetic */ MqttTopicFilter build() {
            return super.build();
        }

        @Override // com.hivemq.client.mqtt.datatypes.MqttTopicFilterBuilderBase
        public /* bridge */ /* synthetic */ MqttTopicFilterBuilderBase.End multiLevelWildcard() {
            return (MqttTopicFilterBuilderBase.End) super.multiLevelWildcard();
        }

        @Override // com.hivemq.client.mqtt.datatypes.MqttTopicFilterBuilderBase
        public /* bridge */ /* synthetic */ MqttTopicFilterBuilderBase singleLevelWildcard() {
            return (MqttTopicFilterBuilderBase) super.singleLevelWildcard();
        }

        public Default() {
        }

        Default(String str) {
            super(str);
        }

        Default(MqttTopicFilterImpl mqttTopicFilterImpl) {
            super(mqttTopicFilterImpl);
        }

        @Override // com.hivemq.client.mqtt.datatypes.MqttTopicFilterBuilderBase, com.hivemq.client.mqtt.datatypes.MqttTopicFilterBuilderBase.Complete
        public SharedDefault share(String str) {
            if (this.stringBuilder == null) {
                return new SharedDefault(str);
            }
            return new SharedDefault(str, this.stringBuilder.toString());
        }
    }

    public static class Nested<P> extends Base<Nested<P>> implements MqttTopicFilterBuilder.Nested.Complete<P> {
        private final Function<? super MqttTopicFilterImpl, P> parentConsumer;

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.hivemq.client.internal.mqtt.datatypes.MqttTopicFilterImplBuilder
        public Nested<P> self() {
            return this;
        }

        @Override // com.hivemq.client.mqtt.datatypes.MqttTopicFilterBuilderBase
        public /* bridge */ /* synthetic */ MqttTopicFilterBuilderBase addLevel(String str) {
            return (MqttTopicFilterBuilderBase) super.addLevel(str);
        }

        @Override // com.hivemq.client.mqtt.datatypes.MqttTopicFilterBuilderBase
        public /* bridge */ /* synthetic */ MqttTopicFilterBuilderBase.End multiLevelWildcard() {
            return (MqttTopicFilterBuilderBase.End) super.multiLevelWildcard();
        }

        @Override // com.hivemq.client.mqtt.datatypes.MqttTopicFilterBuilderBase
        public /* bridge */ /* synthetic */ MqttTopicFilterBuilderBase singleLevelWildcard() {
            return (MqttTopicFilterBuilderBase) super.singleLevelWildcard();
        }

        public Nested(Function<? super MqttTopicFilterImpl, P> function) {
            this.parentConsumer = function;
        }

        @Override // com.hivemq.client.mqtt.datatypes.MqttTopicFilterBuilderBase, com.hivemq.client.mqtt.datatypes.MqttTopicFilterBuilderBase.Complete
        public SharedNested<P> share(String str) {
            if (this.stringBuilder == null) {
                return new SharedNested<>(str, this.parentConsumer);
            }
            return new SharedNested<>(str, this.stringBuilder.toString(), this.parentConsumer);
        }

        @Override // com.hivemq.client.mqtt.datatypes.MqttTopicFilterBuilder.Nested.End
        public P applyTopicFilter() {
            return this.parentConsumer.apply(build());
        }
    }

    public static abstract class SharedBase<B extends SharedBase<B>> extends MqttTopicFilterImplBuilder<B> {
        private String shareName;

        SharedBase(String str) {
            this.shareName = (String) Checks.notNull(str, "Share name");
        }

        SharedBase(String str, String str2) {
            super(str2);
            this.shareName = (String) Checks.notNull(str, "Share name");
        }

        SharedBase(MqttSharedTopicFilterImpl mqttSharedTopicFilterImpl) {
            super(mqttSharedTopicFilterImpl);
            this.shareName = mqttSharedTopicFilterImpl.getShareName();
        }

        public B share(String str) {
            this.shareName = (String) Checks.notNull(str, "Share name");
            return self();
        }

        public MqttSharedTopicFilterImpl build() {
            Checks.state(this.stringBuilder != null, "At least one topic level must be added.");
            String string = this.stringBuilder.toString();
            Checks.state(true ^ string.isEmpty(), "Topic must be at least one character long.");
            return MqttSharedTopicFilterImpl.of(this.shareName, string);
        }
    }

    public static class SharedDefault extends SharedBase<SharedDefault> implements MqttSharedTopicFilterBuilder.Complete {
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.hivemq.client.internal.mqtt.datatypes.MqttTopicFilterImplBuilder
        public SharedDefault self() {
            return this;
        }

        @Override // com.hivemq.client.mqtt.datatypes.MqttTopicFilterBuilderBase
        public /* bridge */ /* synthetic */ MqttTopicFilterBuilderBase addLevel(String str) {
            return (MqttTopicFilterBuilderBase) super.addLevel(str);
        }

        @Override // com.hivemq.client.mqtt.datatypes.MqttSharedTopicFilterBuilder.End
        public /* bridge */ /* synthetic */ MqttSharedTopicFilter build() {
            return super.build();
        }

        @Override // com.hivemq.client.mqtt.datatypes.MqttTopicFilterBuilderBase
        public /* bridge */ /* synthetic */ MqttTopicFilterBuilderBase.End multiLevelWildcard() {
            return (MqttTopicFilterBuilderBase.End) super.multiLevelWildcard();
        }

        @Override // com.hivemq.client.mqtt.datatypes.MqttTopicFilterBuilderBase, com.hivemq.client.mqtt.datatypes.MqttTopicFilterBuilderBase.Complete
        public /* bridge */ /* synthetic */ MqttTopicFilterBuilderBase.SharedBase share(String str) {
            return (MqttTopicFilterBuilderBase.SharedBase) super.share(str);
        }

        @Override // com.hivemq.client.mqtt.datatypes.MqttTopicFilterBuilderBase
        public /* bridge */ /* synthetic */ MqttTopicFilterBuilderBase singleLevelWildcard() {
            return (MqttTopicFilterBuilderBase) super.singleLevelWildcard();
        }

        public SharedDefault(String str) {
            super(str);
        }

        SharedDefault(String str, String str2) {
            super(str, str2);
        }

        SharedDefault(MqttSharedTopicFilterImpl mqttSharedTopicFilterImpl) {
            super(mqttSharedTopicFilterImpl);
        }
    }

    public static class SharedNested<P> extends SharedBase<SharedNested<P>> implements MqttSharedTopicFilterBuilder.Nested.Complete<P> {
        private final Function<? super MqttSharedTopicFilterImpl, P> parentConsumer;

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.hivemq.client.internal.mqtt.datatypes.MqttTopicFilterImplBuilder
        public SharedNested<P> self() {
            return this;
        }

        @Override // com.hivemq.client.mqtt.datatypes.MqttTopicFilterBuilderBase
        public /* bridge */ /* synthetic */ MqttTopicFilterBuilderBase addLevel(String str) {
            return (MqttTopicFilterBuilderBase) super.addLevel(str);
        }

        @Override // com.hivemq.client.mqtt.datatypes.MqttTopicFilterBuilderBase
        public /* bridge */ /* synthetic */ MqttTopicFilterBuilderBase.End multiLevelWildcard() {
            return (MqttTopicFilterBuilderBase.End) super.multiLevelWildcard();
        }

        @Override // com.hivemq.client.mqtt.datatypes.MqttTopicFilterBuilderBase, com.hivemq.client.mqtt.datatypes.MqttTopicFilterBuilderBase.Complete
        public /* bridge */ /* synthetic */ MqttTopicFilterBuilderBase.SharedBase share(String str) {
            return (MqttTopicFilterBuilderBase.SharedBase) super.share(str);
        }

        @Override // com.hivemq.client.mqtt.datatypes.MqttTopicFilterBuilderBase
        public /* bridge */ /* synthetic */ MqttTopicFilterBuilderBase singleLevelWildcard() {
            return (MqttTopicFilterBuilderBase) super.singleLevelWildcard();
        }

        SharedNested(String str, Function<? super MqttSharedTopicFilterImpl, P> function) {
            super(str);
            this.parentConsumer = function;
        }

        SharedNested(String str, String str2, Function<? super MqttSharedTopicFilterImpl, P> function) {
            super(str, str2);
            this.parentConsumer = function;
        }

        @Override // com.hivemq.client.mqtt.datatypes.MqttSharedTopicFilterBuilder.Nested.End
        public P applyTopicFilter() {
            return this.parentConsumer.apply(build());
        }
    }
}
