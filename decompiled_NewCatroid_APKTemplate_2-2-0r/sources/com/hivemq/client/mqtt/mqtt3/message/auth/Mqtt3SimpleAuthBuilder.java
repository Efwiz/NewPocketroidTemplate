package com.hivemq.client.mqtt.mqtt3.message.auth;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt3SimpleAuthBuilder extends Mqtt3SimpleAuthBuilderBase<Complete> {

    public interface Complete extends Mqtt3SimpleAuthBuilder, Mqtt3SimpleAuthBuilderBase.Complete<Complete> {
        Mqtt3SimpleAuth build();
    }

    public interface Nested<P> extends Mqtt3SimpleAuthBuilderBase<Complete<P>> {

        public interface Complete<P> extends Nested<P>, Mqtt3SimpleAuthBuilderBase.Complete<Complete<P>> {
            P applySimpleAuth();
        }
    }
}
