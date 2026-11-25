package com.hivemq.client.internal.mqtt.datatypes;

import com.hivemq.client.internal.mqtt.datatypes.MqttUserPropertiesImplBuilder;
import com.hivemq.client.internal.mqtt.util.MqttChecks;
import com.hivemq.client.internal.util.Checks;
import com.hivemq.client.internal.util.collections.ImmutableList;
import com.hivemq.client.mqtt.datatypes.MqttUtf8String;
import com.hivemq.client.mqtt.mqtt5.datatypes.Mqtt5UserProperties;
import com.hivemq.client.mqtt.mqtt5.datatypes.Mqtt5UserPropertiesBuilder;
import com.hivemq.client.mqtt.mqtt5.datatypes.Mqtt5UserPropertiesBuilderBase;
import com.hivemq.client.mqtt.mqtt5.datatypes.Mqtt5UserProperty;
import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Stream;

/* JADX INFO: loaded from: classes10.dex */
public abstract class MqttUserPropertiesImplBuilder<B extends MqttUserPropertiesImplBuilder<B>> {
    private final ImmutableList.Builder<MqttUserPropertyImpl> listBuilder;

    abstract B self();

    MqttUserPropertiesImplBuilder() {
        this.listBuilder = ImmutableList.builder();
    }

    MqttUserPropertiesImplBuilder(MqttUserPropertiesImpl mqttUserPropertiesImpl) {
        ImmutableList<MqttUserPropertyImpl> immutableListAsList = mqttUserPropertiesImpl.asList();
        ImmutableList.Builder<MqttUserPropertyImpl> builder = ImmutableList.builder(immutableListAsList.size() + 1);
        this.listBuilder = builder;
        builder.addAll(immutableListAsList);
    }

    public B add(String str, String str2) {
        this.listBuilder.add(MqttUserPropertyImpl.of(str, str2));
        return (B) self();
    }

    public B add(MqttUtf8String mqttUtf8String, MqttUtf8String mqttUtf8String2) {
        this.listBuilder.add(MqttChecks.userProperty(mqttUtf8String, mqttUtf8String2));
        return (B) self();
    }

    public B add(Mqtt5UserProperty mqtt5UserProperty) {
        this.listBuilder.add(MqttChecks.userProperty(mqtt5UserProperty));
        return (B) self();
    }

    public B addAll(Mqtt5UserProperty... mqtt5UserPropertyArr) {
        Checks.notNull(mqtt5UserPropertyArr, "User Properties");
        this.listBuilder.ensureFree(mqtt5UserPropertyArr.length);
        for (Mqtt5UserProperty mqtt5UserProperty : mqtt5UserPropertyArr) {
            add(mqtt5UserProperty);
        }
        return (B) self();
    }

    public B addAll(Collection<? extends Mqtt5UserProperty> collection) {
        Checks.notNull(collection, "User Properties");
        this.listBuilder.ensureFree(collection.size());
        collection.forEach(new MqttUserPropertiesImplBuilder$$ExternalSyntheticLambda0(this));
        return (B) self();
    }

    public B addAll(Stream<? extends Mqtt5UserProperty> stream) {
        Checks.notNull(stream, "User Properties");
        stream.forEach(new MqttUserPropertiesImplBuilder$$ExternalSyntheticLambda0(this));
        return (B) self();
    }

    public MqttUserPropertiesImpl build() {
        return MqttUserPropertiesImpl.build(this.listBuilder);
    }

    public static class Default extends MqttUserPropertiesImplBuilder<Default> implements Mqtt5UserPropertiesBuilder {
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.hivemq.client.internal.mqtt.datatypes.MqttUserPropertiesImplBuilder
        public Default self() {
            return this;
        }

        @Override // com.hivemq.client.mqtt.mqtt5.datatypes.Mqtt5UserPropertiesBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5UserPropertiesBuilderBase add(MqttUtf8String mqttUtf8String, MqttUtf8String mqttUtf8String2) {
            return (Mqtt5UserPropertiesBuilderBase) super.add(mqttUtf8String, mqttUtf8String2);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.datatypes.Mqtt5UserPropertiesBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5UserPropertiesBuilderBase add(Mqtt5UserProperty mqtt5UserProperty) {
            return (Mqtt5UserPropertiesBuilderBase) super.add(mqtt5UserProperty);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.datatypes.Mqtt5UserPropertiesBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5UserPropertiesBuilderBase add(String str, String str2) {
            return (Mqtt5UserPropertiesBuilderBase) super.add(str, str2);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.datatypes.Mqtt5UserPropertiesBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5UserPropertiesBuilderBase addAll(Collection collection) {
            return (Mqtt5UserPropertiesBuilderBase) super.addAll((Collection<? extends Mqtt5UserProperty>) collection);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.datatypes.Mqtt5UserPropertiesBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5UserPropertiesBuilderBase addAll(Stream stream) {
            return (Mqtt5UserPropertiesBuilderBase) super.addAll((Stream<? extends Mqtt5UserProperty>) stream);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.datatypes.Mqtt5UserPropertiesBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5UserPropertiesBuilderBase addAll(Mqtt5UserProperty[] mqtt5UserPropertyArr) {
            return (Mqtt5UserPropertiesBuilderBase) super.addAll(mqtt5UserPropertyArr);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.datatypes.Mqtt5UserPropertiesBuilder
        public /* bridge */ /* synthetic */ Mqtt5UserProperties build() {
            return super.build();
        }

        public Default() {
        }

        Default(MqttUserPropertiesImpl mqttUserPropertiesImpl) {
            super(mqttUserPropertiesImpl);
        }
    }

    public static class Nested<P> extends MqttUserPropertiesImplBuilder<Nested<P>> implements Mqtt5UserPropertiesBuilder.Nested<P> {
        private final Function<? super MqttUserPropertiesImpl, P> parentConsumer;

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.hivemq.client.internal.mqtt.datatypes.MqttUserPropertiesImplBuilder
        public Nested<P> self() {
            return this;
        }

        @Override // com.hivemq.client.mqtt.mqtt5.datatypes.Mqtt5UserPropertiesBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5UserPropertiesBuilderBase add(MqttUtf8String mqttUtf8String, MqttUtf8String mqttUtf8String2) {
            return (Mqtt5UserPropertiesBuilderBase) super.add(mqttUtf8String, mqttUtf8String2);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.datatypes.Mqtt5UserPropertiesBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5UserPropertiesBuilderBase add(Mqtt5UserProperty mqtt5UserProperty) {
            return (Mqtt5UserPropertiesBuilderBase) super.add(mqtt5UserProperty);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.datatypes.Mqtt5UserPropertiesBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5UserPropertiesBuilderBase add(String str, String str2) {
            return (Mqtt5UserPropertiesBuilderBase) super.add(str, str2);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.datatypes.Mqtt5UserPropertiesBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5UserPropertiesBuilderBase addAll(Collection collection) {
            return (Mqtt5UserPropertiesBuilderBase) super.addAll((Collection<? extends Mqtt5UserProperty>) collection);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.datatypes.Mqtt5UserPropertiesBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5UserPropertiesBuilderBase addAll(Stream stream) {
            return (Mqtt5UserPropertiesBuilderBase) super.addAll((Stream<? extends Mqtt5UserProperty>) stream);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.datatypes.Mqtt5UserPropertiesBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5UserPropertiesBuilderBase addAll(Mqtt5UserProperty[] mqtt5UserPropertyArr) {
            return (Mqtt5UserPropertiesBuilderBase) super.addAll(mqtt5UserPropertyArr);
        }

        public Nested(MqttUserPropertiesImpl mqttUserPropertiesImpl, Function<? super MqttUserPropertiesImpl, P> function) {
            super(mqttUserPropertiesImpl);
            this.parentConsumer = function;
        }

        @Override // com.hivemq.client.mqtt.mqtt5.datatypes.Mqtt5UserPropertiesBuilder.Nested
        public P applyUserProperties() {
            return this.parentConsumer.apply(build());
        }
    }
}
