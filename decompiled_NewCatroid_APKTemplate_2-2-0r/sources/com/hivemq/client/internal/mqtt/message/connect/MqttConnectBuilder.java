package com.hivemq.client.internal.mqtt.message.connect;

import com.hivemq.client.internal.mqtt.datatypes.MqttUserPropertiesImpl;
import com.hivemq.client.internal.mqtt.datatypes.MqttUserPropertiesImplBuilder;
import com.hivemq.client.internal.mqtt.message.auth.MqttSimpleAuth;
import com.hivemq.client.internal.mqtt.message.auth.MqttSimpleAuthBuilder;
import com.hivemq.client.internal.mqtt.message.connect.MqttConnectBuilder;
import com.hivemq.client.internal.mqtt.message.publish.MqttPublish;
import com.hivemq.client.internal.mqtt.message.publish.MqttPublishBuilder;
import com.hivemq.client.internal.mqtt.message.publish.MqttWillPublish;
import com.hivemq.client.internal.mqtt.util.MqttChecks;
import com.hivemq.client.internal.util.Checks;
import com.hivemq.client.mqtt.mqtt5.auth.Mqtt5EnhancedAuthMechanism;
import com.hivemq.client.mqtt.mqtt5.datatypes.Mqtt5UserProperties;
import com.hivemq.client.mqtt.mqtt5.datatypes.Mqtt5UserPropertiesBuilder;
import com.hivemq.client.mqtt.mqtt5.message.auth.Mqtt5SimpleAuth;
import com.hivemq.client.mqtt.mqtt5.message.auth.Mqtt5SimpleAuthBuilder;
import com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5Connect;
import com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5ConnectBuilder;
import com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5ConnectBuilderBase;
import com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5ConnectRestrictions;
import com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5ConnectRestrictionsBuilder;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5WillPublishBuilder;
import java.util.function.Function;

/* JADX INFO: loaded from: classes10.dex */
public abstract class MqttConnectBuilder<B extends MqttConnectBuilder<B>> {
    private boolean cleanStart;
    private Mqtt5EnhancedAuthMechanism enhancedAuthMechanism;
    private int keepAlive;
    private MqttConnectRestrictions restrictions;
    private long sessionExpiryInterval;
    private MqttSimpleAuth simpleAuth;
    private MqttUserPropertiesImpl userProperties;
    private MqttWillPublish willPublish;

    abstract B self();

    MqttConnectBuilder() {
        this.keepAlive = 60;
        this.cleanStart = true;
        this.sessionExpiryInterval = 0L;
        this.restrictions = MqttConnectRestrictions.DEFAULT;
        this.userProperties = MqttUserPropertiesImpl.NO_USER_PROPERTIES;
    }

    MqttConnectBuilder(MqttConnect mqttConnect) {
        this.keepAlive = 60;
        this.cleanStart = true;
        this.sessionExpiryInterval = 0L;
        this.restrictions = MqttConnectRestrictions.DEFAULT;
        this.userProperties = MqttUserPropertiesImpl.NO_USER_PROPERTIES;
        this.keepAlive = mqttConnect.getKeepAlive();
        this.cleanStart = mqttConnect.isCleanStart();
        this.sessionExpiryInterval = mqttConnect.getSessionExpiryInterval();
        this.restrictions = mqttConnect.getRestrictions();
        this.simpleAuth = mqttConnect.getRawSimpleAuth();
        this.enhancedAuthMechanism = mqttConnect.getRawEnhancedAuthMechanism();
        this.willPublish = mqttConnect.getRawWillPublish();
        this.userProperties = mqttConnect.getUserProperties();
    }

    public B keepAlive(int i) {
        this.keepAlive = Checks.unsignedShort(i, "Keep alive");
        return (B) self();
    }

    public B noKeepAlive() {
        this.keepAlive = 0;
        return (B) self();
    }

    public B cleanStart(boolean z) {
        this.cleanStart = z;
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

    public B restrictions(Mqtt5ConnectRestrictions mqtt5ConnectRestrictions) {
        this.restrictions = (MqttConnectRestrictions) Checks.notImplemented(mqtt5ConnectRestrictions, MqttConnectRestrictions.class, "Connect restrictions");
        return (B) self();
    }

    public MqttConnectRestrictionsBuilder.Nested<B> restrictions() {
        return new MqttConnectRestrictionsBuilder.Nested<>(this.restrictions, new Function() { // from class: com.hivemq.client.internal.mqtt.message.connect.MqttConnectBuilder$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.restrictions((MqttConnectRestrictions) obj);
            }
        });
    }

    public B simpleAuth(Mqtt5SimpleAuth mqtt5SimpleAuth) {
        this.simpleAuth = (MqttSimpleAuth) Checks.notImplementedOrNull(mqtt5SimpleAuth, MqttSimpleAuth.class, "Simple auth");
        return (B) self();
    }

    public MqttSimpleAuthBuilder.Nested<B> simpleAuth() {
        return new MqttSimpleAuthBuilder.Nested<>(new Function() { // from class: com.hivemq.client.internal.mqtt.message.connect.MqttConnectBuilder$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.simpleAuth((MqttSimpleAuth) obj);
            }
        });
    }

    public B enhancedAuth(Mqtt5EnhancedAuthMechanism mqtt5EnhancedAuthMechanism) {
        this.enhancedAuthMechanism = mqtt5EnhancedAuthMechanism;
        return (B) self();
    }

    public B willPublish(Mqtt5Publish mqtt5Publish) {
        this.willPublish = mqtt5Publish == null ? null : ((MqttPublish) Checks.notImplemented(mqtt5Publish, MqttPublish.class, "Will publish")).asWill();
        return (B) self();
    }

    public MqttPublishBuilder.WillNested<B> willPublish() {
        return new MqttPublishBuilder.WillNested<>(new Function() { // from class: com.hivemq.client.internal.mqtt.message.connect.MqttConnectBuilder$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.willPublish((MqttWillPublish) obj);
            }
        });
    }

    public B userProperties(Mqtt5UserProperties mqtt5UserProperties) {
        this.userProperties = MqttChecks.userProperties(mqtt5UserProperties);
        return (B) self();
    }

    public MqttUserPropertiesImplBuilder.Nested<B> userProperties() {
        return new MqttUserPropertiesImplBuilder.Nested<>(this.userProperties, new Function() { // from class: com.hivemq.client.internal.mqtt.message.connect.MqttConnectBuilder$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.userProperties((MqttUserPropertiesImpl) obj);
            }
        });
    }

    public MqttConnect build() {
        return new MqttConnect(this.keepAlive, this.cleanStart, this.sessionExpiryInterval, this.restrictions, this.simpleAuth, this.enhancedAuthMechanism, this.willPublish, this.userProperties);
    }

    public static class Default extends MqttConnectBuilder<Default> implements Mqtt5ConnectBuilder {
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.hivemq.client.internal.mqtt.message.connect.MqttConnectBuilder
        public Default self() {
            return this;
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5ConnectBuilder
        public /* bridge */ /* synthetic */ Mqtt5Connect build() {
            return super.build();
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5ConnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5ConnectBuilderBase cleanStart(boolean z) {
            return (Mqtt5ConnectBuilderBase) super.cleanStart(z);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5ConnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5ConnectBuilderBase enhancedAuth(Mqtt5EnhancedAuthMechanism mqtt5EnhancedAuthMechanism) {
            return (Mqtt5ConnectBuilderBase) super.enhancedAuth(mqtt5EnhancedAuthMechanism);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5ConnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5ConnectBuilderBase keepAlive(int i) {
            return (Mqtt5ConnectBuilderBase) super.keepAlive(i);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5ConnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5ConnectBuilderBase noKeepAlive() {
            return (Mqtt5ConnectBuilderBase) super.noKeepAlive();
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5ConnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5ConnectBuilderBase noSessionExpiry() {
            return (Mqtt5ConnectBuilderBase) super.noSessionExpiry();
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5ConnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5ConnectBuilderBase restrictions(Mqtt5ConnectRestrictions mqtt5ConnectRestrictions) {
            return (Mqtt5ConnectBuilderBase) super.restrictions(mqtt5ConnectRestrictions);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5ConnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5ConnectRestrictionsBuilder.Nested restrictions() {
            return super.restrictions();
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5ConnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5ConnectBuilderBase sessionExpiryInterval(long j) {
            return (Mqtt5ConnectBuilderBase) super.sessionExpiryInterval(j);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5ConnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5SimpleAuthBuilder.Nested simpleAuth() {
            return super.simpleAuth();
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5ConnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5ConnectBuilderBase simpleAuth(Mqtt5SimpleAuth mqtt5SimpleAuth) {
            return (Mqtt5ConnectBuilderBase) super.simpleAuth(mqtt5SimpleAuth);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5ConnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5UserPropertiesBuilder.Nested userProperties() {
            return super.userProperties();
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5ConnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5ConnectBuilderBase userProperties(Mqtt5UserProperties mqtt5UserProperties) {
            return (Mqtt5ConnectBuilderBase) super.userProperties(mqtt5UserProperties);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5ConnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5ConnectBuilderBase willPublish(Mqtt5Publish mqtt5Publish) {
            return (Mqtt5ConnectBuilderBase) super.willPublish(mqtt5Publish);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5ConnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5WillPublishBuilder.Nested willPublish() {
            return super.willPublish();
        }

        public Default() {
        }

        Default(MqttConnect mqttConnect) {
            super(mqttConnect);
        }
    }

    public static class Nested<P> extends MqttConnectBuilder<Nested<P>> implements Mqtt5ConnectBuilder.Nested<P> {
        private final Function<? super MqttConnect, P> parentConsumer;

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.hivemq.client.internal.mqtt.message.connect.MqttConnectBuilder
        public Nested<P> self() {
            return this;
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5ConnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5ConnectBuilderBase cleanStart(boolean z) {
            return (Mqtt5ConnectBuilderBase) super.cleanStart(z);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5ConnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5ConnectBuilderBase enhancedAuth(Mqtt5EnhancedAuthMechanism mqtt5EnhancedAuthMechanism) {
            return (Mqtt5ConnectBuilderBase) super.enhancedAuth(mqtt5EnhancedAuthMechanism);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5ConnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5ConnectBuilderBase keepAlive(int i) {
            return (Mqtt5ConnectBuilderBase) super.keepAlive(i);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5ConnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5ConnectBuilderBase noKeepAlive() {
            return (Mqtt5ConnectBuilderBase) super.noKeepAlive();
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5ConnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5ConnectBuilderBase noSessionExpiry() {
            return (Mqtt5ConnectBuilderBase) super.noSessionExpiry();
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5ConnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5ConnectBuilderBase restrictions(Mqtt5ConnectRestrictions mqtt5ConnectRestrictions) {
            return (Mqtt5ConnectBuilderBase) super.restrictions(mqtt5ConnectRestrictions);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5ConnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5ConnectRestrictionsBuilder.Nested restrictions() {
            return super.restrictions();
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5ConnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5ConnectBuilderBase sessionExpiryInterval(long j) {
            return (Mqtt5ConnectBuilderBase) super.sessionExpiryInterval(j);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5ConnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5SimpleAuthBuilder.Nested simpleAuth() {
            return super.simpleAuth();
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5ConnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5ConnectBuilderBase simpleAuth(Mqtt5SimpleAuth mqtt5SimpleAuth) {
            return (Mqtt5ConnectBuilderBase) super.simpleAuth(mqtt5SimpleAuth);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5ConnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5UserPropertiesBuilder.Nested userProperties() {
            return super.userProperties();
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5ConnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5ConnectBuilderBase userProperties(Mqtt5UserProperties mqtt5UserProperties) {
            return (Mqtt5ConnectBuilderBase) super.userProperties(mqtt5UserProperties);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5ConnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5ConnectBuilderBase willPublish(Mqtt5Publish mqtt5Publish) {
            return (Mqtt5ConnectBuilderBase) super.willPublish(mqtt5Publish);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5ConnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5WillPublishBuilder.Nested willPublish() {
            return super.willPublish();
        }

        public Nested(Function<? super MqttConnect, P> function) {
            this.parentConsumer = function;
        }

        public Nested(MqttConnect mqttConnect, Function<? super MqttConnect, P> function) {
            super(mqttConnect);
            this.parentConsumer = function;
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5ConnectBuilder.Nested
        public P applyConnect() {
            return this.parentConsumer.apply(build());
        }
    }

    public static class Send<P> extends MqttConnectBuilder<Send<P>> implements Mqtt5ConnectBuilder.Send<P> {
        private final Function<? super MqttConnect, P> parentConsumer;

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.hivemq.client.internal.mqtt.message.connect.MqttConnectBuilder
        public Send<P> self() {
            return this;
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5ConnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5ConnectBuilderBase cleanStart(boolean z) {
            return (Mqtt5ConnectBuilderBase) super.cleanStart(z);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5ConnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5ConnectBuilderBase enhancedAuth(Mqtt5EnhancedAuthMechanism mqtt5EnhancedAuthMechanism) {
            return (Mqtt5ConnectBuilderBase) super.enhancedAuth(mqtt5EnhancedAuthMechanism);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5ConnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5ConnectBuilderBase keepAlive(int i) {
            return (Mqtt5ConnectBuilderBase) super.keepAlive(i);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5ConnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5ConnectBuilderBase noKeepAlive() {
            return (Mqtt5ConnectBuilderBase) super.noKeepAlive();
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5ConnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5ConnectBuilderBase noSessionExpiry() {
            return (Mqtt5ConnectBuilderBase) super.noSessionExpiry();
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5ConnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5ConnectBuilderBase restrictions(Mqtt5ConnectRestrictions mqtt5ConnectRestrictions) {
            return (Mqtt5ConnectBuilderBase) super.restrictions(mqtt5ConnectRestrictions);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5ConnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5ConnectRestrictionsBuilder.Nested restrictions() {
            return super.restrictions();
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5ConnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5ConnectBuilderBase sessionExpiryInterval(long j) {
            return (Mqtt5ConnectBuilderBase) super.sessionExpiryInterval(j);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5ConnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5SimpleAuthBuilder.Nested simpleAuth() {
            return super.simpleAuth();
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5ConnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5ConnectBuilderBase simpleAuth(Mqtt5SimpleAuth mqtt5SimpleAuth) {
            return (Mqtt5ConnectBuilderBase) super.simpleAuth(mqtt5SimpleAuth);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5ConnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5UserPropertiesBuilder.Nested userProperties() {
            return super.userProperties();
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5ConnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5ConnectBuilderBase userProperties(Mqtt5UserProperties mqtt5UserProperties) {
            return (Mqtt5ConnectBuilderBase) super.userProperties(mqtt5UserProperties);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5ConnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5ConnectBuilderBase willPublish(Mqtt5Publish mqtt5Publish) {
            return (Mqtt5ConnectBuilderBase) super.willPublish(mqtt5Publish);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5ConnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5WillPublishBuilder.Nested willPublish() {
            return super.willPublish();
        }

        public Send(Function<? super MqttConnect, P> function) {
            this.parentConsumer = function;
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5ConnectBuilder.Send
        public P send() {
            return this.parentConsumer.apply(build());
        }
    }
}
