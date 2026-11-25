package com.hivemq.client.mqtt.mqtt3.message.connect;

import com.hivemq.client.mqtt.mqtt3.message.auth.Mqtt3SimpleAuth;
import com.hivemq.client.mqtt.mqtt3.message.auth.Mqtt3SimpleAuthBuilder;
import com.hivemq.client.mqtt.mqtt3.message.connect.Mqtt3ConnectBuilderBase;
import com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3Publish;
import com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3WillPublishBuilder;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt3ConnectBuilderBase<B extends Mqtt3ConnectBuilderBase<B>> {
    B cleanSession(boolean z);

    B keepAlive(int i);

    B noKeepAlive();

    B restrictions(Mqtt3ConnectRestrictions mqtt3ConnectRestrictions);

    Mqtt3ConnectRestrictionsBuilder.Nested<? extends B> restrictions();

    Mqtt3SimpleAuthBuilder.Nested<? extends B> simpleAuth();

    B simpleAuth(Mqtt3SimpleAuth mqtt3SimpleAuth);

    B willPublish(Mqtt3Publish mqtt3Publish);

    Mqtt3WillPublishBuilder.Nested<? extends B> willPublish();
}
