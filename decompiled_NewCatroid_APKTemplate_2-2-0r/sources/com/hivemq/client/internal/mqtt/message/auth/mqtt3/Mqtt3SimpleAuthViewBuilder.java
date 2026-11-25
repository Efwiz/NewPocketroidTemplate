package com.hivemq.client.internal.mqtt.message.auth.mqtt3;

import com.hivemq.client.internal.mqtt.datatypes.MqttUtf8StringImpl;
import com.hivemq.client.internal.mqtt.message.auth.mqtt3.Mqtt3SimpleAuthViewBuilder;
import com.hivemq.client.internal.mqtt.util.MqttChecks;
import com.hivemq.client.internal.util.Checks;
import com.hivemq.client.mqtt.datatypes.MqttUtf8String;
import com.hivemq.client.mqtt.mqtt3.message.auth.Mqtt3SimpleAuth;
import com.hivemq.client.mqtt.mqtt3.message.auth.Mqtt3SimpleAuthBuilder;
import com.hivemq.client.mqtt.mqtt3.message.auth.Mqtt3SimpleAuthBuilderBase;
import java.nio.ByteBuffer;
import java.util.function.Function;

/* JADX INFO: loaded from: classes10.dex */
public abstract class Mqtt3SimpleAuthViewBuilder<B extends Mqtt3SimpleAuthViewBuilder<B>> {
    private ByteBuffer password;
    private MqttUtf8StringImpl username;

    abstract B self();

    public B username(String str) {
        this.username = MqttUtf8StringImpl.of(str, "Username");
        return (B) self();
    }

    public B username(MqttUtf8String mqttUtf8String) {
        this.username = MqttChecks.string(mqttUtf8String, "Username");
        return (B) self();
    }

    public B password(byte[] bArr) {
        this.password = MqttChecks.binaryData(bArr, "Password");
        return (B) self();
    }

    public B password(ByteBuffer byteBuffer) {
        this.password = MqttChecks.binaryData(byteBuffer, "Password");
        return (B) self();
    }

    public Mqtt3SimpleAuthView build() {
        Checks.state(this.username != null, "Username must be given.");
        return Mqtt3SimpleAuthView.of(this.username, this.password);
    }

    public static class Default extends Mqtt3SimpleAuthViewBuilder<Default> implements Mqtt3SimpleAuthBuilder.Complete {
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.hivemq.client.internal.mqtt.message.auth.mqtt3.Mqtt3SimpleAuthViewBuilder
        public Default self() {
            return this;
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.auth.Mqtt3SimpleAuthBuilder.Complete
        public /* bridge */ /* synthetic */ Mqtt3SimpleAuth build() {
            return super.build();
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.auth.Mqtt3SimpleAuthBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt3SimpleAuthBuilderBase.Complete password(ByteBuffer byteBuffer) {
            return (Mqtt3SimpleAuthBuilderBase.Complete) super.password(byteBuffer);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.auth.Mqtt3SimpleAuthBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt3SimpleAuthBuilderBase.Complete password(byte[] bArr) {
            return (Mqtt3SimpleAuthBuilderBase.Complete) super.password(bArr);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.auth.Mqtt3SimpleAuthBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3SimpleAuthBuilderBase.Complete username(MqttUtf8String mqttUtf8String) {
            return (Mqtt3SimpleAuthBuilderBase.Complete) super.username(mqttUtf8String);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.auth.Mqtt3SimpleAuthBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3SimpleAuthBuilderBase.Complete username(String str) {
            return (Mqtt3SimpleAuthBuilderBase.Complete) super.username(str);
        }
    }

    public static class Nested<P> extends Mqtt3SimpleAuthViewBuilder<Nested<P>> implements Mqtt3SimpleAuthBuilder.Nested.Complete<P> {
        private final Function<? super Mqtt3SimpleAuthView, P> parentConsumer;

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.hivemq.client.internal.mqtt.message.auth.mqtt3.Mqtt3SimpleAuthViewBuilder
        public Nested<P> self() {
            return this;
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.auth.Mqtt3SimpleAuthBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt3SimpleAuthBuilderBase.Complete password(ByteBuffer byteBuffer) {
            return (Mqtt3SimpleAuthBuilderBase.Complete) super.password(byteBuffer);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.auth.Mqtt3SimpleAuthBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt3SimpleAuthBuilderBase.Complete password(byte[] bArr) {
            return (Mqtt3SimpleAuthBuilderBase.Complete) super.password(bArr);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.auth.Mqtt3SimpleAuthBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3SimpleAuthBuilderBase.Complete username(MqttUtf8String mqttUtf8String) {
            return (Mqtt3SimpleAuthBuilderBase.Complete) super.username(mqttUtf8String);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.auth.Mqtt3SimpleAuthBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3SimpleAuthBuilderBase.Complete username(String str) {
            return (Mqtt3SimpleAuthBuilderBase.Complete) super.username(str);
        }

        public Nested(Function<? super Mqtt3SimpleAuthView, P> function) {
            this.parentConsumer = function;
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.auth.Mqtt3SimpleAuthBuilder.Nested.Complete
        public P applySimpleAuth() {
            return this.parentConsumer.apply(build());
        }
    }
}
