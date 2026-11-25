package com.hivemq.client.mqtt.mqtt5;

import com.hivemq.client.mqtt.MqttClientConnectionConfig;
import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.mqtt5.auth.Mqtt5EnhancedAuthMechanism;
import java.util.Optional;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt5ClientConnectionConfig extends MqttClientConnectionConfig {

    public interface RestrictionsForClient {
        boolean areSubscriptionIdentifiersAvailable();

        MqttQos getMaximumQos();

        int getSendMaximum();

        int getSendMaximumPacketSize();

        int getSendTopicAliasMaximum();

        boolean isRetainAvailable();

        boolean isSharedSubscriptionAvailable();

        boolean isWildcardSubscriptionAvailable();
    }

    public interface RestrictionsForServer {
        int getMaximumPacketSize();

        int getReceiveMaximum();

        int getTopicAliasMaximum();

        boolean isProblemInformationRequested();

        boolean isResponseInformationRequested();
    }

    Optional<Mqtt5EnhancedAuthMechanism> getEnhancedAuthMechanism();

    RestrictionsForClient getRestrictionsForClient();

    RestrictionsForServer getRestrictionsForServer();

    long getSessionExpiryInterval();
}
