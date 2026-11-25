package com.hivemq.client.internal.mqtt.handler.publish.incoming;

import com.hivemq.client.internal.mqtt.datatypes.MqttTopicFilterImpl;
import com.hivemq.client.internal.mqtt.message.subscribe.MqttSubscription;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes10.dex */
public interface MqttSubscribedPublishFlows {
    void cancel(MqttSubscribedPublishFlow mqttSubscribedPublishFlow);

    void clear(Throwable th);

    void findMatching(MqttStatefulPublishWithFlows mqttStatefulPublishWithFlows);

    Map<Integer, List<MqttSubscription>> getSubscriptions();

    void suback(MqttTopicFilterImpl mqttTopicFilterImpl, int i, boolean z);

    void subscribe(MqttSubscription mqttSubscription, int i, MqttSubscribedPublishFlow mqttSubscribedPublishFlow);

    void unsubscribe(MqttTopicFilterImpl mqttTopicFilterImpl);
}
