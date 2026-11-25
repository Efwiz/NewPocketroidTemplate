package com.hivemq.client.mqtt.mqtt5;

import com.hivemq.client.mqtt.MqttGlobalPublishFilter;
import com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5Connect;
import com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5ConnectBuilder;
import com.hivemq.client.mqtt.mqtt5.message.connect.connack.Mqtt5ConnAck;
import com.hivemq.client.mqtt.mqtt5.message.disconnect.Mqtt5Disconnect;
import com.hivemq.client.mqtt.mqtt5.message.disconnect.Mqtt5DisconnectBuilder;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilder;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishResult;
import com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5Subscribe;
import com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5SubscribeBuilder;
import com.hivemq.client.mqtt.mqtt5.message.subscribe.suback.Mqtt5SubAck;
import com.hivemq.client.mqtt.mqtt5.message.unsubscribe.Mqtt5Unsubscribe;
import com.hivemq.client.mqtt.mqtt5.message.unsubscribe.Mqtt5UnsubscribeBuilder;
import com.hivemq.client.mqtt.mqtt5.message.unsubscribe.unsuback.Mqtt5UnsubAck;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt5BlockingClient extends Mqtt5Client {

    public interface Mqtt5Publishes extends AutoCloseable {
        @Override // java.lang.AutoCloseable
        void close();

        Mqtt5Publish receive() throws InterruptedException;

        Optional<Mqtt5Publish> receive(long j, TimeUnit timeUnit) throws InterruptedException;

        Optional<Mqtt5Publish> receiveNow();
    }

    Mqtt5ConnAck connect();

    Mqtt5ConnAck connect(Mqtt5Connect mqtt5Connect);

    Mqtt5ConnectBuilder.Send<Mqtt5ConnAck> connectWith();

    void disconnect();

    void disconnect(Mqtt5Disconnect mqtt5Disconnect);

    Mqtt5DisconnectBuilder.SendVoid disconnectWith();

    Mqtt5PublishResult publish(Mqtt5Publish mqtt5Publish);

    Mqtt5PublishBuilder.Send<Mqtt5PublishResult> publishWith();

    Mqtt5Publishes publishes(MqttGlobalPublishFilter mqttGlobalPublishFilter);

    Mqtt5Publishes publishes(MqttGlobalPublishFilter mqttGlobalPublishFilter, boolean z);

    void reauth();

    Mqtt5SubAck subscribe(Mqtt5Subscribe mqtt5Subscribe);

    Mqtt5SubscribeBuilder.Send.Start<Mqtt5SubAck> subscribeWith();

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5Client
    default Mqtt5BlockingClient toBlocking() {
        return this;
    }

    Mqtt5UnsubAck unsubscribe(Mqtt5Unsubscribe mqtt5Unsubscribe);

    Mqtt5UnsubscribeBuilder.Send.Start<Mqtt5UnsubAck> unsubscribeWith();
}
