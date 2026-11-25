package com.hivemq.client.mqtt.mqtt5.message.connect;

import com.hivemq.client.internal.mqtt.message.connect.MqttConnectBuilder;
import com.hivemq.client.mqtt.mqtt5.auth.Mqtt5EnhancedAuthMechanism;
import com.hivemq.client.mqtt.mqtt5.datatypes.Mqtt5UserProperties;
import com.hivemq.client.mqtt.mqtt5.message.Mqtt5Message;
import com.hivemq.client.mqtt.mqtt5.message.Mqtt5MessageType;
import com.hivemq.client.mqtt.mqtt5.message.auth.Mqtt5SimpleAuth;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5WillPublish;
import java.util.Optional;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt5Connect extends Mqtt5Message {
    public static final boolean DEFAULT_CLEAN_START = true;
    public static final int DEFAULT_KEEP_ALIVE = 60;
    public static final long DEFAULT_SESSION_EXPIRY_INTERVAL = 0;
    public static final int NO_KEEP_ALIVE = 0;
    public static final long NO_SESSION_EXPIRY = 4294967295L;

    Mqtt5ConnectBuilder extend();

    Optional<Mqtt5EnhancedAuthMechanism> getEnhancedAuthMechanism();

    int getKeepAlive();

    Mqtt5ConnectRestrictions getRestrictions();

    long getSessionExpiryInterval();

    Optional<Mqtt5SimpleAuth> getSimpleAuth();

    Mqtt5UserProperties getUserProperties();

    Optional<Mqtt5WillPublish> getWillPublish();

    boolean isCleanStart();

    static Mqtt5ConnectBuilder builder() {
        return new MqttConnectBuilder.Default();
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.Mqtt5Message
    default Mqtt5MessageType getType() {
        return Mqtt5MessageType.CONNECT;
    }
}
