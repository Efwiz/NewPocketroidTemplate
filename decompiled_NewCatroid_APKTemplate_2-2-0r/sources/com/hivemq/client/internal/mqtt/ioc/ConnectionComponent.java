package com.hivemq.client.internal.mqtt.ioc;

import com.hivemq.client.internal.mqtt.codec.MqttCodecModule;
import com.hivemq.client.internal.mqtt.handler.connect.MqttConnAckFlow;
import com.hivemq.client.internal.mqtt.message.connect.MqttConnect;
import dagger.BindsInstance;
import dagger.Subcomponent;
import io.netty.bootstrap.Bootstrap;

/* JADX INFO: loaded from: classes10.dex */
@ConnectionScope
@Subcomponent(modules = {ConnectionModule.class, MqttCodecModule.class})
public interface ConnectionComponent {

    @Subcomponent.Builder
    public interface Builder {
        ConnectionComponent build();

        @BindsInstance
        Builder connAckFlow(MqttConnAckFlow mqttConnAckFlow);

        @BindsInstance
        Builder connect(MqttConnect mqttConnect);
    }

    Bootstrap bootstrap();
}
