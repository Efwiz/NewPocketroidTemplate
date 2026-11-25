package com.hivemq.client.mqtt.mqtt3.message.unsubscribe;

import com.hivemq.client.mqtt.datatypes.MqttTopicFilter;
import com.hivemq.client.mqtt.datatypes.MqttTopicFilterBuilder;
import com.hivemq.client.mqtt.mqtt3.message.subscribe.Mqtt3Subscribe;
import com.hivemq.client.mqtt.mqtt3.message.unsubscribe.Mqtt3UnsubscribeBuilderBase;
import java.util.Collection;
import java.util.stream.Stream;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt3UnsubscribeBuilderBase<C extends Mqtt3UnsubscribeBuilderBase<C>> {

    public interface Start<C extends Mqtt3UnsubscribeBuilderBase<C>> extends Mqtt3UnsubscribeBuilderBase<C> {
        MqttTopicFilterBuilder.Nested<? extends C> topicFilter();

        C topicFilter(MqttTopicFilter mqttTopicFilter);

        C topicFilter(String str);
    }

    MqttTopicFilterBuilder.Nested<? extends C> addTopicFilter();

    C addTopicFilter(MqttTopicFilter mqttTopicFilter);

    C addTopicFilter(String str);

    C addTopicFilters(Collection<? extends MqttTopicFilter> collection);

    C addTopicFilters(Stream<? extends MqttTopicFilter> stream);

    C addTopicFilters(MqttTopicFilter... mqttTopicFilterArr);

    C reverse(Mqtt3Subscribe mqtt3Subscribe);
}
