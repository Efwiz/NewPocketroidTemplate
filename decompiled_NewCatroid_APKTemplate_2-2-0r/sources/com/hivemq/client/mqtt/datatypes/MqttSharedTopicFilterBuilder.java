package com.hivemq.client.mqtt.datatypes;

/* JADX INFO: loaded from: classes10.dex */
public interface MqttSharedTopicFilterBuilder extends MqttTopicFilterBuilderBase.SharedBase<MqttSharedTopicFilterBuilder, Complete, End> {

    public interface Complete extends MqttSharedTopicFilterBuilder, End, MqttTopicFilterBuilderBase.SharedBase.Complete<MqttSharedTopicFilterBuilder, Complete, End> {
    }

    public interface End extends MqttTopicFilterBuilderBase.End {
        MqttSharedTopicFilter build();
    }

    public interface Nested<P> extends MqttTopicFilterBuilderBase.SharedBase<Nested<P>, Complete<P>, End<P>> {

        public interface Complete<P> extends Nested<P>, End<P>, MqttTopicFilterBuilderBase.SharedBase.Complete<Nested<P>, Complete<P>, End<P>> {
        }

        public interface End<P> extends MqttTopicFilterBuilderBase.End {
            P applyTopicFilter();
        }
    }
}
