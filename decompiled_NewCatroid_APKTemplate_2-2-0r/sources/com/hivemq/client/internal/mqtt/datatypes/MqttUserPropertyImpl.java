package com.hivemq.client.internal.mqtt.datatypes;

import com.hivemq.client.mqtt.mqtt5.datatypes.Mqtt5UserProperty;
import com.huawei.hms.ml.language.common.utils.Constant;
import io.netty.buffer.ByteBuf;

/* JADX INFO: loaded from: classes10.dex */
public class MqttUserPropertyImpl implements Mqtt5UserProperty {

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private final MqttUtf8StringImpl f185name;
    private final MqttUtf8StringImpl value;

    public static MqttUserPropertyImpl of(String str, String str2) {
        return of(MqttUtf8StringImpl.of(str, "User property name"), MqttUtf8StringImpl.of(str2, "User property value"));
    }

    public static MqttUserPropertyImpl of(MqttUtf8StringImpl mqttUtf8StringImpl, MqttUtf8StringImpl mqttUtf8StringImpl2) {
        return new MqttUserPropertyImpl(mqttUtf8StringImpl, mqttUtf8StringImpl2);
    }

    public static MqttUserPropertyImpl decode(ByteBuf byteBuf) {
        MqttUtf8StringImpl mqttUtf8StringImplDecode;
        MqttUtf8StringImpl mqttUtf8StringImplDecode2 = MqttUtf8StringImpl.decode(byteBuf);
        if (mqttUtf8StringImplDecode2 == null || (mqttUtf8StringImplDecode = MqttUtf8StringImpl.decode(byteBuf)) == null) {
            return null;
        }
        return new MqttUserPropertyImpl(mqttUtf8StringImplDecode2, mqttUtf8StringImplDecode);
    }

    public MqttUserPropertyImpl(MqttUtf8StringImpl mqttUtf8StringImpl, MqttUtf8StringImpl mqttUtf8StringImpl2) {
        this.f185name = mqttUtf8StringImpl;
        this.value = mqttUtf8StringImpl2;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.datatypes.Mqtt5UserProperty
    public MqttUtf8StringImpl getName() {
        return this.f185name;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.datatypes.Mqtt5UserProperty
    public MqttUtf8StringImpl getValue() {
        return this.value;
    }

    void encode(ByteBuf byteBuf) {
        byteBuf.writeByte(38);
        this.f185name.encode(byteBuf);
        this.value.encode(byteBuf);
    }

    int encodedLength() {
        return this.f185name.encodedLength() + 1 + this.value.encodedLength();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MqttUserPropertyImpl)) {
            return false;
        }
        MqttUserPropertyImpl mqttUserPropertyImpl = (MqttUserPropertyImpl) obj;
        return this.f185name.equals(mqttUserPropertyImpl.f185name) && this.value.equals(mqttUserPropertyImpl.value);
    }

    public int hashCode() {
        return (this.f185name.hashCode() * 31) + this.value.hashCode();
    }

    @Override // java.lang.Comparable
    public int compareTo(Mqtt5UserProperty mqtt5UserProperty) {
        int iCompareTo = this.f185name.compareTo(mqtt5UserProperty.getName());
        return iCompareTo != 0 ? iCompareTo : this.value.compareTo(mqtt5UserProperty.getValue());
    }

    public String toString() {
        return "(" + this.f185name + ", " + this.value + Constant.AFTER_QUTO;
    }
}
