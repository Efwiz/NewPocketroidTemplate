package com.hivemq.client.internal.mqtt.datatypes;

import com.hivemq.client.internal.util.collections.ImmutableList;
import com.hivemq.client.mqtt.mqtt5.datatypes.Mqtt5UserProperties;
import io.netty.buffer.ByteBuf;

/* JADX INFO: loaded from: classes10.dex */
public class MqttUserPropertiesImpl implements Mqtt5UserProperties {
    public static final MqttUserPropertiesImpl NO_USER_PROPERTIES = new MqttUserPropertiesImpl(ImmutableList.of());
    private int encodedLength = -1;
    private final ImmutableList<MqttUserPropertyImpl> userProperties;

    public static MqttUserPropertiesImpl of(ImmutableList<MqttUserPropertyImpl> immutableList) {
        return immutableList.isEmpty() ? NO_USER_PROPERTIES : new MqttUserPropertiesImpl(immutableList);
    }

    public static MqttUserPropertiesImpl build(ImmutableList.Builder<MqttUserPropertyImpl> builder) {
        return builder == null ? NO_USER_PROPERTIES : of(builder.build());
    }

    private MqttUserPropertiesImpl(ImmutableList<MqttUserPropertyImpl> immutableList) {
        this.userProperties = immutableList;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.datatypes.Mqtt5UserProperties
    public ImmutableList<MqttUserPropertyImpl> asList() {
        return this.userProperties;
    }

    public void encode(ByteBuf byteBuf) {
        for (int i = 0; i < this.userProperties.size(); i++) {
            this.userProperties.get(i).encode(byteBuf);
        }
    }

    public int encodedLength() {
        if (this.encodedLength == -1) {
            this.encodedLength = calculateEncodedLength();
        }
        return this.encodedLength;
    }

    private int calculateEncodedLength() {
        int iEncodedLength = 0;
        for (int i = 0; i < this.userProperties.size(); i++) {
            iEncodedLength += this.userProperties.get(i).encodedLength();
        }
        return iEncodedLength;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof MqttUserPropertiesImpl) {
            return this.userProperties.equals(((MqttUserPropertiesImpl) obj).userProperties);
        }
        return false;
    }

    public int hashCode() {
        return this.userProperties.hashCode();
    }

    @Override // com.hivemq.client.mqtt.mqtt5.datatypes.Mqtt5UserProperties
    public MqttUserPropertiesImplBuilder.Default extend() {
        return new MqttUserPropertiesImplBuilder.Default(this);
    }

    public String toString() {
        return this.userProperties.toString();
    }
}
