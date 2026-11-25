package com.hivemq.client.mqtt.mqtt5.message.publish;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt5PublishBuilder extends Mqtt5PublishBuilderBase<Complete> {

    public interface Complete extends Mqtt5PublishBuilder, Mqtt5PublishBuilderBase.Complete<Complete> {
        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilder
        Mqtt5WillPublishBuilder.Complete asWill();

        Mqtt5Publish build();
    }

    public interface Nested<P> extends Mqtt5PublishBuilderBase<Complete<P>> {

        public interface Complete<P> extends Nested<P>, Mqtt5PublishBuilderBase.Complete<Complete<P>> {
            P applyPublish();
        }
    }

    public interface Send<P> extends Mqtt5PublishBuilderBase<Complete<P>> {

        public interface Complete<P> extends Send<P>, Mqtt5PublishBuilderBase.Complete<Complete<P>> {
            P send();
        }
    }

    Mqtt5WillPublishBuilder asWill();
}
