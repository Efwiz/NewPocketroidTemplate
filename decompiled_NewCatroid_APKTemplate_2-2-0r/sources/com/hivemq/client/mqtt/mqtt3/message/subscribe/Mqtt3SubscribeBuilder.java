package com.hivemq.client.mqtt.mqtt3.message.subscribe;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt3SubscribeBuilder extends Mqtt3SubscribeBuilderBase<Complete> {

    public interface Complete extends Mqtt3SubscribeBuilder, Mqtt3SubscribeBuilderBase<Complete> {
        Mqtt3Subscribe build();
    }

    public interface Nested<P> extends Mqtt3SubscribeBuilderBase<Complete<P>> {

        public interface Complete<P> extends Nested<P>, Mqtt3SubscribeBuilderBase<Complete<P>> {
            P applySubscribe();
        }

        public interface Start<P> extends Nested<P>, Mqtt3SubscribeBuilderBase.Start<Complete<P>, Complete<P>> {

            public interface Complete<P> extends Start<P>, Complete<P>, Mqtt3SubscribeBuilderBase.Start.Complete<Complete<P>, Complete<P>> {
            }
        }
    }

    public interface Publishes<P> extends Mqtt3SubscribeBuilderBase<Complete<P>> {

        public interface Args<P> {
            P applySubscribe();

            Args<P> manualAcknowledgement(boolean z);
        }

        public interface Complete<P> extends Publishes<P>, Args<P>, Mqtt3SubscribeBuilderBase<Complete<P>> {
        }

        public interface Start<P> extends Publishes<P>, Mqtt3SubscribeBuilderBase.Start<Complete<P>, Complete<P>> {

            public interface Complete<P> extends Start<P>, Complete<P>, Mqtt3SubscribeBuilderBase.Start.Complete<Complete<P>, Complete<P>> {
            }
        }
    }

    public interface Send<P> extends Mqtt3SubscribeBuilderBase<Complete<P>> {

        public interface Complete<P> extends Send<P>, Mqtt3SubscribeBuilderBase<Complete<P>> {
            P send();
        }

        public interface Start<P> extends Send<P>, Mqtt3SubscribeBuilderBase.Start<Complete<P>, Complete<P>> {

            public interface Complete<P> extends Start<P>, Complete<P>, Mqtt3SubscribeBuilderBase.Start.Complete<Complete<P>, Complete<P>> {
            }
        }
    }

    public interface Start extends Mqtt3SubscribeBuilder, Mqtt3SubscribeBuilderBase.Start<Complete, Complete> {

        public interface Complete extends Start, Complete, Mqtt3SubscribeBuilderBase.Start.Complete<Complete, Complete> {
        }
    }
}
