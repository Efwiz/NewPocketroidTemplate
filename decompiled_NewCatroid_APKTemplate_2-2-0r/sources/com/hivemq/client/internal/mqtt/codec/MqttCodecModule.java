package com.hivemq.client.internal.mqtt.codec;

import com.hivemq.client.internal.mqtt.MqttClientConfig;
import com.hivemq.client.internal.mqtt.codec.decoder.MqttMessageDecoders;
import com.hivemq.client.internal.mqtt.codec.decoder.mqtt3.Mqtt3ClientMessageDecoders;
import com.hivemq.client.internal.mqtt.codec.decoder.mqtt5.Mqtt5ClientMessageDecoders;
import com.hivemq.client.internal.mqtt.codec.encoder.MqttMessageEncoders;
import com.hivemq.client.internal.mqtt.codec.encoder.mqtt3.Mqtt3ClientMessageEncoders;
import com.hivemq.client.internal.mqtt.codec.encoder.mqtt5.Mqtt5ClientMessageEncoders;
import com.hivemq.client.internal.mqtt.ioc.ConnectionScope;
import com.hivemq.client.mqtt.MqttVersion;
import dagger.Lazy;
import dagger.Module;
import dagger.Provides;

/* JADX INFO: loaded from: classes10.dex */
@Module
public abstract class MqttCodecModule {

    /* JADX INFO: renamed from: com.hivemq.client.internal.mqtt.codec.MqttCodecModule$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$hivemq$client$mqtt$MqttVersion;

        static {
            int[] iArr = new int[MqttVersion.values().length];
            $SwitchMap$com$hivemq$client$mqtt$MqttVersion = iArr;
            try {
                iArr[MqttVersion.MQTT_5_0.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$hivemq$client$mqtt$MqttVersion[MqttVersion.MQTT_3_1_1.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    @ConnectionScope
    @Provides
    static MqttMessageDecoders provideMessageDecoders(MqttClientConfig mqttClientConfig, Lazy<Mqtt5ClientMessageDecoders> lazy, Lazy<Mqtt3ClientMessageDecoders> lazy2) {
        int i = AnonymousClass1.$SwitchMap$com$hivemq$client$mqtt$MqttVersion[mqttClientConfig.getMqttVersion().ordinal()];
        if (i == 1) {
            return lazy.get();
        }
        if (i == 2) {
            return lazy2.get();
        }
        throw new IllegalStateException();
    }

    @ConnectionScope
    @Provides
    static MqttMessageEncoders provideMessageEncoders(MqttClientConfig mqttClientConfig, Lazy<Mqtt5ClientMessageEncoders> lazy, Lazy<Mqtt3ClientMessageEncoders> lazy2) {
        int i = AnonymousClass1.$SwitchMap$com$hivemq$client$mqtt$MqttVersion[mqttClientConfig.getMqttVersion().ordinal()];
        if (i == 1) {
            return lazy.get();
        }
        if (i == 2) {
            return lazy2.get();
        }
        throw new IllegalStateException();
    }
}
