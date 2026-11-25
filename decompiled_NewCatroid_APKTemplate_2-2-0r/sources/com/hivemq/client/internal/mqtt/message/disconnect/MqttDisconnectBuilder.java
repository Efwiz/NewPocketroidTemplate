package com.hivemq.client.internal.mqtt.message.disconnect;

import com.hivemq.client.internal.mqtt.datatypes.MqttUserPropertiesImpl;
import com.hivemq.client.internal.mqtt.datatypes.MqttUserPropertiesImplBuilder;
import com.hivemq.client.internal.mqtt.datatypes.MqttUtf8StringImpl;
import com.hivemq.client.internal.mqtt.message.disconnect.MqttDisconnectBuilder;
import com.hivemq.client.internal.mqtt.util.MqttChecks;
import com.hivemq.client.internal.util.Checks;
import com.hivemq.client.mqtt.datatypes.MqttUtf8String;
import com.hivemq.client.mqtt.mqtt5.datatypes.Mqtt5UserProperties;
import com.hivemq.client.mqtt.mqtt5.datatypes.Mqtt5UserPropertiesBuilder;
import com.hivemq.client.mqtt.mqtt5.message.disconnect.Mqtt5Disconnect;
import com.hivemq.client.mqtt.mqtt5.message.disconnect.Mqtt5DisconnectBuilder;
import com.hivemq.client.mqtt.mqtt5.message.disconnect.Mqtt5DisconnectBuilderBase;
import com.hivemq.client.mqtt.mqtt5.message.disconnect.Mqtt5DisconnectReasonCode;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: loaded from: classes10.dex */
public abstract class MqttDisconnectBuilder<B extends MqttDisconnectBuilder<B>> {
    private Mqtt5DisconnectReasonCode reasonCode;
    private MqttUtf8StringImpl reasonString;
    private MqttUtf8StringImpl serverReference;
    private long sessionExpiryInterval;
    private MqttUserPropertiesImpl userProperties;

    abstract B self();

    MqttDisconnectBuilder() {
        this.reasonCode = MqttDisconnect.DEFAULT_REASON_CODE;
        this.sessionExpiryInterval = -1L;
        this.userProperties = MqttUserPropertiesImpl.NO_USER_PROPERTIES;
    }

    MqttDisconnectBuilder(MqttDisconnect mqttDisconnect) {
        this.reasonCode = MqttDisconnect.DEFAULT_REASON_CODE;
        this.sessionExpiryInterval = -1L;
        this.userProperties = MqttUserPropertiesImpl.NO_USER_PROPERTIES;
        this.reasonCode = (Mqtt5DisconnectReasonCode) mqttDisconnect.getReasonCode();
        this.sessionExpiryInterval = mqttDisconnect.getRawSessionExpiryInterval();
        this.serverReference = mqttDisconnect.getRawServerReference();
        this.reasonString = mqttDisconnect.getRawReasonString();
        this.userProperties = mqttDisconnect.getUserProperties();
    }

    public B reasonCode(Mqtt5DisconnectReasonCode mqtt5DisconnectReasonCode) {
        this.reasonCode = (Mqtt5DisconnectReasonCode) Checks.notNull(mqtt5DisconnectReasonCode, "Reason Code");
        return (B) self();
    }

    public B sessionExpiryInterval(long j) {
        this.sessionExpiryInterval = Checks.unsignedInt(j, "Session expiry interval");
        return (B) self();
    }

    public B noSessionExpiry() {
        this.sessionExpiryInterval = 4294967295L;
        return (B) self();
    }

    public B serverReference(String str) {
        this.serverReference = MqttChecks.stringOrNull(str, "Server reference");
        return (B) self();
    }

    public B serverReference(MqttUtf8String mqttUtf8String) {
        this.serverReference = MqttChecks.stringOrNull(mqttUtf8String, "Server reference");
        return (B) self();
    }

    public B reasonString(String str) {
        this.reasonString = MqttChecks.reasonString(str);
        return (B) self();
    }

    public B reasonString(MqttUtf8String mqttUtf8String) {
        this.reasonString = MqttChecks.reasonString(mqttUtf8String);
        return (B) self();
    }

    public B userProperties(Mqtt5UserProperties mqtt5UserProperties) {
        this.userProperties = MqttChecks.userProperties(mqtt5UserProperties);
        return (B) self();
    }

    public MqttUserPropertiesImplBuilder.Nested<B> userProperties() {
        return new MqttUserPropertiesImplBuilder.Nested<>(this.userProperties, new Function() { // from class: com.hivemq.client.internal.mqtt.message.disconnect.MqttDisconnectBuilder$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.userProperties((MqttUserPropertiesImpl) obj);
            }
        });
    }

    public MqttDisconnect build() {
        return new MqttDisconnect(this.reasonCode, this.sessionExpiryInterval, this.serverReference, this.reasonString, this.userProperties);
    }

    public static class Default extends MqttDisconnectBuilder<Default> implements Mqtt5DisconnectBuilder {
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.hivemq.client.internal.mqtt.message.disconnect.MqttDisconnectBuilder
        public Default self() {
            return this;
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.disconnect.Mqtt5DisconnectBuilder
        public /* bridge */ /* synthetic */ Mqtt5Disconnect build() {
            return super.build();
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.disconnect.Mqtt5DisconnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5DisconnectBuilderBase noSessionExpiry() {
            return (Mqtt5DisconnectBuilderBase) super.noSessionExpiry();
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.disconnect.Mqtt5DisconnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5DisconnectBuilderBase reasonCode(Mqtt5DisconnectReasonCode mqtt5DisconnectReasonCode) {
            return (Mqtt5DisconnectBuilderBase) super.reasonCode(mqtt5DisconnectReasonCode);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.disconnect.Mqtt5DisconnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5DisconnectBuilderBase reasonString(MqttUtf8String mqttUtf8String) {
            return (Mqtt5DisconnectBuilderBase) super.reasonString(mqttUtf8String);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.disconnect.Mqtt5DisconnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5DisconnectBuilderBase reasonString(String str) {
            return (Mqtt5DisconnectBuilderBase) super.reasonString(str);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.disconnect.Mqtt5DisconnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5DisconnectBuilderBase serverReference(MqttUtf8String mqttUtf8String) {
            return (Mqtt5DisconnectBuilderBase) super.serverReference(mqttUtf8String);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.disconnect.Mqtt5DisconnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5DisconnectBuilderBase serverReference(String str) {
            return (Mqtt5DisconnectBuilderBase) super.serverReference(str);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.disconnect.Mqtt5DisconnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5DisconnectBuilderBase sessionExpiryInterval(long j) {
            return (Mqtt5DisconnectBuilderBase) super.sessionExpiryInterval(j);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.disconnect.Mqtt5DisconnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5UserPropertiesBuilder.Nested userProperties() {
            return super.userProperties();
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.disconnect.Mqtt5DisconnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5DisconnectBuilderBase userProperties(Mqtt5UserProperties mqtt5UserProperties) {
            return (Mqtt5DisconnectBuilderBase) super.userProperties(mqtt5UserProperties);
        }

        public Default() {
        }

        Default(MqttDisconnect mqttDisconnect) {
            super(mqttDisconnect);
        }
    }

    public static class Nested<P> extends MqttDisconnectBuilder<Nested<P>> implements Mqtt5DisconnectBuilder.Nested<P> {
        private final Function<? super MqttDisconnect, P> parentConsumer;

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.hivemq.client.internal.mqtt.message.disconnect.MqttDisconnectBuilder
        public Nested<P> self() {
            return this;
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.disconnect.Mqtt5DisconnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5DisconnectBuilderBase noSessionExpiry() {
            return (Mqtt5DisconnectBuilderBase) super.noSessionExpiry();
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.disconnect.Mqtt5DisconnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5DisconnectBuilderBase reasonCode(Mqtt5DisconnectReasonCode mqtt5DisconnectReasonCode) {
            return (Mqtt5DisconnectBuilderBase) super.reasonCode(mqtt5DisconnectReasonCode);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.disconnect.Mqtt5DisconnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5DisconnectBuilderBase reasonString(MqttUtf8String mqttUtf8String) {
            return (Mqtt5DisconnectBuilderBase) super.reasonString(mqttUtf8String);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.disconnect.Mqtt5DisconnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5DisconnectBuilderBase reasonString(String str) {
            return (Mqtt5DisconnectBuilderBase) super.reasonString(str);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.disconnect.Mqtt5DisconnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5DisconnectBuilderBase serverReference(MqttUtf8String mqttUtf8String) {
            return (Mqtt5DisconnectBuilderBase) super.serverReference(mqttUtf8String);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.disconnect.Mqtt5DisconnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5DisconnectBuilderBase serverReference(String str) {
            return (Mqtt5DisconnectBuilderBase) super.serverReference(str);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.disconnect.Mqtt5DisconnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5DisconnectBuilderBase sessionExpiryInterval(long j) {
            return (Mqtt5DisconnectBuilderBase) super.sessionExpiryInterval(j);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.disconnect.Mqtt5DisconnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5UserPropertiesBuilder.Nested userProperties() {
            return super.userProperties();
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.disconnect.Mqtt5DisconnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5DisconnectBuilderBase userProperties(Mqtt5UserProperties mqtt5UserProperties) {
            return (Mqtt5DisconnectBuilderBase) super.userProperties(mqtt5UserProperties);
        }

        public Nested(Function<? super MqttDisconnect, P> function) {
            this.parentConsumer = function;
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.disconnect.Mqtt5DisconnectBuilder.Nested
        public P applyDisconnect() {
            return this.parentConsumer.apply(build());
        }
    }

    public static class Send<P> extends MqttDisconnectBuilder<Send<P>> implements Mqtt5DisconnectBuilder.Send<P> {
        private final Function<? super MqttDisconnect, P> parentConsumer;

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.hivemq.client.internal.mqtt.message.disconnect.MqttDisconnectBuilder
        public Send<P> self() {
            return this;
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.disconnect.Mqtt5DisconnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5DisconnectBuilderBase noSessionExpiry() {
            return (Mqtt5DisconnectBuilderBase) super.noSessionExpiry();
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.disconnect.Mqtt5DisconnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5DisconnectBuilderBase reasonCode(Mqtt5DisconnectReasonCode mqtt5DisconnectReasonCode) {
            return (Mqtt5DisconnectBuilderBase) super.reasonCode(mqtt5DisconnectReasonCode);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.disconnect.Mqtt5DisconnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5DisconnectBuilderBase reasonString(MqttUtf8String mqttUtf8String) {
            return (Mqtt5DisconnectBuilderBase) super.reasonString(mqttUtf8String);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.disconnect.Mqtt5DisconnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5DisconnectBuilderBase reasonString(String str) {
            return (Mqtt5DisconnectBuilderBase) super.reasonString(str);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.disconnect.Mqtt5DisconnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5DisconnectBuilderBase serverReference(MqttUtf8String mqttUtf8String) {
            return (Mqtt5DisconnectBuilderBase) super.serverReference(mqttUtf8String);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.disconnect.Mqtt5DisconnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5DisconnectBuilderBase serverReference(String str) {
            return (Mqtt5DisconnectBuilderBase) super.serverReference(str);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.disconnect.Mqtt5DisconnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5DisconnectBuilderBase sessionExpiryInterval(long j) {
            return (Mqtt5DisconnectBuilderBase) super.sessionExpiryInterval(j);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.disconnect.Mqtt5DisconnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5UserPropertiesBuilder.Nested userProperties() {
            return super.userProperties();
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.disconnect.Mqtt5DisconnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5DisconnectBuilderBase userProperties(Mqtt5UserProperties mqtt5UserProperties) {
            return (Mqtt5DisconnectBuilderBase) super.userProperties(mqtt5UserProperties);
        }

        public Send(Function<? super MqttDisconnect, P> function) {
            this.parentConsumer = function;
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.disconnect.Mqtt5DisconnectBuilder.Send
        public P send() {
            return this.parentConsumer.apply(build());
        }
    }

    public static class SendVoid extends MqttDisconnectBuilder<SendVoid> implements Mqtt5DisconnectBuilder.SendVoid {
        private final Consumer<? super MqttDisconnect> parentConsumer;

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.hivemq.client.internal.mqtt.message.disconnect.MqttDisconnectBuilder
        public SendVoid self() {
            return this;
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.disconnect.Mqtt5DisconnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5DisconnectBuilderBase noSessionExpiry() {
            return (Mqtt5DisconnectBuilderBase) super.noSessionExpiry();
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.disconnect.Mqtt5DisconnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5DisconnectBuilderBase reasonCode(Mqtt5DisconnectReasonCode mqtt5DisconnectReasonCode) {
            return (Mqtt5DisconnectBuilderBase) super.reasonCode(mqtt5DisconnectReasonCode);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.disconnect.Mqtt5DisconnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5DisconnectBuilderBase reasonString(MqttUtf8String mqttUtf8String) {
            return (Mqtt5DisconnectBuilderBase) super.reasonString(mqttUtf8String);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.disconnect.Mqtt5DisconnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5DisconnectBuilderBase reasonString(String str) {
            return (Mqtt5DisconnectBuilderBase) super.reasonString(str);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.disconnect.Mqtt5DisconnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5DisconnectBuilderBase serverReference(MqttUtf8String mqttUtf8String) {
            return (Mqtt5DisconnectBuilderBase) super.serverReference(mqttUtf8String);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.disconnect.Mqtt5DisconnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5DisconnectBuilderBase serverReference(String str) {
            return (Mqtt5DisconnectBuilderBase) super.serverReference(str);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.disconnect.Mqtt5DisconnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5DisconnectBuilderBase sessionExpiryInterval(long j) {
            return (Mqtt5DisconnectBuilderBase) super.sessionExpiryInterval(j);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.disconnect.Mqtt5DisconnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5UserPropertiesBuilder.Nested userProperties() {
            return super.userProperties();
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.disconnect.Mqtt5DisconnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5DisconnectBuilderBase userProperties(Mqtt5UserProperties mqtt5UserProperties) {
            return (Mqtt5DisconnectBuilderBase) super.userProperties(mqtt5UserProperties);
        }

        public SendVoid(Consumer<? super MqttDisconnect> consumer) {
            this.parentConsumer = consumer;
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.disconnect.Mqtt5DisconnectBuilder.SendVoid
        public void send() {
            this.parentConsumer.accept(build());
        }
    }
}
