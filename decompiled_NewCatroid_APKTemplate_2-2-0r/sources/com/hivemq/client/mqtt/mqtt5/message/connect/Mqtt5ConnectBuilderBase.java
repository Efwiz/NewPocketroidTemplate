package com.hivemq.client.mqtt.mqtt5.message.connect;

import com.hivemq.client.mqtt.mqtt5.auth.Mqtt5EnhancedAuthMechanism;
import com.hivemq.client.mqtt.mqtt5.datatypes.Mqtt5UserProperties;
import com.hivemq.client.mqtt.mqtt5.datatypes.Mqtt5UserPropertiesBuilder;
import com.hivemq.client.mqtt.mqtt5.message.auth.Mqtt5SimpleAuth;
import com.hivemq.client.mqtt.mqtt5.message.auth.Mqtt5SimpleAuthBuilder;
import com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5ConnectBuilderBase;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5WillPublishBuilder;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt5ConnectBuilderBase<B extends Mqtt5ConnectBuilderBase<B>> {
    B cleanStart(boolean z);

    B enhancedAuth(Mqtt5EnhancedAuthMechanism mqtt5EnhancedAuthMechanism);

    B keepAlive(int i);

    B noKeepAlive();

    B noSessionExpiry();

    B restrictions(Mqtt5ConnectRestrictions mqtt5ConnectRestrictions);

    Mqtt5ConnectRestrictionsBuilder.Nested<? extends B> restrictions();

    B sessionExpiryInterval(long j);

    Mqtt5SimpleAuthBuilder.Nested<? extends B> simpleAuth();

    B simpleAuth(Mqtt5SimpleAuth mqtt5SimpleAuth);

    Mqtt5UserPropertiesBuilder.Nested<? extends B> userProperties();

    B userProperties(Mqtt5UserProperties mqtt5UserProperties);

    B willPublish(Mqtt5Publish mqtt5Publish);

    Mqtt5WillPublishBuilder.Nested<? extends B> willPublish();
}
