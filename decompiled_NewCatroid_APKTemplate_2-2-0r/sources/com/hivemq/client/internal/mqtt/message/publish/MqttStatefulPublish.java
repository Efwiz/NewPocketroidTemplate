package com.hivemq.client.internal.mqtt.message.publish;

import com.hivemq.client.internal.mqtt.message.MqttStatefulMessage;
import com.hivemq.client.internal.util.collections.ImmutableIntList;

/* JADX INFO: loaded from: classes10.dex */
public class MqttStatefulPublish extends MqttStatefulMessage.WithId<MqttPublish> {
    public static final ImmutableIntList DEFAULT_NO_SUBSCRIPTION_IDENTIFIERS = ImmutableIntList.of();
    public static final int DEFAULT_NO_TOPIC_ALIAS = 0;
    public static final int NO_PACKET_IDENTIFIER_QOS_0 = -1;
    public static final int TOPIC_ALIAS_FLAG = 65535;
    public static final int TOPIC_ALIAS_FLAG_NEW = 65536;
    private final boolean dup;
    private final ImmutableIntList subscriptionIdentifiers;
    private final int topicAlias;

    MqttStatefulPublish(MqttPublish mqttPublish, int i, boolean z, int i2, ImmutableIntList immutableIntList) {
        super(mqttPublish, i);
        this.dup = z;
        this.topicAlias = i2;
        this.subscriptionIdentifiers = immutableIntList;
    }

    public boolean isDup() {
        return this.dup;
    }

    public int getTopicAlias() {
        return this.topicAlias & 65535;
    }

    public boolean isNewTopicAlias() {
        return (this.topicAlias & 65536) != 0;
    }

    public ImmutableIntList getSubscriptionIdentifiers() {
        return this.subscriptionIdentifiers;
    }

    @Override // com.hivemq.client.internal.mqtt.message.MqttStatefulMessage.WithId, com.hivemq.client.internal.mqtt.message.MqttStatefulMessage
    protected String toAttributeString() {
        return super.toAttributeString() + ", dup=" + this.dup + ", topicAlias=" + this.topicAlias + ", subscriptionIdentifiers=" + this.subscriptionIdentifiers;
    }

    public String toString() {
        return "MqttStatefulPublish{" + toAttributeString() + '}';
    }
}
