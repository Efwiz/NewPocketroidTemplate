package com.hivemq.client.mqtt.mqtt3.message.connect;

import com.hivemq.client.internal.mqtt.message.connect.mqtt3.Mqtt3ConnectViewBuilder;
import com.hivemq.client.mqtt.mqtt3.message.Mqtt3Message;
import com.hivemq.client.mqtt.mqtt3.message.Mqtt3MessageType;
import com.hivemq.client.mqtt.mqtt3.message.auth.Mqtt3SimpleAuth;
import com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3Publish;
import java.util.Optional;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt3Connect extends Mqtt3Message {
    public static final boolean DEFAULT_CLEAN_SESSION = true;
    public static final int DEFAULT_KEEP_ALIVE = 60;
    public static final int NO_KEEP_ALIVE = 0;

    Mqtt3ConnectBuilder extend();

    int getKeepAlive();

    Mqtt3ConnectRestrictions getRestrictions();

    Optional<Mqtt3SimpleAuth> getSimpleAuth();

    Optional<Mqtt3Publish> getWillPublish();

    boolean isCleanSession();

    static Mqtt3ConnectBuilder builder() {
        return new Mqtt3ConnectViewBuilder.Default();
    }

    @Override // com.hivemq.client.mqtt.mqtt3.message.Mqtt3Message
    default Mqtt3MessageType getType() {
        return Mqtt3MessageType.CONNECT;
    }
}
