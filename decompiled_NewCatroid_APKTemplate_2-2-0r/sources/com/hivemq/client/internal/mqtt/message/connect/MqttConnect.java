package com.hivemq.client.internal.mqtt.message.connect;

import com.hivemq.client.internal.mqtt.MqttClientConfig;
import com.hivemq.client.internal.mqtt.datatypes.MqttClientIdentifierImpl;
import com.hivemq.client.internal.mqtt.datatypes.MqttUserPropertiesImpl;
import com.hivemq.client.internal.mqtt.message.MqttMessageWithUserProperties;
import com.hivemq.client.internal.mqtt.message.auth.MqttEnhancedAuth;
import com.hivemq.client.internal.mqtt.message.auth.MqttSimpleAuth;
import com.hivemq.client.internal.mqtt.message.publish.MqttWillPublish;
import com.hivemq.client.internal.util.StringUtil;
import com.hivemq.client.mqtt.mqtt5.auth.Mqtt5EnhancedAuthMechanism;
import com.hivemq.client.mqtt.mqtt5.datatypes.Mqtt5UserProperties;
import com.hivemq.client.mqtt.mqtt5.message.auth.Mqtt5SimpleAuth;
import com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5Connect;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5WillPublish;
import java.util.Objects;
import java.util.Optional;

/* JADX INFO: loaded from: classes10.dex */
public class MqttConnect extends MqttMessageWithUserProperties implements Mqtt5Connect {
    public static final MqttConnect DEFAULT = new MqttConnect(60, true, 0, MqttConnectRestrictions.DEFAULT, null, null, null, MqttUserPropertiesImpl.NO_USER_PROPERTIES);
    private final boolean cleanStart;
    private final Mqtt5EnhancedAuthMechanism enhancedAuthMechanism;
    private final int keepAlive;
    private final MqttConnectRestrictions restrictions;
    private final long sessionExpiryInterval;
    private final MqttSimpleAuth simpleAuth;
    private final MqttWillPublish willPublish;

    @Override // com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5Connect
    public /* bridge */ /* synthetic */ Mqtt5UserProperties getUserProperties() {
        return super.getUserProperties();
    }

    public MqttConnect(int i, boolean z, long j, MqttConnectRestrictions mqttConnectRestrictions, MqttSimpleAuth mqttSimpleAuth, Mqtt5EnhancedAuthMechanism mqtt5EnhancedAuthMechanism, MqttWillPublish mqttWillPublish, MqttUserPropertiesImpl mqttUserPropertiesImpl) {
        super(mqttUserPropertiesImpl);
        this.keepAlive = i;
        this.cleanStart = z;
        this.sessionExpiryInterval = j;
        this.restrictions = mqttConnectRestrictions;
        this.simpleAuth = mqttSimpleAuth;
        this.enhancedAuthMechanism = mqtt5EnhancedAuthMechanism;
        this.willPublish = mqttWillPublish;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5Connect
    public int getKeepAlive() {
        return this.keepAlive;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5Connect
    public boolean isCleanStart() {
        return this.cleanStart;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5Connect
    public long getSessionExpiryInterval() {
        return this.sessionExpiryInterval;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5Connect
    public MqttConnectRestrictions getRestrictions() {
        return this.restrictions;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5Connect
    public Optional<Mqtt5SimpleAuth> getSimpleAuth() {
        return Optional.ofNullable(this.simpleAuth);
    }

    public MqttSimpleAuth getRawSimpleAuth() {
        return this.simpleAuth;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5Connect
    public Optional<Mqtt5EnhancedAuthMechanism> getEnhancedAuthMechanism() {
        return Optional.ofNullable(this.enhancedAuthMechanism);
    }

    public Mqtt5EnhancedAuthMechanism getRawEnhancedAuthMechanism() {
        return this.enhancedAuthMechanism;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5Connect
    public Optional<Mqtt5WillPublish> getWillPublish() {
        return Optional.ofNullable(this.willPublish);
    }

    public MqttWillPublish getRawWillPublish() {
        return this.willPublish;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5Connect
    public MqttConnectBuilder.Default extend() {
        return new MqttConnectBuilder.Default(this);
    }

    public MqttConnect setDefaults(MqttClientConfig mqttClientConfig) {
        MqttClientConfig.ConnectDefaults connectDefaults = mqttClientConfig.getConnectDefaults();
        MqttSimpleAuth simpleAuth = connectDefaults.getSimpleAuth();
        Mqtt5EnhancedAuthMechanism enhancedAuthMechanism = connectDefaults.getEnhancedAuthMechanism();
        MqttWillPublish willPublish = connectDefaults.getWillPublish();
        if ((simpleAuth == null || this.simpleAuth != null) && ((enhancedAuthMechanism == null || this.enhancedAuthMechanism != null) && (willPublish == null || this.willPublish != null))) {
            return this;
        }
        int i = this.keepAlive;
        boolean z = this.cleanStart;
        long j = this.sessionExpiryInterval;
        MqttConnectRestrictions mqttConnectRestrictions = this.restrictions;
        MqttSimpleAuth mqttSimpleAuth = this.simpleAuth;
        MqttSimpleAuth mqttSimpleAuth2 = mqttSimpleAuth == null ? simpleAuth : mqttSimpleAuth;
        Mqtt5EnhancedAuthMechanism mqtt5EnhancedAuthMechanism = this.enhancedAuthMechanism;
        Mqtt5EnhancedAuthMechanism mqtt5EnhancedAuthMechanism2 = mqtt5EnhancedAuthMechanism == null ? enhancedAuthMechanism : mqtt5EnhancedAuthMechanism;
        MqttWillPublish mqttWillPublish = this.willPublish;
        return new MqttConnect(i, z, j, mqttConnectRestrictions, mqttSimpleAuth2, mqtt5EnhancedAuthMechanism2, mqttWillPublish == null ? willPublish : mqttWillPublish, getUserProperties());
    }

    public MqttStatefulConnect createStateful(MqttClientIdentifierImpl mqttClientIdentifierImpl, MqttEnhancedAuth mqttEnhancedAuth) {
        return new MqttStatefulConnect(this, mqttClientIdentifierImpl, mqttEnhancedAuth);
    }

    @Override // com.hivemq.client.internal.mqtt.message.MqttMessageWithUserProperties
    protected String toAttributeString() {
        return "keepAlive=" + this.keepAlive + ", cleanStart=" + this.cleanStart + ", sessionExpiryInterval=" + this.sessionExpiryInterval + (this.restrictions == MqttConnectRestrictions.DEFAULT ? "" : ", restrictions=" + this.restrictions) + (this.simpleAuth == null ? "" : ", simpleAuth=" + this.simpleAuth) + (this.enhancedAuthMechanism == null ? "" : ", enhancedAuthMechanism=" + this.enhancedAuthMechanism) + (this.willPublish != null ? ", willPublish=" + this.willPublish : "") + StringUtil.prepend(", ", super.toAttributeString());
    }

    public String toString() {
        return "MqttConnect{" + toAttributeString() + '}';
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MqttConnect)) {
            return false;
        }
        MqttConnect mqttConnect = (MqttConnect) obj;
        return partialEquals(mqttConnect) && this.keepAlive == mqttConnect.keepAlive && this.cleanStart == mqttConnect.cleanStart && this.sessionExpiryInterval == mqttConnect.sessionExpiryInterval && this.restrictions.equals(mqttConnect.restrictions) && Objects.equals(this.simpleAuth, mqttConnect.simpleAuth) && Objects.equals(this.enhancedAuthMechanism, mqttConnect.enhancedAuthMechanism) && Objects.equals(this.willPublish, mqttConnect.willPublish);
    }

    public int hashCode() {
        return (((((((((((((partialHashCode() * 31) + this.keepAlive) * 31) + Boolean.hashCode(this.cleanStart)) * 31) + Long.hashCode(this.sessionExpiryInterval)) * 31) + this.restrictions.hashCode()) * 31) + Objects.hashCode(this.simpleAuth)) * 31) + Objects.hashCode(this.enhancedAuthMechanism)) * 31) + Objects.hashCode(this.willPublish);
    }
}
