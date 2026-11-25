package com.hivemq.client.mqtt.mqtt5.message.auth;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt5SimpleAuthBuilder extends Mqtt5SimpleAuthBuilderBase<Complete> {

    public interface Complete extends Mqtt5SimpleAuthBuilder, Mqtt5SimpleAuthBuilderBase<Complete> {
        Mqtt5SimpleAuth build();
    }

    public interface Nested<P> extends Mqtt5SimpleAuthBuilderBase<Complete<P>> {

        public interface Complete<P> extends Nested<P>, Mqtt5SimpleAuthBuilderBase<Complete<P>> {
            P applySimpleAuth();
        }
    }
}
