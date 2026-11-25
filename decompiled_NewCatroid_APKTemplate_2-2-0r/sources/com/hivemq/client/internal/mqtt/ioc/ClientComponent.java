package com.hivemq.client.internal.mqtt.ioc;

import com.hivemq.client.internal.mqtt.MqttClientConfig;
import com.hivemq.client.internal.mqtt.handler.publish.incoming.MqttIncomingQosHandler;
import com.hivemq.client.internal.mqtt.handler.publish.outgoing.MqttOutgoingQosHandler;
import com.hivemq.client.internal.mqtt.handler.subscribe.MqttSubscriptionHandler;
import dagger.BindsInstance;
import dagger.Subcomponent;

/* JADX INFO: loaded from: classes10.dex */
@ClientScope
@Subcomponent(modules = {ClientModule.class})
public interface ClientComponent {

    @Subcomponent.Builder
    public interface Builder {
        ClientComponent build();

        @BindsInstance
        Builder clientConfig(MqttClientConfig mqttClientConfig);
    }

    ConnectionComponent.Builder connectionComponentBuilder();

    MqttIncomingQosHandler incomingQosHandler();

    MqttOutgoingQosHandler outgoingQosHandler();

    MqttSubscriptionHandler subscriptionHandler();
}
