package com.hivemq.client.internal.mqtt.mqtt3;

import com.hivemq.client.internal.mqtt.MqttClientConfig;
import com.hivemq.client.internal.mqtt.message.auth.MqttSimpleAuth;
import com.hivemq.client.internal.mqtt.message.auth.mqtt3.Mqtt3SimpleAuthView;
import com.hivemq.client.internal.mqtt.message.publish.MqttPublish;
import com.hivemq.client.internal.mqtt.message.publish.MqttWillPublish;
import com.hivemq.client.internal.mqtt.message.publish.mqtt3.Mqtt3PublishView;
import com.hivemq.client.mqtt.MqttClientExecutorConfig;
import com.hivemq.client.mqtt.MqttClientState;
import com.hivemq.client.mqtt.MqttClientTransportConfig;
import com.hivemq.client.mqtt.MqttVersion;
import com.hivemq.client.mqtt.datatypes.MqttClientIdentifier;
import com.hivemq.client.mqtt.lifecycle.MqttClientAutoReconnect;
import com.hivemq.client.mqtt.lifecycle.MqttClientConnectedListener;
import com.hivemq.client.mqtt.lifecycle.MqttClientDisconnectedListener;
import com.hivemq.client.mqtt.mqtt3.Mqtt3ClientConfig;
import com.hivemq.client.mqtt.mqtt3.Mqtt3ClientConnectionConfig;
import com.hivemq.client.mqtt.mqtt3.message.auth.Mqtt3SimpleAuth;
import com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3Publish;
import java.util.List;
import java.util.Optional;

/* JADX INFO: loaded from: classes10.dex */
public class Mqtt3ClientConfigView implements Mqtt3ClientConfig {
    private final MqttClientConfig delegate;

    public Mqtt3ClientConfigView(MqttClientConfig mqttClientConfig) {
        this.delegate = mqttClientConfig;
    }

    @Override // com.hivemq.client.mqtt.MqttClientConfig
    public MqttVersion getMqttVersion() {
        return this.delegate.getMqttVersion();
    }

    @Override // com.hivemq.client.mqtt.MqttClientConfig
    public Optional<MqttClientIdentifier> getClientIdentifier() {
        return this.delegate.getClientIdentifier();
    }

    @Override // com.hivemq.client.mqtt.MqttClientConfig
    public MqttClientTransportConfig getTransportConfig() {
        return this.delegate.getTransportConfig();
    }

    @Override // com.hivemq.client.mqtt.MqttClientConfig
    public MqttClientExecutorConfig getExecutorConfig() {
        return this.delegate.getExecutorConfig();
    }

    @Override // com.hivemq.client.mqtt.MqttClientConfig
    public Optional<MqttClientAutoReconnect> getAutomaticReconnect() {
        return this.delegate.getAutomaticReconnect();
    }

    @Override // com.hivemq.client.mqtt.mqtt3.Mqtt3ClientConfig
    public Optional<Mqtt3SimpleAuth> getSimpleAuth() {
        return Optional.ofNullable(getRawSimpleAuth());
    }

    private Mqtt3SimpleAuth getRawSimpleAuth() {
        MqttSimpleAuth simpleAuth = this.delegate.getConnectDefaults().getSimpleAuth();
        if (simpleAuth == null) {
            return null;
        }
        return Mqtt3SimpleAuthView.of(simpleAuth);
    }

    @Override // com.hivemq.client.mqtt.mqtt3.Mqtt3ClientConfig
    public Optional<Mqtt3Publish> getWillPublish() {
        return Optional.ofNullable(getRawWillPublish());
    }

    private Mqtt3Publish getRawWillPublish() {
        MqttWillPublish willPublish = this.delegate.getConnectDefaults().getWillPublish();
        if (willPublish == null) {
            return null;
        }
        return Mqtt3PublishView.of((MqttPublish) willPublish);
    }

    @Override // com.hivemq.client.mqtt.MqttClientConfig
    public List<MqttClientConnectedListener> getConnectedListeners() {
        return this.delegate.getConnectedListeners();
    }

    @Override // com.hivemq.client.mqtt.MqttClientConfig
    public List<MqttClientDisconnectedListener> getDisconnectedListeners() {
        return this.delegate.getDisconnectedListeners();
    }

    @Override // com.hivemq.client.mqtt.MqttClientConfig
    public MqttClientState getState() {
        return this.delegate.getState();
    }

    @Override // com.hivemq.client.mqtt.mqtt3.Mqtt3ClientConfig, com.hivemq.client.mqtt.MqttClientConfig
    public Optional<Mqtt3ClientConnectionConfig> getConnectionConfig() {
        return Optional.ofNullable(this.delegate.getRawConnectionConfig());
    }
}
