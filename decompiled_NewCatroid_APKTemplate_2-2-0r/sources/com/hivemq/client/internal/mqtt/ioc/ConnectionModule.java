package com.hivemq.client.internal.mqtt.ioc;

import com.hivemq.client.internal.mqtt.handler.MqttChannelInitializer;
import com.hivemq.client.internal.mqtt.handler.auth.MqttAuthHandler;
import com.hivemq.client.internal.mqtt.handler.auth.MqttConnectAuthHandler;
import com.hivemq.client.internal.mqtt.handler.auth.MqttDisconnectOnAuthHandler;
import com.hivemq.client.internal.mqtt.message.connect.MqttConnect;
import com.hivemq.client.internal.netty.NettyEventLoopProvider;
import dagger.Lazy;
import dagger.Module;
import dagger.Provides;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFactory;

/* JADX INFO: loaded from: classes10.dex */
@Module
abstract class ConnectionModule {
    ConnectionModule() {
    }

    @Provides
    static Bootstrap provideBootstrap(MqttChannelInitializer mqttChannelInitializer) {
        return new Bootstrap().channelFactory((ChannelFactory) NettyEventLoopProvider.INSTANCE.getChannelFactory()).handler(mqttChannelInitializer);
    }

    @ConnectionScope
    @Provides
    static MqttAuthHandler provideAuthHandler(MqttConnect mqttConnect, Lazy<MqttConnectAuthHandler> lazy, Lazy<MqttDisconnectOnAuthHandler> lazy2) {
        return mqttConnect.getRawEnhancedAuthMechanism() == null ? lazy2.get() : lazy.get();
    }
}
