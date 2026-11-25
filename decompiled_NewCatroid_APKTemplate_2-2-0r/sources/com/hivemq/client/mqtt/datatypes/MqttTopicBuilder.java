package com.hivemq.client.mqtt.datatypes;

/* JADX INFO: loaded from: classes10.dex */
public interface MqttTopicBuilder extends MqttTopicBuilderBase<Complete> {

    public interface Complete extends MqttTopicBuilder, MqttTopicBuilderBase<Complete> {
        MqttTopic build();
    }

    public interface Nested<P> extends MqttTopicBuilderBase<Complete<P>> {

        public interface Complete<P> extends Nested<P>, MqttTopicBuilderBase<Complete<P>> {
            P applyTopic();
        }
    }

    MqttTopicFilterBuilder filter();

    MqttSharedTopicFilterBuilder share(String str);
}
