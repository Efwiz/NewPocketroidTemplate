package com.hivemq.client.internal.mqtt.handler.publish.incoming;

import com.hivemq.client.internal.mqtt.datatypes.MqttTopicFilterImpl;
import com.hivemq.client.internal.mqtt.ioc.ClientScope;
import com.hivemq.client.internal.mqtt.message.subscribe.MqttSubscribe;
import com.hivemq.client.internal.mqtt.message.subscribe.MqttSubscription;
import com.hivemq.client.internal.mqtt.message.unsubscribe.MqttUnsubscribe;
import com.hivemq.client.internal.mqtt.message.unsubscribe.unsuback.mqtt3.Mqtt3UnsubAckView;
import com.hivemq.client.internal.util.collections.HandleList;
import com.hivemq.client.internal.util.collections.ImmutableList;
import com.hivemq.client.mqtt.MqttGlobalPublishFilter;
import com.hivemq.client.mqtt.mqtt5.message.subscribe.suback.Mqtt5SubAckReasonCode;
import com.hivemq.client.mqtt.mqtt5.message.unsubscribe.unsuback.Mqtt5UnsubAckReasonCode;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;

/* JADX INFO: loaded from: classes10.dex */
@ClientScope
public class MqttIncomingPublishFlows {
    private final MqttSubscribedPublishFlows subscribedFlows = new MqttSubscribedPublishFlowTree();
    private final HandleList<MqttGlobalIncomingPublishFlow>[] globalFlows = new HandleList[MqttGlobalPublishFilter.values().length];

    @Inject
    MqttIncomingPublishFlows() {
    }

    public void subscribe(MqttSubscribe mqttSubscribe, int i, MqttSubscribedPublishFlow mqttSubscribedPublishFlow) {
        ImmutableList<MqttSubscription> subscriptions = mqttSubscribe.getSubscriptions();
        for (int i2 = 0; i2 < subscriptions.size(); i2++) {
            this.subscribedFlows.subscribe(subscriptions.get(i2), i, mqttSubscribedPublishFlow);
        }
    }

    public void subAck(MqttSubscribe mqttSubscribe, int i, ImmutableList<Mqtt5SubAckReasonCode> immutableList) {
        ImmutableList<MqttSubscription> subscriptions = mqttSubscribe.getSubscriptions();
        boolean z = subscriptions.size() > immutableList.size();
        for (int i2 = 0; i2 < subscriptions.size(); i2++) {
            this.subscribedFlows.suback(subscriptions.get(i2).getTopicFilter(), i, z || immutableList.get(i2).isError());
        }
    }

    public void unsubscribe(MqttUnsubscribe mqttUnsubscribe, ImmutableList<Mqtt5UnsubAckReasonCode> immutableList) {
        ImmutableList<MqttTopicFilterImpl> topicFilters = mqttUnsubscribe.getTopicFilters();
        boolean z = immutableList == Mqtt3UnsubAckView.REASON_CODES_ALL_SUCCESS;
        for (int i = 0; i < topicFilters.size(); i++) {
            if (z || !immutableList.get(i).isError()) {
                this.subscribedFlows.unsubscribe(topicFilters.get(i));
            }
        }
    }

    void cancel(MqttSubscribedPublishFlow mqttSubscribedPublishFlow) {
        this.subscribedFlows.cancel(mqttSubscribedPublishFlow);
    }

    public void subscribeGlobal(MqttGlobalIncomingPublishFlow mqttGlobalIncomingPublishFlow) {
        int iOrdinal = mqttGlobalIncomingPublishFlow.getFilter().ordinal();
        HandleList<MqttGlobalIncomingPublishFlow> handleList = this.globalFlows[iOrdinal];
        if (handleList == null) {
            handleList = new HandleList<>();
            this.globalFlows[iOrdinal] = handleList;
        }
        mqttGlobalIncomingPublishFlow.setHandle(handleList.add(mqttGlobalIncomingPublishFlow));
    }

    void cancelGlobal(MqttGlobalIncomingPublishFlow mqttGlobalIncomingPublishFlow) {
        int iOrdinal = mqttGlobalIncomingPublishFlow.getFilter().ordinal();
        HandleList<MqttGlobalIncomingPublishFlow> handleList = this.globalFlows[iOrdinal];
        HandleList.Handle<MqttGlobalIncomingPublishFlow> handle = mqttGlobalIncomingPublishFlow.getHandle();
        if (handleList == null || handle == null) {
            return;
        }
        handleList.remove(handle);
        if (handleList.isEmpty()) {
            this.globalFlows[iOrdinal] = null;
        }
    }

    void findMatching(MqttStatefulPublishWithFlows mqttStatefulPublishWithFlows) {
        this.subscribedFlows.findMatching(mqttStatefulPublishWithFlows);
        if (mqttStatefulPublishWithFlows.subscriptionFound) {
            add(mqttStatefulPublishWithFlows, this.globalFlows[MqttGlobalPublishFilter.SUBSCRIBED.ordinal()]);
        } else {
            add(mqttStatefulPublishWithFlows, this.globalFlows[MqttGlobalPublishFilter.UNSOLICITED.ordinal()]);
        }
        add(mqttStatefulPublishWithFlows, this.globalFlows[MqttGlobalPublishFilter.ALL.ordinal()]);
        if (mqttStatefulPublishWithFlows.isEmpty()) {
            add(mqttStatefulPublishWithFlows, this.globalFlows[MqttGlobalPublishFilter.REMAINING.ordinal()]);
        }
    }

    private static void add(MqttStatefulPublishWithFlows mqttStatefulPublishWithFlows, HandleList<MqttGlobalIncomingPublishFlow> handleList) {
        if (handleList == null) {
            return;
        }
        Object first = handleList.getFirst();
        while (true) {
            HandleList.Handle handle = (HandleList.Handle) first;
            if (handle == null) {
                return;
            }
            mqttStatefulPublishWithFlows.add((MqttIncomingPublishFlow) handle.getElement());
            first = handle.getNext();
        }
    }

    public void clear(Throwable th) {
        this.subscribedFlows.clear(th);
        int i = 0;
        while (true) {
            HandleList<MqttGlobalIncomingPublishFlow>[] handleListArr = this.globalFlows;
            if (i >= handleListArr.length) {
                return;
            }
            HandleList<MqttGlobalIncomingPublishFlow> handleList = handleListArr[i];
            if (handleList != null) {
                Object first = handleList.getFirst();
                while (true) {
                    HandleList.Handle handle = (HandleList.Handle) first;
                    if (handle != null) {
                        ((MqttGlobalIncomingPublishFlow) handle.getElement()).onError(th);
                        first = handle.getNext();
                    }
                }
            }
            this.globalFlows[i] = null;
            i++;
        }
    }

    public Map<Integer, List<MqttSubscription>> getSubscriptions() {
        return this.subscribedFlows.getSubscriptions();
    }
}
