package com.hivemq.client.mqtt.datatypes;

/* JADX INFO: loaded from: classes10.dex */
public interface MqttTopicFilterBuilder extends MqttTopicFilterBuilderBase<Complete, End, MqttSharedTopicFilterBuilder, MqttSharedTopicFilterBuilder.Complete, MqttSharedTopicFilterBuilder.End> {

    public interface Complete extends MqttTopicFilterBuilder, End, MqttTopicFilterBuilderBase.Complete<Complete, End, MqttSharedTopicFilterBuilder, MqttSharedTopicFilterBuilder.Complete, MqttSharedTopicFilterBuilder.End> {
    }

    public interface End extends MqttTopicFilterBuilderBase.End {
        MqttTopicFilter build();
    }

    public interface Nested<P> extends MqttTopicFilterBuilderBase<Complete<P>, End<P>, MqttSharedTopicFilterBuilder.Nested<P>, MqttSharedTopicFilterBuilder.Nested.Complete<P>, MqttSharedTopicFilterBuilder.Nested.End<P>> {

        public interface Complete<P> extends Nested<P>, End<P>, MqttTopicFilterBuilderBase.Complete<Complete<P>, End<P>, MqttSharedTopicFilterBuilder.Nested<P>, MqttSharedTopicFilterBuilder.Nested.Complete<P>, MqttSharedTopicFilterBuilder.Nested.End<P>> {
        }

        public interface End<P> extends MqttTopicFilterBuilderBase.End {
            P applyTopicFilter();
        }
    }
}
