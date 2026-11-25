package com.hivemq.client.mqtt.mqtt5.advanced.interceptor.qos2;

import com.hivemq.client.mqtt.mqtt5.Mqtt5ClientConfig;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish;
import com.hivemq.client.mqtt.mqtt5.message.publish.pubcomp.Mqtt5PubComp;
import com.hivemq.client.mqtt.mqtt5.message.publish.pubrec.Mqtt5PubRec;
import com.hivemq.client.mqtt.mqtt5.message.publish.pubrel.Mqtt5PubRel;
import com.hivemq.client.mqtt.mqtt5.message.publish.pubrel.Mqtt5PubRelBuilder;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt5OutgoingQos2Interceptor {
    void onPubComp(Mqtt5ClientConfig mqtt5ClientConfig, Mqtt5PubRel mqtt5PubRel, Mqtt5PubComp mqtt5PubComp);

    void onPubRec(Mqtt5ClientConfig mqtt5ClientConfig, Mqtt5Publish mqtt5Publish, Mqtt5PubRec mqtt5PubRec, Mqtt5PubRelBuilder mqtt5PubRelBuilder);

    void onPubRecError(Mqtt5ClientConfig mqtt5ClientConfig, Mqtt5Publish mqtt5Publish, Mqtt5PubRec mqtt5PubRec);
}
