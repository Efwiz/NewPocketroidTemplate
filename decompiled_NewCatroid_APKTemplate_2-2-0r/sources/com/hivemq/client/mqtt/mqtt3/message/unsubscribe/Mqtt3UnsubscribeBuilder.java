package com.hivemq.client.mqtt.mqtt3.message.unsubscribe;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt3UnsubscribeBuilder extends Mqtt3UnsubscribeBuilderBase<Complete> {

    public interface Complete extends Mqtt3UnsubscribeBuilder, Mqtt3UnsubscribeBuilderBase<Complete> {
        Mqtt3Unsubscribe build();
    }

    public interface Nested<P> extends Mqtt3UnsubscribeBuilderBase<Complete<P>> {

        public interface Complete<P> extends Nested<P>, Mqtt3UnsubscribeBuilderBase<Complete<P>> {
            P applyUnsubscribe();
        }

        public interface Start<P> extends Nested<P>, Mqtt3UnsubscribeBuilderBase.Start<Complete<P>> {
        }
    }

    public interface Send<P> extends Mqtt3UnsubscribeBuilderBase<Complete<P>> {

        public interface Complete<P> extends Send<P>, Mqtt3UnsubscribeBuilderBase<Complete<P>> {
            P send();
        }

        public interface Start<P> extends Send<P>, Mqtt3UnsubscribeBuilderBase.Start<Complete<P>> {
        }
    }

    public interface SendVoid extends Mqtt3UnsubscribeBuilderBase<Complete> {

        public interface Complete extends SendVoid, Mqtt3UnsubscribeBuilderBase<Complete> {
            void send();
        }

        public interface Start extends SendVoid, Mqtt3UnsubscribeBuilderBase.Start<Complete> {
        }
    }

    public interface Start extends Mqtt3UnsubscribeBuilder, Mqtt3UnsubscribeBuilderBase.Start<Complete> {
    }
}
