package com.hivemq.client.mqtt.mqtt5.message.unsubscribe;

import com.hivemq.client.mqtt.datatypes.MqttTopicFilter;
import com.hivemq.client.mqtt.datatypes.MqttTopicFilterBuilder;
import com.hivemq.client.mqtt.mqtt5.datatypes.Mqtt5UserProperties;
import com.hivemq.client.mqtt.mqtt5.datatypes.Mqtt5UserPropertiesBuilder;
import com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5Subscribe;
import com.hivemq.client.mqtt.mqtt5.message.unsubscribe.Mqtt5UnsubscribeBuilderBase.Complete;
import java.util.Collection;
import java.util.stream.Stream;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt5UnsubscribeBuilderBase<C extends Complete<C>> {

    public interface Complete<C extends Complete<C>> extends Mqtt5UnsubscribeBuilderBase<C> {
        Mqtt5UserPropertiesBuilder.Nested<? extends C> userProperties();

        C userProperties(Mqtt5UserProperties mqtt5UserProperties);
    }

    public interface Start<C extends Complete<C>> extends Mqtt5UnsubscribeBuilderBase<C> {
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

    C reverse(Mqtt5Subscribe mqtt5Subscribe);
}
