package com.hivemq.client.mqtt.mqtt5.message.publish;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt5WillPublishBuilder extends Mqtt5PublishBuilderBase.WillBase<Complete> {

    public interface Complete extends Mqtt5WillPublishBuilder, Mqtt5PublishBuilderBase.WillBase.Complete<Complete> {
        Mqtt5WillPublish build();
    }

    public interface Nested<P> extends Mqtt5PublishBuilderBase.WillBase<Complete<P>> {

        public interface Complete<P> extends Nested<P>, Mqtt5PublishBuilderBase.WillBase.Complete<Complete<P>> {
            P applyWillPublish();
        }
    }
}
