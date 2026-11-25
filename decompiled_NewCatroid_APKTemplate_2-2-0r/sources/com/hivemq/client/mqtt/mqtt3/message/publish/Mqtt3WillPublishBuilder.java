package com.hivemq.client.mqtt.mqtt3.message.publish;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt3WillPublishBuilder extends Mqtt3PublishBuilderBase<Complete> {

    public interface Complete extends Mqtt3WillPublishBuilder, Mqtt3PublishBuilderBase.Complete<Complete> {
        Mqtt3Publish build();
    }

    public interface Nested<P> extends Mqtt3PublishBuilderBase<Complete<P>> {

        public interface Complete<P> extends Nested<P>, Mqtt3PublishBuilderBase.Complete<Complete<P>> {
            P applyWillPublish();
        }
    }
}
