package com.hivemq.client.internal.mqtt.message.connect.mqtt3;

import com.hivemq.client.internal.mqtt.message.auth.MqttSimpleAuth;
import com.hivemq.client.internal.mqtt.message.auth.mqtt3.Mqtt3SimpleAuthView;
import com.hivemq.client.internal.mqtt.message.auth.mqtt3.Mqtt3SimpleAuthViewBuilder;
import com.hivemq.client.internal.mqtt.message.connect.MqttConnect;
import com.hivemq.client.internal.mqtt.message.connect.MqttConnectRestrictions;
import com.hivemq.client.internal.mqtt.message.connect.MqttConnectRestrictionsBuilder;
import com.hivemq.client.internal.mqtt.message.connect.mqtt3.Mqtt3ConnectViewBuilder;
import com.hivemq.client.internal.mqtt.message.publish.MqttWillPublish;
import com.hivemq.client.internal.mqtt.message.publish.mqtt3.Mqtt3PublishView;
import com.hivemq.client.internal.mqtt.message.publish.mqtt3.Mqtt3PublishViewBuilder;
import com.hivemq.client.internal.util.Checks;
import com.hivemq.client.mqtt.mqtt3.message.auth.Mqtt3SimpleAuth;
import com.hivemq.client.mqtt.mqtt3.message.auth.Mqtt3SimpleAuthBuilder;
import com.hivemq.client.mqtt.mqtt3.message.connect.Mqtt3Connect;
import com.hivemq.client.mqtt.mqtt3.message.connect.Mqtt3ConnectBuilder;
import com.hivemq.client.mqtt.mqtt3.message.connect.Mqtt3ConnectBuilderBase;
import com.hivemq.client.mqtt.mqtt3.message.connect.Mqtt3ConnectRestrictions;
import com.hivemq.client.mqtt.mqtt3.message.connect.Mqtt3ConnectRestrictionsBuilder;
import com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3Publish;
import com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3WillPublishBuilder;
import java.util.function.Function;

/* JADX INFO: loaded from: classes10.dex */
public abstract class Mqtt3ConnectViewBuilder<B extends Mqtt3ConnectViewBuilder<B>> {
    private boolean cleanSession;
    private int keepAliveSeconds;
    private MqttConnectRestrictions restrictions;
    private MqttSimpleAuth simpleAuth;
    private MqttWillPublish willPublish;

    abstract B self();

    Mqtt3ConnectViewBuilder() {
        this.keepAliveSeconds = 60;
        this.cleanSession = true;
        this.restrictions = MqttConnectRestrictions.DEFAULT;
    }

    Mqtt3ConnectViewBuilder(Mqtt3ConnectView mqtt3ConnectView) {
        this.keepAliveSeconds = 60;
        this.cleanSession = true;
        this.restrictions = MqttConnectRestrictions.DEFAULT;
        MqttConnect delegate = mqtt3ConnectView.getDelegate();
        this.keepAliveSeconds = delegate.getKeepAlive();
        this.cleanSession = delegate.isCleanStart();
        this.restrictions = delegate.getRestrictions();
        this.simpleAuth = delegate.getRawSimpleAuth();
        this.willPublish = delegate.getRawWillPublish();
    }

    public B keepAlive(int i) {
        this.keepAliveSeconds = Checks.unsignedShort(i, "Keep alive");
        return (B) self();
    }

    public B noKeepAlive() {
        this.keepAliveSeconds = 0;
        return (B) self();
    }

    public B cleanSession(boolean z) {
        this.cleanSession = z;
        return (B) self();
    }

    public B restrictions(Mqtt3ConnectRestrictions mqtt3ConnectRestrictions) {
        this.restrictions = (MqttConnectRestrictions) Checks.notImplemented(mqtt3ConnectRestrictions, MqttConnectRestrictions.class, "Connect restrictions");
        return (B) self();
    }

    public MqttConnectRestrictionsBuilder.Nested<B> restrictions() {
        return new MqttConnectRestrictionsBuilder.Nested<>(this.restrictions, new Function() { // from class: com.hivemq.client.internal.mqtt.message.connect.mqtt3.Mqtt3ConnectViewBuilder$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.restrictions((MqttConnectRestrictions) obj);
            }
        });
    }

    public B simpleAuth(Mqtt3SimpleAuth mqtt3SimpleAuth) {
        this.simpleAuth = mqtt3SimpleAuth == null ? null : ((Mqtt3SimpleAuthView) Checks.notImplemented(mqtt3SimpleAuth, Mqtt3SimpleAuthView.class, "Simple auth")).getDelegate();
        return (B) self();
    }

    public Mqtt3SimpleAuthViewBuilder.Nested<B> simpleAuth() {
        return new Mqtt3SimpleAuthViewBuilder.Nested<>(new Function() { // from class: com.hivemq.client.internal.mqtt.message.connect.mqtt3.Mqtt3ConnectViewBuilder$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.simpleAuth((Mqtt3SimpleAuthView) obj);
            }
        });
    }

    public B willPublish(Mqtt3Publish mqtt3Publish) {
        this.willPublish = mqtt3Publish == null ? null : ((Mqtt3PublishView) Checks.notImplemented(mqtt3Publish, Mqtt3PublishView.class, "Will publish")).getDelegate().asWill();
        return (B) self();
    }

    public Mqtt3PublishViewBuilder.WillNested<B> willPublish() {
        return new Mqtt3PublishViewBuilder.WillNested<>(new Function() { // from class: com.hivemq.client.internal.mqtt.message.connect.mqtt3.Mqtt3ConnectViewBuilder$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.willPublish((Mqtt3PublishView) obj);
            }
        });
    }

    public Mqtt3ConnectView build() {
        return Mqtt3ConnectView.of(this.keepAliveSeconds, this.cleanSession, this.restrictions, this.simpleAuth, this.willPublish);
    }

    public static class Default extends Mqtt3ConnectViewBuilder<Default> implements Mqtt3ConnectBuilder {
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.hivemq.client.internal.mqtt.message.connect.mqtt3.Mqtt3ConnectViewBuilder
        public Default self() {
            return this;
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.connect.Mqtt3ConnectBuilder
        public /* bridge */ /* synthetic */ Mqtt3Connect build() {
            return super.build();
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.connect.Mqtt3ConnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3ConnectBuilderBase cleanSession(boolean z) {
            return (Mqtt3ConnectBuilderBase) super.cleanSession(z);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.connect.Mqtt3ConnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3ConnectBuilderBase keepAlive(int i) {
            return (Mqtt3ConnectBuilderBase) super.keepAlive(i);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.connect.Mqtt3ConnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3ConnectBuilderBase noKeepAlive() {
            return (Mqtt3ConnectBuilderBase) super.noKeepAlive();
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.connect.Mqtt3ConnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3ConnectBuilderBase restrictions(Mqtt3ConnectRestrictions mqtt3ConnectRestrictions) {
            return (Mqtt3ConnectBuilderBase) super.restrictions(mqtt3ConnectRestrictions);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.connect.Mqtt3ConnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3ConnectRestrictionsBuilder.Nested restrictions() {
            return super.restrictions();
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.connect.Mqtt3ConnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3SimpleAuthBuilder.Nested simpleAuth() {
            return super.simpleAuth();
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.connect.Mqtt3ConnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3ConnectBuilderBase simpleAuth(Mqtt3SimpleAuth mqtt3SimpleAuth) {
            return (Mqtt3ConnectBuilderBase) super.simpleAuth(mqtt3SimpleAuth);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.connect.Mqtt3ConnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3ConnectBuilderBase willPublish(Mqtt3Publish mqtt3Publish) {
            return (Mqtt3ConnectBuilderBase) super.willPublish(mqtt3Publish);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.connect.Mqtt3ConnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3WillPublishBuilder.Nested willPublish() {
            return super.willPublish();
        }

        public Default() {
        }

        Default(Mqtt3ConnectView mqtt3ConnectView) {
            super(mqtt3ConnectView);
        }
    }

    public static class Nested<P> extends Mqtt3ConnectViewBuilder<Nested<P>> implements Mqtt3ConnectBuilder.Nested<P> {
        private final Function<? super Mqtt3ConnectView, P> parentConsumer;

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.hivemq.client.internal.mqtt.message.connect.mqtt3.Mqtt3ConnectViewBuilder
        public Nested<P> self() {
            return this;
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.connect.Mqtt3ConnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3ConnectBuilderBase cleanSession(boolean z) {
            return (Mqtt3ConnectBuilderBase) super.cleanSession(z);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.connect.Mqtt3ConnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3ConnectBuilderBase keepAlive(int i) {
            return (Mqtt3ConnectBuilderBase) super.keepAlive(i);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.connect.Mqtt3ConnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3ConnectBuilderBase noKeepAlive() {
            return (Mqtt3ConnectBuilderBase) super.noKeepAlive();
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.connect.Mqtt3ConnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3ConnectBuilderBase restrictions(Mqtt3ConnectRestrictions mqtt3ConnectRestrictions) {
            return (Mqtt3ConnectBuilderBase) super.restrictions(mqtt3ConnectRestrictions);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.connect.Mqtt3ConnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3ConnectRestrictionsBuilder.Nested restrictions() {
            return super.restrictions();
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.connect.Mqtt3ConnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3SimpleAuthBuilder.Nested simpleAuth() {
            return super.simpleAuth();
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.connect.Mqtt3ConnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3ConnectBuilderBase simpleAuth(Mqtt3SimpleAuth mqtt3SimpleAuth) {
            return (Mqtt3ConnectBuilderBase) super.simpleAuth(mqtt3SimpleAuth);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.connect.Mqtt3ConnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3ConnectBuilderBase willPublish(Mqtt3Publish mqtt3Publish) {
            return (Mqtt3ConnectBuilderBase) super.willPublish(mqtt3Publish);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.connect.Mqtt3ConnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3WillPublishBuilder.Nested willPublish() {
            return super.willPublish();
        }

        public Nested(Function<? super Mqtt3ConnectView, P> function) {
            this.parentConsumer = function;
        }

        public Nested(Mqtt3ConnectView mqtt3ConnectView, Function<? super Mqtt3ConnectView, P> function) {
            super(mqtt3ConnectView);
            this.parentConsumer = function;
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.connect.Mqtt3ConnectBuilder.Nested
        public P applyConnect() {
            return this.parentConsumer.apply(build());
        }
    }

    public static class Send<P> extends Mqtt3ConnectViewBuilder<Send<P>> implements Mqtt3ConnectBuilder.Send<P> {
        private final Function<? super Mqtt3ConnectView, P> parentConsumer;

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.hivemq.client.internal.mqtt.message.connect.mqtt3.Mqtt3ConnectViewBuilder
        public Send<P> self() {
            return this;
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.connect.Mqtt3ConnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3ConnectBuilderBase cleanSession(boolean z) {
            return (Mqtt3ConnectBuilderBase) super.cleanSession(z);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.connect.Mqtt3ConnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3ConnectBuilderBase keepAlive(int i) {
            return (Mqtt3ConnectBuilderBase) super.keepAlive(i);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.connect.Mqtt3ConnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3ConnectBuilderBase noKeepAlive() {
            return (Mqtt3ConnectBuilderBase) super.noKeepAlive();
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.connect.Mqtt3ConnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3ConnectBuilderBase restrictions(Mqtt3ConnectRestrictions mqtt3ConnectRestrictions) {
            return (Mqtt3ConnectBuilderBase) super.restrictions(mqtt3ConnectRestrictions);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.connect.Mqtt3ConnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3ConnectRestrictionsBuilder.Nested restrictions() {
            return super.restrictions();
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.connect.Mqtt3ConnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3SimpleAuthBuilder.Nested simpleAuth() {
            return super.simpleAuth();
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.connect.Mqtt3ConnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3ConnectBuilderBase simpleAuth(Mqtt3SimpleAuth mqtt3SimpleAuth) {
            return (Mqtt3ConnectBuilderBase) super.simpleAuth(mqtt3SimpleAuth);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.connect.Mqtt3ConnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3ConnectBuilderBase willPublish(Mqtt3Publish mqtt3Publish) {
            return (Mqtt3ConnectBuilderBase) super.willPublish(mqtt3Publish);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.connect.Mqtt3ConnectBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3WillPublishBuilder.Nested willPublish() {
            return super.willPublish();
        }

        public Send(Function<? super Mqtt3ConnectView, P> function) {
            this.parentConsumer = function;
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.connect.Mqtt3ConnectBuilder.Send
        public P send() {
            return this.parentConsumer.apply(build());
        }
    }
}
