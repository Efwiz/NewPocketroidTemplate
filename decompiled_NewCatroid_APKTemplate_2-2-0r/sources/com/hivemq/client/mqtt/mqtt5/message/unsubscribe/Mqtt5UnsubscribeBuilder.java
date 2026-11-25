package com.hivemq.client.mqtt.mqtt5.message.unsubscribe;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt5UnsubscribeBuilder extends Mqtt5UnsubscribeBuilderBase<Complete> {

    public interface Complete extends Mqtt5UnsubscribeBuilder, Mqtt5UnsubscribeBuilderBase.Complete<Complete> {
        Mqtt5Unsubscribe build();
    }

    public interface Nested<P> extends Mqtt5UnsubscribeBuilderBase<Complete<P>> {

        public interface Complete<P> extends Nested<P>, Mqtt5UnsubscribeBuilderBase.Complete<Complete<P>> {
            P applyUnsubscribe();
        }

        public interface Start<P> extends Nested<P>, Mqtt5UnsubscribeBuilderBase.Start<Complete<P>> {
        }
    }

    public interface Send<P> extends Mqtt5UnsubscribeBuilderBase<Complete<P>> {

        public interface Complete<P> extends Send<P>, Mqtt5UnsubscribeBuilderBase.Complete<Complete<P>> {
            P send();
        }

        public interface Start<P> extends Send<P>, Mqtt5UnsubscribeBuilderBase.Start<Complete<P>> {
        }
    }

    public interface Start extends Mqtt5UnsubscribeBuilder, Mqtt5UnsubscribeBuilderBase.Start<Complete> {
    }
}
