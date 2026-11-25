package com.hivemq.client.internal.mqtt.message.auth;

import com.hivemq.client.internal.mqtt.datatypes.MqttUtf8StringImpl;
import com.hivemq.client.internal.mqtt.message.auth.MqttSimpleAuthBuilder;
import com.hivemq.client.internal.mqtt.util.MqttChecks;
import com.hivemq.client.internal.util.Checks;
import com.hivemq.client.mqtt.datatypes.MqttUtf8String;
import com.hivemq.client.mqtt.mqtt5.message.auth.Mqtt5SimpleAuth;
import com.hivemq.client.mqtt.mqtt5.message.auth.Mqtt5SimpleAuthBuilder;
import com.hivemq.client.mqtt.mqtt5.message.auth.Mqtt5SimpleAuthBuilderBase;
import java.nio.ByteBuffer;
import java.util.function.Function;

/* JADX INFO: loaded from: classes10.dex */
public abstract class MqttSimpleAuthBuilder<B extends MqttSimpleAuthBuilder<B>> {
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

    public MqttSimpleAuth build() {
        Checks.state((this.username == null && this.password == null) ? false : true, "Either user name or password must be given.");
        return new MqttSimpleAuth(this.username, this.password);
    }

    public static class Default extends MqttSimpleAuthBuilder<Default> implements Mqtt5SimpleAuthBuilder.Complete {
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.hivemq.client.internal.mqtt.message.auth.MqttSimpleAuthBuilder
        public Default self() {
            return this;
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.auth.Mqtt5SimpleAuthBuilder.Complete
        public /* bridge */ /* synthetic */ Mqtt5SimpleAuth build() {
            return super.build();
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.auth.Mqtt5SimpleAuthBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5SimpleAuthBuilderBase password(ByteBuffer byteBuffer) {
            return (Mqtt5SimpleAuthBuilderBase) super.password(byteBuffer);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.auth.Mqtt5SimpleAuthBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5SimpleAuthBuilderBase password(byte[] bArr) {
            return (Mqtt5SimpleAuthBuilderBase) super.password(bArr);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.auth.Mqtt5SimpleAuthBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5SimpleAuthBuilderBase username(MqttUtf8String mqttUtf8String) {
            return (Mqtt5SimpleAuthBuilderBase) super.username(mqttUtf8String);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.auth.Mqtt5SimpleAuthBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5SimpleAuthBuilderBase username(String str) {
            return (Mqtt5SimpleAuthBuilderBase) super.username(str);
        }
    }

    public static class Nested<P> extends MqttSimpleAuthBuilder<Nested<P>> implements Mqtt5SimpleAuthBuilder.Nested.Complete<P> {
        private final Function<? super MqttSimpleAuth, P> parentConsumer;

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.hivemq.client.internal.mqtt.message.auth.MqttSimpleAuthBuilder
        public Nested<P> self() {
            return this;
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.auth.Mqtt5SimpleAuthBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5SimpleAuthBuilderBase password(ByteBuffer byteBuffer) {
            return (Mqtt5SimpleAuthBuilderBase) super.password(byteBuffer);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.auth.Mqtt5SimpleAuthBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5SimpleAuthBuilderBase password(byte[] bArr) {
            return (Mqtt5SimpleAuthBuilderBase) super.password(bArr);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.auth.Mqtt5SimpleAuthBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5SimpleAuthBuilderBase username(MqttUtf8String mqttUtf8String) {
            return (Mqtt5SimpleAuthBuilderBase) super.username(mqttUtf8String);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.auth.Mqtt5SimpleAuthBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5SimpleAuthBuilderBase username(String str) {
            return (Mqtt5SimpleAuthBuilderBase) super.username(str);
        }

        public Nested(Function<? super MqttSimpleAuth, P> function) {
            this.parentConsumer = function;
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.auth.Mqtt5SimpleAuthBuilder.Nested.Complete
        public P applySimpleAuth() {
            return this.parentConsumer.apply(build());
        }
    }
}
