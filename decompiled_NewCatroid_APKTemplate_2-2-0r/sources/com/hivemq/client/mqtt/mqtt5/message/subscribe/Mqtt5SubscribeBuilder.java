package com.hivemq.client.mqtt.mqtt5.message.subscribe;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt5SubscribeBuilder extends Mqtt5SubscribeBuilderBase<Complete> {

    public interface Complete extends Mqtt5SubscribeBuilder, Mqtt5SubscribeBuilderBase.Complete<Complete> {
        Mqtt5Subscribe build();
    }

    public interface Nested<P> extends Mqtt5SubscribeBuilderBase<Complete<P>> {

        public interface Complete<P> extends Nested<P>, Mqtt5SubscribeBuilderBase.Complete<Complete<P>> {
            P applySubscribe();
        }

        public interface Start<P> extends Nested<P>, Mqtt5SubscribeBuilderBase.Start<Complete<P>, Complete<P>> {

            public interface Complete<P> extends Start<P>, Complete<P>, Mqtt5SubscribeBuilderBase.Start.Complete<Complete<P>, Complete<P>> {
            }
        }
    }

    public interface Publishes<P> extends Mqtt5SubscribeBuilderBase<Complete<P>> {

        public interface Args<P> {
            P applySubscribe();

            Args<P> manualAcknowledgement(boolean z);
        }

        public interface Complete<P> extends Publishes<P>, Args<P>, Mqtt5SubscribeBuilderBase.Complete<Complete<P>> {
        }

        public interface Start<P> extends Publishes<P>, Mqtt5SubscribeBuilderBase.Start<Complete<P>, Complete<P>> {

            public interface Complete<P> extends Start<P>, Complete<P>, Mqtt5SubscribeBuilderBase.Start.Complete<Complete<P>, Complete<P>> {
            }
        }
    }

    public interface Send<P> extends Mqtt5SubscribeBuilderBase<Complete<P>> {

        public interface Complete<P> extends Send<P>, Mqtt5SubscribeBuilderBase.Complete<Complete<P>> {
            P send();
        }

        public interface Start<P> extends Send<P>, Mqtt5SubscribeBuilderBase.Start<Complete<P>, Complete<P>> {

            public interface Complete<P> extends Start<P>, Complete<P>, Mqtt5SubscribeBuilderBase.Start.Complete<Complete<P>, Complete<P>> {
            }
        }
    }

    public interface Start extends Mqtt5SubscribeBuilder, Mqtt5SubscribeBuilderBase.Start<Complete, Complete> {

        public interface Complete extends Start, Complete, Mqtt5SubscribeBuilderBase.Start.Complete<Complete, Complete> {
        }
    }
}
