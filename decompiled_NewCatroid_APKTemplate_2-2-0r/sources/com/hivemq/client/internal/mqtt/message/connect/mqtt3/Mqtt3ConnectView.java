package com.hivemq.client.internal.mqtt.message.connect.mqtt3;

import com.hivemq.client.internal.mqtt.datatypes.MqttUserPropertiesImpl;
import com.hivemq.client.internal.mqtt.message.auth.MqttSimpleAuth;
import com.hivemq.client.internal.mqtt.message.auth.mqtt3.Mqtt3SimpleAuthView;
import com.hivemq.client.internal.mqtt.message.connect.MqttConnect;
import com.hivemq.client.internal.mqtt.message.connect.MqttConnectRestrictions;
import com.hivemq.client.internal.mqtt.message.publish.MqttPublish;
import com.hivemq.client.internal.mqtt.message.publish.MqttWillPublish;
import com.hivemq.client.internal.mqtt.message.publish.mqtt3.Mqtt3PublishView;
import com.hivemq.client.mqtt.mqtt3.message.auth.Mqtt3SimpleAuth;
import com.hivemq.client.mqtt.mqtt3.message.connect.Mqtt3Connect;
import com.hivemq.client.mqtt.mqtt3.message.connect.Mqtt3ConnectRestrictions;
import com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3Publish;
import java.util.Optional;

/* JADX INFO: loaded from: classes10.dex */
public class Mqtt3ConnectView implements Mqtt3Connect {
    public static final Mqtt3ConnectView DEFAULT = of(60, true, MqttConnectRestrictions.DEFAULT, null, null);
    private final MqttConnect delegate;

    private static MqttConnect delegate(int i, boolean z, MqttConnectRestrictions mqttConnectRestrictions, MqttSimpleAuth mqttSimpleAuth, MqttWillPublish mqttWillPublish) {
        return new MqttConnect(i, z, z ? 0L : 4294967295L, mqttConnectRestrictions, mqttSimpleAuth, null, mqttWillPublish, MqttUserPropertiesImpl.NO_USER_PROPERTIES);
    }

    static Mqtt3ConnectView of(int i, boolean z, MqttConnectRestrictions mqttConnectRestrictions, MqttSimpleAuth mqttSimpleAuth, MqttWillPublish mqttWillPublish) {
        return new Mqtt3ConnectView(delegate(i, z, mqttConnectRestrictions, mqttSimpleAuth, mqttWillPublish));
    }

    public static Mqtt3ConnectView of(MqttConnect mqttConnect) {
        return new Mqtt3ConnectView(mqttConnect);
    }

    private Mqtt3ConnectView(MqttConnect mqttConnect) {
        this.delegate = mqttConnect;
    }

    @Override // com.hivemq.client.mqtt.mqtt3.message.connect.Mqtt3Connect
    public int getKeepAlive() {
        return this.delegate.getKeepAlive();
    }

    @Override // com.hivemq.client.mqtt.mqtt3.message.connect.Mqtt3Connect
    public boolean isCleanSession() {
        return this.delegate.isCleanStart();
    }

    @Override // com.hivemq.client.mqtt.mqtt3.message.connect.Mqtt3Connect
    public Mqtt3ConnectRestrictions getRestrictions() {
        return this.delegate.getRestrictions();
    }

    @Override // com.hivemq.client.mqtt.mqtt3.message.connect.Mqtt3Connect
    public Optional<Mqtt3SimpleAuth> getSimpleAuth() {
        return Optional.ofNullable(getRawSimpleAuth());
    }

    private Mqtt3SimpleAuth getRawSimpleAuth() {
        MqttSimpleAuth rawSimpleAuth = this.delegate.getRawSimpleAuth();
        if (rawSimpleAuth == null) {
            return null;
        }
        return Mqtt3SimpleAuthView.of(rawSimpleAuth);
    }

    @Override // com.hivemq.client.mqtt.mqtt3.message.connect.Mqtt3Connect
    public Optional<Mqtt3Publish> getWillPublish() {
        return Optional.ofNullable(getRawWillPublish());
    }

    private Mqtt3Publish getRawWillPublish() {
        MqttWillPublish rawWillPublish = this.delegate.getRawWillPublish();
        if (rawWillPublish == null) {
            return null;
        }
        return Mqtt3PublishView.of((MqttPublish) rawWillPublish);
    }

    public MqttConnect getDelegate() {
        return this.delegate;
    }

    @Override // com.hivemq.client.mqtt.mqtt3.message.connect.Mqtt3Connect
    public Mqtt3ConnectViewBuilder.Default extend() {
        return new Mqtt3ConnectViewBuilder.Default(this);
    }

    private String toAttributeString() {
        Mqtt3SimpleAuth rawSimpleAuth = getRawSimpleAuth();
        Mqtt3Publish rawWillPublish = getRawWillPublish();
        return "keepAlive=" + getKeepAlive() + ", cleanSession=" + isCleanSession() + ", restrictions=" + getRestrictions() + (rawSimpleAuth == null ? "" : ", simpleAuth=" + rawSimpleAuth) + (rawWillPublish != null ? ", willPublish=" + rawWillPublish : "");
    }

    public String toString() {
        return "MqttConnect{" + toAttributeString() + '}';
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Mqtt3ConnectView) {
            return this.delegate.equals(((Mqtt3ConnectView) obj).delegate);
        }
        return false;
    }

    public int hashCode() {
        return this.delegate.hashCode();
    }
}
