package com.hivemq.client.mqtt.mqtt3.message.publish;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt3PublishBuilder extends Mqtt3PublishBuilderBase<Complete> {

    public interface Complete extends Mqtt3PublishBuilder, Mqtt3PublishBuilderBase.Complete<Complete> {
        Mqtt3Publish build();
    }

    public interface Nested<P> extends Mqtt3PublishBuilderBase<Complete<P>> {

        public interface Complete<P> extends Nested<P>, Mqtt3PublishBuilderBase.Complete<Complete<P>> {
            P applyPublish();
        }
    }

    public interface Send<P> extends Mqtt3PublishBuilderBase<Complete<P>> {

        public interface Complete<P> extends Send<P>, Mqtt3PublishBuilderBase.Complete<Complete<P>> {
            P send();
        }
    }

    public interface SendVoid extends Mqtt3PublishBuilderBase<Complete> {

        public interface Complete extends SendVoid, Mqtt3PublishBuilderBase.Complete<Complete> {
            void send();
        }
    }
}
