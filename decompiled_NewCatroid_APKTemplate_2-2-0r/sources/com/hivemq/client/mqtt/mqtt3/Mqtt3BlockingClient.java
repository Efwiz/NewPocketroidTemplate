package com.hivemq.client.mqtt.mqtt3;

import com.hivemq.client.mqtt.MqttGlobalPublishFilter;
import com.hivemq.client.mqtt.mqtt3.message.connect.Mqtt3Connect;
import com.hivemq.client.mqtt.mqtt3.message.connect.Mqtt3ConnectBuilder;
import com.hivemq.client.mqtt.mqtt3.message.connect.connack.Mqtt3ConnAck;
import com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3Publish;
import com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3PublishBuilder;
import com.hivemq.client.mqtt.mqtt3.message.subscribe.Mqtt3Subscribe;
import com.hivemq.client.mqtt.mqtt3.message.subscribe.Mqtt3SubscribeBuilder;
import com.hivemq.client.mqtt.mqtt3.message.subscribe.suback.Mqtt3SubAck;
import com.hivemq.client.mqtt.mqtt3.message.unsubscribe.Mqtt3Unsubscribe;
import com.hivemq.client.mqtt.mqtt3.message.unsubscribe.Mqtt3UnsubscribeBuilder;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt3BlockingClient extends Mqtt3Client {

    public interface Mqtt3Publishes extends AutoCloseable {
        @Override // java.lang.AutoCloseable
        void close();

        Mqtt3Publish receive() throws InterruptedException;

        Optional<Mqtt3Publish> receive(long j, TimeUnit timeUnit) throws InterruptedException;

        Optional<Mqtt3Publish> receiveNow();
    }

    Mqtt3ConnAck connect();

    Mqtt3ConnAck connect(Mqtt3Connect mqtt3Connect);

    Mqtt3ConnectBuilder.Send<Mqtt3ConnAck> connectWith();

    void disconnect();

    void publish(Mqtt3Publish mqtt3Publish);

    Mqtt3PublishBuilder.SendVoid publishWith();

    Mqtt3Publishes publishes(MqttGlobalPublishFilter mqttGlobalPublishFilter);

    Mqtt3Publishes publishes(MqttGlobalPublishFilter mqttGlobalPublishFilter, boolean z);

    Mqtt3SubAck subscribe(Mqtt3Subscribe mqtt3Subscribe);

    Mqtt3SubscribeBuilder.Send.Start<Mqtt3SubAck> subscribeWith();

    @Override // com.hivemq.client.mqtt.mqtt3.Mqtt3Client
    default Mqtt3BlockingClient toBlocking() {
        return this;
    }

    void unsubscribe(Mqtt3Unsubscribe mqtt3Unsubscribe);

    Mqtt3UnsubscribeBuilder.SendVoid.Start unsubscribeWith();
}
