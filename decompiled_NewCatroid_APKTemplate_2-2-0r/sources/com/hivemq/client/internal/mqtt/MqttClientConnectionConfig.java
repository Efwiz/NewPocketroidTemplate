package com.hivemq.client.internal.mqtt;

import com.hivemq.client.internal.mqtt.handler.publish.outgoing.MqttTopicAliasAutoMapping;
import com.hivemq.client.internal.mqtt.handler.publish.outgoing.MqttTopicAliasMapping;
import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.mqtt3.Mqtt3ClientConnectionConfig;
import com.hivemq.client.mqtt.mqtt5.Mqtt5ClientConnectionConfig;
import com.hivemq.client.mqtt.mqtt5.auth.Mqtt5EnhancedAuthMechanism;
import io.netty.channel.Channel;
import java.util.Optional;

/* JADX INFO: loaded from: classes10.dex */
public class MqttClientConnectionConfig implements Mqtt5ClientConnectionConfig, Mqtt5ClientConnectionConfig.RestrictionsForServer, Mqtt5ClientConnectionConfig.RestrictionsForClient, Mqtt3ClientConnectionConfig, Mqtt3ClientConnectionConfig.RestrictionsForClient {
    private static final int FLAG_CLEAN_START = 256;
    private static final int FLAG_CLEAN_STOP = 512;
    private static final int FLAG_HAS_SIMPLE_AUTH = 1;
    private static final int FLAG_HAS_WILL_PUBLISH = 2;
    private static final int FLAG_PROBLEM_INFORMATION_REQUESTED = 4;
    private static final int FLAG_RESPONSE_INFORMATION_REQUESTED = 8;
    private static final int FLAG_RETAIN_AVAILABLE = 16;
    private static final int FLAG_SHARED_SUBSCRIPTION_AVAILABLE = 64;
    private static final int FLAG_SUBSCRIPTION_IDENTIFIERS_AVAILABLE = 128;
    private static final int FLAG_WILDCARD_SUBSCRIPTION_AVAILABLE = 32;
    private final Channel channel;
    private final Mqtt5EnhancedAuthMechanism enhancedAuthMechanism;
    private final int flags;
    private final short keepAlive;
    private final int maximumPacketSize;
    private final MqttQos maximumQos;
    private final short receiveMaximum;
    private final short sendMaximum;
    private final int sendMaximumPacketSize;
    private final MqttTopicAliasMapping sendTopicAliasMapping;
    private volatile int sessionExpiryInterval;
    private final short topicAliasMaximum;
    private final MqttClientTransportConfigImpl transportConfig;

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5ClientConnectionConfig, com.hivemq.client.mqtt.mqtt3.Mqtt3ClientConnectionConfig
    public MqttClientConnectionConfig getRestrictionsForClient() {
        return this;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5ClientConnectionConfig
    public MqttClientConnectionConfig getRestrictionsForServer() {
        return this;
    }

    public MqttClientConnectionConfig(MqttClientTransportConfigImpl mqttClientTransportConfigImpl, int i, boolean z, boolean z2, long j, boolean z3, boolean z4, Mqtt5EnhancedAuthMechanism mqtt5EnhancedAuthMechanism, int i2, int i3, int i4, boolean z5, boolean z6, int i5, int i6, int i7, MqttQos mqttQos, boolean z7, boolean z8, boolean z9, boolean z10, Channel channel) {
        this.transportConfig = mqttClientTransportConfigImpl;
        this.keepAlive = (short) i;
        this.sessionExpiryInterval = (int) j;
        this.enhancedAuthMechanism = mqtt5EnhancedAuthMechanism;
        this.receiveMaximum = (short) i2;
        this.maximumPacketSize = i3;
        this.topicAliasMaximum = (short) i4;
        this.sendMaximum = (short) i5;
        this.sendMaximumPacketSize = i6;
        this.sendTopicAliasMapping = i7 == 0 ? null : new MqttTopicAliasAutoMapping(i7);
        this.maximumQos = mqttQos;
        this.channel = channel;
        int i8 = z4 ? (z3 ? 1 : 0) | 2 : z3 ? 1 : 0;
        i8 = z5 ? (i8 == true ? 1 : 0) | 4 : i8;
        i8 = z6 ? (i8 == true ? 1 : 0) | 8 : i8;
        i8 = z7 ? (i8 == true ? 1 : 0) | 16 : i8;
        i8 = z8 ? (i8 == true ? 1 : 0) | 32 : i8;
        i8 = z9 ? (i8 == true ? 1 : 0) | 64 : i8;
        i8 = z10 ? (i8 == true ? 1 : 0) | 128 : i8;
        i8 = z ? (i8 == true ? 1 : 0) | 256 : i8;
        this.flags = z2 ? (i8 == true ? 1 : 0) | 512 : i8;
    }

    @Override // com.hivemq.client.mqtt.MqttClientConnectionConfig
    public MqttClientTransportConfigImpl getTransportConfig() {
        return this.transportConfig;
    }

    @Override // com.hivemq.client.mqtt.MqttClientConnectionConfig
    public int getKeepAlive() {
        return this.keepAlive & 65535;
    }

    public boolean isCleanStart() {
        return (this.flags & 256) != 0;
    }

    public boolean isCleanStop() {
        return (this.flags & 512) != 0;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5ClientConnectionConfig
    public long getSessionExpiryInterval() {
        return ((long) this.sessionExpiryInterval) & 4294967295L;
    }

    public void setSessionExpiryInterval(long j) {
        this.sessionExpiryInterval = (int) j;
    }

    @Override // com.hivemq.client.mqtt.MqttClientConnectionConfig
    public boolean hasSimpleAuth() {
        return (this.flags & 1) != 0;
    }

    @Override // com.hivemq.client.mqtt.MqttClientConnectionConfig
    public boolean hasWillPublish() {
        return (this.flags & 2) != 0;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5ClientConnectionConfig
    public Optional<Mqtt5EnhancedAuthMechanism> getEnhancedAuthMechanism() {
        return Optional.ofNullable(this.enhancedAuthMechanism);
    }

    public Mqtt5EnhancedAuthMechanism getRawEnhancedAuthMechanism() {
        return this.enhancedAuthMechanism;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5ClientConnectionConfig.RestrictionsForServer
    public int getReceiveMaximum() {
        return this.receiveMaximum & 65535;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5ClientConnectionConfig.RestrictionsForServer
    public int getMaximumPacketSize() {
        return this.maximumPacketSize;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5ClientConnectionConfig.RestrictionsForServer
    public int getTopicAliasMaximum() {
        return this.topicAliasMaximum & 65535;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5ClientConnectionConfig.RestrictionsForServer
    public boolean isProblemInformationRequested() {
        return (this.flags & 4) != 0;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5ClientConnectionConfig.RestrictionsForServer
    public boolean isResponseInformationRequested() {
        return (this.flags & 8) != 0;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5ClientConnectionConfig.RestrictionsForClient, com.hivemq.client.mqtt.mqtt3.Mqtt3ClientConnectionConfig.RestrictionsForClient
    public int getSendMaximum() {
        return this.sendMaximum & 65535;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5ClientConnectionConfig.RestrictionsForClient
    public int getSendMaximumPacketSize() {
        return this.sendMaximumPacketSize;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5ClientConnectionConfig.RestrictionsForClient
    public int getSendTopicAliasMaximum() {
        MqttTopicAliasMapping mqttTopicAliasMapping = this.sendTopicAliasMapping;
        if (mqttTopicAliasMapping == null) {
            return 0;
        }
        return mqttTopicAliasMapping.getTopicAliasMaximum();
    }

    public MqttTopicAliasMapping getSendTopicAliasMapping() {
        return this.sendTopicAliasMapping;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5ClientConnectionConfig.RestrictionsForClient
    public MqttQos getMaximumQos() {
        return this.maximumQos;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5ClientConnectionConfig.RestrictionsForClient
    public boolean isRetainAvailable() {
        return (this.flags & 16) != 0;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5ClientConnectionConfig.RestrictionsForClient
    public boolean isWildcardSubscriptionAvailable() {
        return (this.flags & 32) != 0;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5ClientConnectionConfig.RestrictionsForClient
    public boolean isSharedSubscriptionAvailable() {
        return (this.flags & 64) != 0;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5ClientConnectionConfig.RestrictionsForClient
    public boolean areSubscriptionIdentifiersAvailable() {
        return (this.flags & 128) != 0;
    }

    public Channel getChannel() {
        return this.channel;
    }
}
