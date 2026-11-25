package com.hivemq.client.internal.mqtt.util;

import com.hivemq.client.internal.mqtt.datatypes.MqttBinaryData;
import com.hivemq.client.internal.mqtt.datatypes.MqttClientIdentifierImpl;
import com.hivemq.client.internal.mqtt.datatypes.MqttTopicFilterImpl;
import com.hivemq.client.internal.mqtt.datatypes.MqttTopicImpl;
import com.hivemq.client.internal.mqtt.datatypes.MqttUserPropertiesImpl;
import com.hivemq.client.internal.mqtt.datatypes.MqttUserPropertyImpl;
import com.hivemq.client.internal.mqtt.datatypes.MqttUtf8StringImpl;
import com.hivemq.client.internal.mqtt.message.connect.MqttConnect;
import com.hivemq.client.internal.mqtt.message.connect.mqtt3.Mqtt3ConnectView;
import com.hivemq.client.internal.mqtt.message.disconnect.MqttDisconnect;
import com.hivemq.client.internal.mqtt.message.publish.MqttPublish;
import com.hivemq.client.internal.mqtt.message.publish.mqtt3.Mqtt3PublishView;
import com.hivemq.client.internal.mqtt.message.subscribe.MqttSubscribe;
import com.hivemq.client.internal.mqtt.message.subscribe.mqtt3.Mqtt3SubscribeView;
import com.hivemq.client.internal.mqtt.message.unsubscribe.MqttUnsubscribe;
import com.hivemq.client.internal.mqtt.message.unsubscribe.mqtt3.Mqtt3UnsubscribeView;
import com.hivemq.client.internal.util.Checks;
import com.hivemq.client.internal.util.collections.ImmutableList;
import com.hivemq.client.mqtt.datatypes.MqttClientIdentifier;
import com.hivemq.client.mqtt.datatypes.MqttTopic;
import com.hivemq.client.mqtt.datatypes.MqttTopicFilter;
import com.hivemq.client.mqtt.datatypes.MqttUtf8String;
import com.hivemq.client.mqtt.mqtt3.message.connect.Mqtt3Connect;
import com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3Publish;
import com.hivemq.client.mqtt.mqtt3.message.subscribe.Mqtt3Subscribe;
import com.hivemq.client.mqtt.mqtt3.message.unsubscribe.Mqtt3Unsubscribe;
import com.hivemq.client.mqtt.mqtt5.datatypes.Mqtt5UserProperties;
import com.hivemq.client.mqtt.mqtt5.datatypes.Mqtt5UserProperty;
import com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5Connect;
import com.hivemq.client.mqtt.mqtt5.message.disconnect.Mqtt5Disconnect;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish;
import com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5Subscribe;
import com.hivemq.client.mqtt.mqtt5.message.unsubscribe.Mqtt5Unsubscribe;
import java.nio.ByteBuffer;
import java.util.Collection;

/* JADX INFO: loaded from: classes10.dex */
public final class MqttChecks {
    public static MqttUtf8StringImpl string(MqttUtf8String mqttUtf8String, String str) {
        return (MqttUtf8StringImpl) Checks.notImplemented(mqttUtf8String, MqttUtf8StringImpl.class, str);
    }

    public static MqttUtf8StringImpl stringOrNull(String str, String str2) {
        if (str == null) {
            return null;
        }
        return MqttUtf8StringImpl.of(str, str2);
    }

    public static MqttUtf8StringImpl stringOrNull(MqttUtf8String mqttUtf8String, String str) {
        return (MqttUtf8StringImpl) Checks.notImplementedOrNull(mqttUtf8String, MqttUtf8StringImpl.class, str);
    }

    public static MqttUtf8StringImpl reasonString(String str) {
        return stringOrNull(str, "Reason string");
    }

    public static MqttUtf8StringImpl reasonString(MqttUtf8String mqttUtf8String) {
        return stringOrNull(mqttUtf8String, "Reason string");
    }

    public static MqttTopicImpl topic(MqttTopic mqttTopic) {
        return (MqttTopicImpl) Checks.notImplemented(mqttTopic, MqttTopicImpl.class, "Topic");
    }

    public static MqttTopicFilterImpl topicFilter(MqttTopicFilter mqttTopicFilter) {
        return (MqttTopicFilterImpl) Checks.notImplemented(mqttTopicFilter, MqttTopicFilterImpl.class, "Topic filter");
    }

    public static MqttClientIdentifierImpl clientIdentifier(MqttClientIdentifier mqttClientIdentifier) {
        return (MqttClientIdentifierImpl) Checks.notImplemented(mqttClientIdentifier, MqttClientIdentifierImpl.class, "Client identifier");
    }

    private static ByteBuffer binaryDataInternal(byte[] bArr, String str) {
        if (!MqttBinaryData.isInRange(bArr)) {
            throw new IllegalArgumentException(str + " can not be encoded as binary data. Maximum length is 65535 bytes, but was " + bArr.length + " bytes.");
        }
        return ByteBuffer.wrap(bArr);
    }

    public static ByteBuffer binaryData(byte[] bArr, String str) {
        return binaryDataInternal((byte[]) Checks.notNull(bArr, str), str);
    }

    public static ByteBuffer binaryDataOrNull(byte[] bArr, String str) {
        if (bArr == null) {
            return null;
        }
        return binaryDataInternal(bArr, str);
    }

    private static ByteBuffer binaryDataInternal(ByteBuffer byteBuffer, String str) {
        if (!MqttBinaryData.isInRange(byteBuffer)) {
            throw new IllegalArgumentException(str + " can not be encoded as binary data. Maximum length is 65535 bytes, but was " + byteBuffer.remaining() + " bytes.");
        }
        return byteBuffer.slice();
    }

    public static ByteBuffer binaryData(ByteBuffer byteBuffer, String str) {
        return binaryDataInternal((ByteBuffer) Checks.notNull(byteBuffer, str), str);
    }

    public static ByteBuffer binaryDataOrNull(ByteBuffer byteBuffer, String str) {
        if (byteBuffer == null) {
            return null;
        }
        return binaryDataInternal(byteBuffer, str);
    }

    public static MqttUserPropertiesImpl userProperties(Mqtt5UserProperties mqtt5UserProperties) {
        return (MqttUserPropertiesImpl) Checks.notImplemented(mqtt5UserProperties, MqttUserPropertiesImpl.class, "User properties");
    }

    public static MqttUserPropertiesImpl userProperties(Mqtt5UserProperty... mqtt5UserPropertyArr) {
        return MqttUserPropertiesImpl.of((ImmutableList<MqttUserPropertyImpl>) Checks.elementsNotImplemented(ImmutableList.copyOf(mqtt5UserPropertyArr, "User properties"), MqttUserPropertyImpl.class, "User property"));
    }

    public static MqttUserPropertiesImpl userProperties(Collection<Mqtt5UserProperty> collection) {
        return MqttUserPropertiesImpl.of((ImmutableList<MqttUserPropertyImpl>) Checks.elementsNotImplemented(ImmutableList.copyOf(collection, "User properties"), MqttUserPropertyImpl.class, "User property"));
    }

    public static MqttUserPropertyImpl userProperty(Mqtt5UserProperty mqtt5UserProperty) {
        return (MqttUserPropertyImpl) Checks.notImplemented(mqtt5UserProperty, MqttUserPropertyImpl.class, "User property");
    }

    public static MqttUserPropertyImpl userProperty(MqttUtf8String mqttUtf8String, MqttUtf8String mqttUtf8String2) {
        return MqttUserPropertyImpl.of(string(mqttUtf8String, "User property name"), string(mqttUtf8String2, "User property value"));
    }

    public static int packetSize(int i, String str) {
        if (i <= 0 || i > 268435460) {
            throw new IllegalArgumentException(str + " must not exceed the value range of ]0, 268435460], but was " + i + ".");
        }
        return i;
    }

    public static MqttConnect connect(Mqtt5Connect mqtt5Connect) {
        return (MqttConnect) Checks.notImplemented(mqtt5Connect, MqttConnect.class, "Connect");
    }

    public static MqttConnect connect(Mqtt3Connect mqtt3Connect) {
        return ((Mqtt3ConnectView) Checks.notImplemented(mqtt3Connect, Mqtt3ConnectView.class, "Connect")).getDelegate();
    }

    public static MqttPublish publish(Mqtt5Publish mqtt5Publish) {
        return (MqttPublish) Checks.notImplemented(mqtt5Publish, MqttPublish.class, "Publish");
    }

    public static MqttPublish publish(Mqtt3Publish mqtt3Publish) {
        return ((Mqtt3PublishView) Checks.notImplemented(mqtt3Publish, Mqtt3PublishView.class, "Publish")).getDelegate();
    }

    public static MqttSubscribe subscribe(Mqtt5Subscribe mqtt5Subscribe) {
        return (MqttSubscribe) Checks.notImplemented(mqtt5Subscribe, MqttSubscribe.class, "Subscribe");
    }

    public static MqttSubscribe subscribe(Mqtt3Subscribe mqtt3Subscribe) {
        return ((Mqtt3SubscribeView) Checks.notImplemented(mqtt3Subscribe, Mqtt3SubscribeView.class, "Subscribe")).getDelegate();
    }

    public static MqttUnsubscribe unsubscribe(Mqtt5Unsubscribe mqtt5Unsubscribe) {
        return (MqttUnsubscribe) Checks.notImplemented(mqtt5Unsubscribe, MqttUnsubscribe.class, "Unsubscribe");
    }

    public static MqttUnsubscribe unsubscribe(Mqtt3Unsubscribe mqtt3Unsubscribe) {
        return ((Mqtt3UnsubscribeView) Checks.notImplemented(mqtt3Unsubscribe, Mqtt3UnsubscribeView.class, "Unsubscribe")).getDelegate();
    }

    public static MqttDisconnect disconnect(Mqtt5Disconnect mqtt5Disconnect) {
        return (MqttDisconnect) Checks.notImplemented(mqtt5Disconnect, MqttDisconnect.class, "Disconnect");
    }

    private MqttChecks() {
    }
}
